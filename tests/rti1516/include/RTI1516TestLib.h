/* -*-c++-*- OpenRTI - Copyright (C) 2009-2011 Mathias Froehlich
 *
 * This file is part of OpenRTI.
 *
 * OpenRTI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * OpenRTI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OpenRTI.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

#ifndef OpenRTI_RTI1516TestLib_h
#define OpenRTI_RTI1516TestLib_h

#include <string>
#include <vector>
#include <iostream>

#include <RTI/FederateAmbassador.h>
#include <RTI/RTIambassadorFactory.h>
#include <RTI/RTIambassador.h>
#include <RTI/LogicalTime.h>
#include <RTI/LogicalTimeInterval.h>
#include <RTI/LogicalTimeFactory.h>
#include <RTI/RangeBounds.h>

#include <TestLib.h>

namespace OpenRTI {

inline rti1516::VariableLengthData
toVariableLengthData(const char* s)
{
    rti1516::VariableLengthData variableLengthData;
    if (s)
        variableLengthData.setData(s, strlen(s));
    return variableLengthData;
}

inline rti1516::VariableLengthData
toVariableLengthData(const std::string& s)
{
    rti1516::VariableLengthData variableLengthData;
    variableLengthData.setData(s.data(), s.size());
    return variableLengthData;
}

inline std::string
toString(const rti1516::VariableLengthData& variableLengthData)
{
    if (!variableLengthData.size())
        return std::string();
    return std::string((const char*)variableLengthData.data(), variableLengthData.size());
}

inline rti1516::VariableLengthData
toVariableLengthData(const std::wstring& s)
{
    rti1516::VariableLengthData variableLengthData;
    variableLengthData.setData(s.data(), sizeof(std::wstring::value_type)*s.size());
    return variableLengthData;
}

inline std::wstring
toWString(const rti1516::VariableLengthData& variableLengthData)
{
    if (!variableLengthData.size())
        return std::wstring();
    return std::wstring((const wchar_t*)variableLengthData.data(), variableLengthData.size()/sizeof(std::wstring::value_type));
}

inline rti1516::VariableLengthData
toVariableLengthData(const Clock& c)
{
    // May be at some time make this endian safe
    rti1516::VariableLengthData variableLengthData;
    variableLengthData.setData(&c, sizeof(c));
    return variableLengthData;
}

inline Clock
toClock(const rti1516::VariableLengthData& variableLengthData)
{
    Clock c;
    // May be at some time make this endian safe
    if (variableLengthData.size() == sizeof(Clock))
        memcpy(&c, variableLengthData.data(), sizeof(Clock));
    return c;
}

inline rti1516::VariableLengthData
toVariableLengthData(unsigned u)
{
    // May be at some time make this endian safe
    rti1516::VariableLengthData variableLengthData;
    variableLengthData.setData(&u, sizeof(u));
    return variableLengthData;
}

inline unsigned
toUnsigned(const rti1516::VariableLengthData& variableLengthData)
{
    unsigned u = -1;
    // May be at some time make this endian safe
    if (variableLengthData.size() == sizeof(unsigned))
        memcpy(&u, variableLengthData.data(), sizeof(unsigned));
    return u;
}

class RTI1516TestAmbassador : public RTITest::Ambassador, public rti1516::FederateAmbassador {
public:
  RTI1516TestAmbassador(const RTITest::ConstructorArgs& constructorArgs) :
    RTITest::Ambassador(constructorArgs),
    _synchronized(0)
  { }
  virtual ~RTI1516TestAmbassador()
    throw ()
  { }

  virtual bool execJoined(rti1516::RTIambassador& ambassador) = 0;

  bool waitForAllFederates(rti1516::RTIambassador& ambassador)
  {
    _synchronized = 0;

    // FIXME need a test for concurrent announces
    try {
      ambassador.registerFederationSynchronizationPoint(getFederateType(), rti1516::VariableLengthData());
    } catch (const rti1516::Exception& e) {
      std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
      return false;
    } catch (...) {
      std::wcout << L"Unknown Exception!" << std::endl;
      return false;
    }

    try {
      Clock timeout = Clock::now() + Clock::fromSeconds(10);
      while (!_federateSet.empty()) {
        if (ambassador.evokeCallback(10.0))
          continue;
        if (timeout < Clock::now()) {
          std::wcout << L"Timeout waiting for other federates to join!" << std::endl;
          return false;
        }
      }
    } catch (const rti1516::Exception& e) {
      std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
      return false;
    } catch (...) {
      std::wcout << L"Unknown Exception!" << std::endl;
      return false;
    }

    // Fill for the next time
    _federateSet.insert(getFederateList().begin(), getFederateList().end());

    try {
      for (std::vector<std::wstring>::const_iterator i = getFederateList().begin(); i != getFederateList().end(); ++i) {
        ambassador.synchronizationPointAchieved(*i);
      }
    } catch (const rti1516::Exception& e) {
      std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
      return false;
    } catch (...) {
      std::wcout << L"Unknown Exception!" << std::endl;
      return false;
    }

    try {
      Clock timeout = Clock::now() + Clock::fromSeconds(10);
      while (_synchronized < getFederateList().size()) {
        if (ambassador.evokeCallback(10.0))
          continue;
        if (timeout < Clock::now()) {
          std::wcout << L"Timeout waiting for other federates to synchronize!" << std::endl;
          return false;
        }
      }
    } catch (const rti1516::Exception& e) {
      std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
      return false;
    } catch (...) {
      std::wcout << L"Unknown Exception!" << std::endl;
      return false;
    }

    return true;
  }

  virtual bool execJoinOnce()
  {
    std::auto_ptr<rti1516::RTIambassador> ambassador;
    rti1516::RTIambassadorFactory factory;
    std::vector<std::wstring> args = getArgumentList();
    ambassador = factory.createRTIambassador(args);

    // create, must work once
    try {
      ambassador->createFederationExecution(getFederationExecution(), getFddFile());

      if (!getFederationBarrier()->success())
        return false;
    } catch (const rti1516::FederationExecutionAlreadyExists&) {
      // Can happen in this test

      if (!getFederationBarrier()->fail())
        return false;
    } catch (const rti1516::Exception& e) {
      std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
      return false;
    } catch (...) {
      std::wcout << L"Unknown Exception!" << std::endl;
      return false;
    }

    // Try that several times. Ensure correct cleanup
    unsigned n = 99;
    for (unsigned i = 0; i < n; ++i) {

      // join must work
      try {
        ambassador->joinFederationExecution(getFederateType(), getFederationExecution(), *this);
      } catch (const rti1516::Exception& e) {
        std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
        return false;
      } catch (...) {
        std::wcout << L"Unknown Exception!" << std::endl;
        return false;
      }

      _federateSet.insert(getFederateList().begin(), getFederateList().end());

      if (!execJoined(*ambassador))
        return false;

      // and now resign must work
      try {
        ambassador->resignFederationExecution(rti1516::CANCEL_THEN_DELETE_THEN_DIVEST);
      } catch (const rti1516::Exception& e) {
        std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
        return false;
      } catch (...) {
        std::wcout << L"Unknown Exception!" << std::endl;
        return false;
      }

    }

    // Wait for all threads, this is to ensure that we do not destroy before we are ready
    wait();

    // destroy, must work once
    try {
      ambassador->destroyFederationExecution(getFederationExecution());

      if (!getFederationBarrier()->success())
        return false;
    } catch (const rti1516::FederatesCurrentlyJoined&) {
      // Can happen in this test
      // Other threads just might have still joined.

      if (!getFederationBarrier()->fail())
        return false;
    } catch (const rti1516::FederationExecutionDoesNotExist&) {
      // Can happen in this test
      // Other threads might have been faster

      if (!getFederationBarrier()->fail())
        return false;
    } catch (const rti1516::Exception& e) {
      std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
      return false;
    } catch (...) {
      std::wcout << L"Unknown Exception!" << std::endl;
      return false;
    }

    return true;
  }

  virtual bool execJoinMultiple()
  {
    std::auto_ptr<rti1516::RTIambassador> ambassador;
    rti1516::RTIambassadorFactory factory;
    std::vector<std::wstring> args = getArgumentList();
    ambassador = factory.createRTIambassador(args);

    // Try that several times. Ensure correct cleanup
    unsigned n = 99;
    for (unsigned i = 0; i < n; ++i) {

      // create, must work once
      try {
        ambassador->createFederationExecution(getFederationExecution(), getFddFile());

        if (!getFederationBarrier()->success())
          return false;
      } catch (const rti1516::FederationExecutionAlreadyExists&) {
        // Can happen in this test

        if (!getFederationBarrier()->fail())
          return false;
      } catch (const rti1516::Exception& e) {
        std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
        return false;
      } catch (...) {
        std::wcout << L"Unknown Exception!" << std::endl;
        return false;
      }

      // join must work
      try {
        ambassador->joinFederationExecution(getFederateType(), getFederationExecution(), *this);
      } catch (const rti1516::Exception& e) {
        std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
        return false;
      } catch (...) {
        std::wcout << L"Unknown Exception!" << std::endl;
        return false;
      }

      _federateSet.insert(getFederateList().begin(), getFederateList().end());

      if (!execJoined(*ambassador))
        return false;

      // and now resign must work
      try {
        ambassador->resignFederationExecution(rti1516::CANCEL_THEN_DELETE_THEN_DIVEST);
      } catch (const rti1516::Exception& e) {
        std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
        return false;
      } catch (...) {
        std::wcout << L"Unknown Exception!" << std::endl;
        return false;
      }

      // Wait for all threads, this is to ensure that we do not destroy before we are ready
      wait();

      // destroy, must work once
      try {
        ambassador->destroyFederationExecution(getFederationExecution());

        if (!getFederationBarrier()->success())
          return false;
      } catch (const rti1516::FederatesCurrentlyJoined&) {
        // Can happen in this test
        // Other threads just might have still joined.

        if (!getFederationBarrier()->fail())
          return false;
      } catch (const rti1516::FederationExecutionDoesNotExist&) {
        // Can happen in this test
        // Other threads might have been faster

        if (!getFederationBarrier()->fail())
          return false;
      } catch (const rti1516::Exception& e) {
        std::wcout << L"rti1516::Exception: \"" << e.what() << L"\"" << std::endl;
        return false;
      } catch (...) {
        std::wcout << L"Unknown Exception!" << std::endl;
        return false;
      }
    }

    return true;
  }

  virtual bool exec()
  {
    if (_constructorArgs._joinOnce)
      return execJoinOnce();
    else
      return execJoinMultiple();
  }

  virtual void synchronizationPointRegistrationSucceeded(const std::wstring& label)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void synchronizationPointRegistrationFailed(const std::wstring& label, rti1516::SynchronizationFailureReason reason)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void announceSynchronizationPoint(const std::wstring& label, const rti1516::VariableLengthData& tag)
    throw (rti1516::FederateInternalError)
  {
    _federateSet.erase(label);
  }

  virtual void federationSynchronized(const std::wstring& label)
    throw (rti1516::FederateInternalError)
  {
    ++_synchronized;
  }

  virtual void initiateFederateSave(const std::wstring& label)
      throw (rti1516::UnableToPerformSave,
             rti1516::FederateInternalError)
  {
  }

  virtual void initiateFederateSave(const std::wstring& label, const rti1516::LogicalTime& logicalTime)
      throw (rti1516::UnableToPerformSave,
             rti1516::InvalidLogicalTime,
             rti1516::FederateInternalError)
  {
  }

  virtual void federationSaved()
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationNotSaved(rti1516::SaveFailureReason theSaveFailureReason)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationSaveStatusResponse(const rti1516::FederateHandleSaveStatusPairVector& theFederateStatusVector)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void requestFederationRestoreSucceeded(const std::wstring& label)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void requestFederationRestoreFailed(const std::wstring& label)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationRestoreBegun()
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void initiateFederateRestore(const std::wstring& label, rti1516::FederateHandle handle)
    throw (rti1516::SpecifiedSaveLabelDoesNotExist,
           rti1516::CouldNotInitiateRestore,
           rti1516::FederateInternalError)
  {
  }

  virtual void federationRestored()
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationNotRestored(rti1516::RestoreFailureReason theRestoreFailureReason)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationRestoreStatusResponse(const rti1516::FederateHandleRestoreStatusPairVector& theFederateStatusVector)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void startRegistrationForObjectClass(rti1516::ObjectClassHandle)
    throw (rti1516::ObjectClassNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void stopRegistrationForObjectClass(rti1516::ObjectClassHandle)
    throw (rti1516::ObjectClassNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void turnInteractionsOn(rti1516::InteractionClassHandle)
    throw (rti1516::InteractionClassNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void turnInteractionsOff(rti1516::InteractionClassHandle)
    throw (rti1516::InteractionClassNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void objectInstanceNameReservationSucceeded(const std::wstring&)
    throw (rti1516::UnknownName,
           rti1516::FederateInternalError)
  {
  }

  virtual void objectInstanceNameReservationFailed(const std::wstring&)
    throw (rti1516::UnknownName,
           rti1516::FederateInternalError)
  {
  }

  virtual void discoverObjectInstance(rti1516::ObjectInstanceHandle, rti1516::ObjectClassHandle, const std::wstring&)
    throw (rti1516::CouldNotDiscover,
           rti1516::ObjectClassNotKnown,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::RegionHandleSet&)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::LogicalTime&, rti1516::OrderType)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::LogicalTime&, rti1516::OrderType, const rti1516::RegionHandleSet&)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::LogicalTime&, rti1516::OrderType, rti1516::MessageRetractionHandle)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::LogicalTime&, rti1516::OrderType, rti1516::MessageRetractionHandle,
                                      const rti1516::RegionHandleSet&)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle, const rti1516::ParameterHandleValueMap&,
                                  const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle, const rti1516::ParameterHandleValueMap&,
                                  const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                  const rti1516::RegionHandleSet&)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle theInteraction,
                                  rti1516::ParameterHandleValueMap const & theParameterValues,
                                  rti1516::VariableLengthData const & theUserSuppliedTag,
                                  rti1516::OrderType sentOrder,
                                  rti1516::TransportationType theType,
                                  rti1516::LogicalTime const & theTime,
                                  rti1516::OrderType receivedOrder)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle theInteraction,
                                  rti1516::ParameterHandleValueMap const & theParameterValues,
                                  rti1516::VariableLengthData const & theUserSuppliedTag,
                                  rti1516::OrderType sentOrder,
                                  rti1516::TransportationType theType,
                                  rti1516::LogicalTime const & theTime,
                                  rti1516::OrderType receivedOrder,
                                  rti1516::RegionHandleSet const & theSentRegionHandleSet)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle theInteraction,
                                  rti1516::ParameterHandleValueMap const & theParameterValues,
                                  rti1516::VariableLengthData const & theUserSuppliedTag,
                                  rti1516::OrderType sentOrder,
                                  rti1516::TransportationType theType,
                                  rti1516::LogicalTime const & theTime,
                                  rti1516::OrderType receivedOrder,
                                  rti1516::MessageRetractionHandle theHandle)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle theInteraction,
                                  rti1516::ParameterHandleValueMap const & theParameterValues,
                                  rti1516::VariableLengthData const & theUserSuppliedTag,
                                  rti1516::OrderType sentOrder,
                                  rti1516::TransportationType theType,
                                  rti1516::LogicalTime const & theTime,
                                  rti1516::OrderType receivedOrder,
                                  rti1516::MessageRetractionHandle theHandle,
                                  rti1516::RegionHandleSet const & theSentRegionHandleSet)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void removeObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                    rti1516::VariableLengthData const & theUserSuppliedTag,
                                    rti1516::OrderType sentOrder)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::FederateInternalError)
  {
  }

  virtual void removeObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                    rti1516::VariableLengthData const & theUserSuppliedTag,
                                    rti1516::OrderType sentOrder,
                                    rti1516::LogicalTime const & theTime,
                                    rti1516::OrderType receivedOrder)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::FederateInternalError)
  {
  }

  virtual void removeObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                    rti1516::VariableLengthData const & theUserSuppliedTag,
                                    rti1516::OrderType sentOrder,
                                    rti1516::LogicalTime const & theTime,
                                    rti1516::OrderType receivedOrder,
                                    rti1516::MessageRetractionHandle theHandle)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributesInScope(rti1516::ObjectInstanceHandle theObject,
                                 rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributesOutOfScope(rti1516::ObjectInstanceHandle theObject,
                                    rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void provideAttributeValueUpdate(rti1516::ObjectInstanceHandle theObject,
                                           rti1516::AttributeHandleSet const & theAttributes,
                                           rti1516::VariableLengthData const & theUserSuppliedTag)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::FederateInternalError)
  {
  }

  virtual void turnUpdatesOnForObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                              rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::FederateInternalError)
  {
  }

  virtual void turnUpdatesOffForObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                               rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::FederateInternalError)
  {
  }

  virtual void requestAttributeOwnershipAssumption(rti1516::ObjectInstanceHandle theObject,
                                                   rti1516::AttributeHandleSet const & offeredAttributes,
                                                   rti1516::VariableLengthData const & theUserSuppliedTag)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeAlreadyOwned,
           rti1516::AttributeNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void requestDivestitureConfirmation(rti1516::ObjectInstanceHandle theObject,
                                              rti1516::AttributeHandleSet const & releasedAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::AttributeDivestitureWasNotRequested,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributeOwnershipAcquisitionNotification(rti1516::ObjectInstanceHandle theObject,
                                                         rti1516::AttributeHandleSet const & securedAttributes,
                                                         rti1516::VariableLengthData const & theUserSuppliedTag)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeAcquisitionWasNotRequested,
           rti1516::AttributeAlreadyOwned,
           rti1516::AttributeNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributeOwnershipUnavailable(rti1516::ObjectInstanceHandle theObject,
                                             rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeAlreadyOwned,
           rti1516::AttributeAcquisitionWasNotRequested,
           rti1516::FederateInternalError)
  {
  }

  virtual void requestAttributeOwnershipRelease(rti1516::ObjectInstanceHandle theObject,
                                                rti1516::AttributeHandleSet const & candidateAttributes,
                                                rti1516::VariableLengthData const & theUserSuppliedTag)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::FederateInternalError)
  {
  }

  virtual void confirmAttributeOwnershipAcquisitionCancellation(rti1516::ObjectInstanceHandle theObject,
                                                                rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeAlreadyOwned,
           rti1516::AttributeAcquisitionWasNotCanceled,
           rti1516::FederateInternalError)
  {
  }

  virtual void informAttributeOwnership(rti1516::ObjectInstanceHandle theObject,
                                        rti1516::AttributeHandle theAttribute,
                                        rti1516::FederateHandle theOwner)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributeIsNotOwned(rti1516::ObjectInstanceHandle theObject,
                                   rti1516::AttributeHandle theAttribute)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributeIsOwnedByRTI(rti1516::ObjectInstanceHandle theObject,
                                     rti1516::AttributeHandle theAttribute)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::FederateInternalError)
  {
  }

  virtual void timeRegulationEnabled(rti1516::LogicalTime const & theFederateTime)
    throw (rti1516::InvalidLogicalTime,
           rti1516::NoRequestToEnableTimeRegulationWasPending,
           rti1516::FederateInternalError)
  {
  }

  virtual void timeConstrainedEnabled(rti1516::LogicalTime const & theFederateTime)
    throw (rti1516::InvalidLogicalTime,
           rti1516::NoRequestToEnableTimeConstrainedWasPending,
           rti1516::FederateInternalError)
  {
  }

  virtual void timeAdvanceGrant(rti1516::LogicalTime const & theTime)
    throw (rti1516::InvalidLogicalTime,
           rti1516::JoinedFederateIsNotInTimeAdvancingState,
           rti1516::FederateInternalError)
  {
  }

  virtual void requestRetraction(rti1516::MessageRetractionHandle theHandle)
    throw (rti1516::FederateInternalError)
  {
  }

private:
  unsigned _synchronized;
  std::set<std::wstring> _federateSet;
};

class RTI1516SimpleAmbassador : public rti1516::FederateAmbassador {
public:
  RTI1516SimpleAmbassador() :
    _timeRegulationEnabled(false),
    _timeConstrainedEnabled(false),
    _timeAdvancePending(false)
  { }
  virtual ~RTI1516SimpleAmbassador()
    throw ()
  { }

  void createAmbassador(std::vector<std::wstring> args)
  {
    rti1516::RTIambassadorFactory factory;
    _ambassador = factory.createRTIambassador(args);
    setLogicalTimeFactory();
  }

  void setLogicalTimeFactory(const std::wstring& logicalTimeImplementationName = std::wstring())
  {
    _logicalTimeImplementationName = logicalTimeImplementationName;
    _logicalTimeFactory = rti1516::LogicalTimeFactoryFactory::makeLogicalTimeFactory(logicalTimeImplementationName);
  }

  void createFederationExecution(const std::wstring& federationExecutionName, const std::wstring& fddFile)
  {
    _ambassador->createFederationExecution(federationExecutionName, fddFile, _logicalTimeImplementationName);
  }

  void destroyFederationExecution(const std::wstring& federationExecutionName)
  {
    _ambassador->destroyFederationExecution(federationExecutionName);
  }

  rti1516::FederateHandle joinFederationExecution(const std::wstring& federateType, const std::wstring& federationExecutionName)
  {
    rti1516::FederateHandle federateHandle = _ambassador->joinFederationExecution(federateType, federationExecutionName, *this);
    _grantedLogicalTime = _logicalTimeFactory->makeLogicalTime();
    _grantedLogicalTime->setInitial();
    return federateHandle;
  }

  void resignFederationExecution(rti1516::ResignAction resignAction)
  {
    _ambassador->resignFederationExecution(resignAction);
  }

    // // 4.6
    //  void registerFederationSynchronizationPoint
    // (std::wstring const & label,
    //  VariableLengthData const & theUserSuppliedTag)
    //   throw (FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  void registerFederationSynchronizationPoint
    // (std::wstring const & label,
    //  VariableLengthData const & theUserSuppliedTag,
    //  FederateHandleSet const & syncSet)
    //   throw (FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 4.9
    //  void synchronizationPointAchieved
    // (std::wstring const & label)
    //   throw (SynchronizationPointLabelNotAnnounced,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 4.11
    //  void requestFederationSave
    // (std::wstring const & label)
    //   throw (FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  void requestFederationSave
    // (std::wstring const & label,
    //  LogicalTime const & theTime)
    //   throw (LogicalTimeAlreadyPassed,
    //          InvalidLogicalTime,
    //          FederateUnableToUseTime,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 4.13
    //  void federateSaveBegun ()
    //   throw (SaveNotInitiated,
    //          FederateNotExecutionMember,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 4.14
    //  void federateSaveComplete ()
    //   throw (FederateHasNotBegunSave,
    //          FederateNotExecutionMember,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  void federateSaveNotComplete()
    //   throw (FederateHasNotBegunSave,
    //          FederateNotExecutionMember,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 4.16
    //  void queryFederationSaveStatus ()
    //   throw (FederateNotExecutionMember,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 4.18
    //  void requestFederationRestore
    // (std::wstring const & label)
    //   throw (FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 4.22
    //  void federateRestoreComplete ()
    //   throw (RestoreNotRequested,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RTIinternalError) = 0;

    //  void federateRestoreNotComplete ()
    //   throw (RestoreNotRequested,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RTIinternalError) = 0;

    // // 4.24
    //  void queryFederationRestoreStatus ()
    //   throw (FederateNotExecutionMember,
    //          SaveInProgress,
    //          RTIinternalError) = 0;

    // /////////////////////////////////////
    // // Declaration Management Services //
    // /////////////////////////////////////

    // // 5.2
    //  void publishObjectClassAttributes
    // (ObjectClassHandle theClass,
    //  AttributeHandleSet const & attributeList)
    //   throw (ObjectClassNotDefined,
    //          AttributeNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 5.3
    //  void unpublishObjectClass
    // (ObjectClassHandle theClass)
    //   throw (ObjectClassNotDefined,
    //          OwnershipAcquisitionPending,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  void unpublishObjectClassAttributes
    // (ObjectClassHandle theClass,
    //  AttributeHandleSet const & attributeList)
    //   throw (ObjectClassNotDefined,
    //          AttributeNotDefined,
    //          OwnershipAcquisitionPending,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 5.4
    //  void publishInteractionClass
    // (InteractionClassHandle theInteraction)
    //   throw (InteractionClassNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 5.5
    //  void unpublishInteractionClass
    // (InteractionClassHandle theInteraction)
    //   throw (InteractionClassNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 5.6
    //  void subscribeObjectClassAttributes
    // (ObjectClassHandle theClass,
    //  AttributeHandleSet const & attributeList,
    //  bool active = true)
    //   throw (ObjectClassNotDefined,
    //          AttributeNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 5.7
    //  void unsubscribeObjectClass
    // (ObjectClassHandle theClass)
    //   throw (ObjectClassNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  void unsubscribeObjectClassAttributes
    // (ObjectClassHandle theClass,
    //  AttributeHandleSet const & attributeList)
    //   throw (ObjectClassNotDefined,
    //          AttributeNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 5.8
    //  void subscribeInteractionClass
    // (InteractionClassHandle theClass,
    //  bool active = true)
    //   throw (InteractionClassNotDefined,
    //          FederateServiceInvocationsAreBeingReportedViaMOM,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 5.9
    //  void unsubscribeInteractionClass
    // (InteractionClassHandle theClass)
    //   throw (InteractionClassNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // ////////////////////////////////
    // // Object Management Services //
    // ////////////////////////////////

    // // 6.2
    //  void reserveObjectInstanceName
    // (std::wstring const & theObjectInstanceName)
    //   throw (IllegalName,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 6.4
    //  ObjectInstanceHandle registerObjectInstance
    // (ObjectClassHandle theClass)
    //   throw (ObjectClassNotDefined,
    //          ObjectClassNotPublished,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  ObjectInstanceHandle registerObjectInstance
    // (ObjectClassHandle theClass,
    //  std::wstring const & theObjectInstanceName)
    //   throw (ObjectClassNotDefined,
    //          ObjectClassNotPublished,
    //          ObjectInstanceNameNotReserved,
    //          ObjectInstanceNameInUse,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 6.6
    //  void updateAttributeValues
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleValueMap const & theAttributeValues,
    //  VariableLengthData const & theUserSuppliedTag)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          AttributeNotOwned,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  MessageRetractionHandle updateAttributeValues
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleValueMap const & theAttributeValues,
    //  VariableLengthData const & theUserSuppliedTag,
    //  LogicalTime const & theTime)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          AttributeNotOwned,
    //          InvalidLogicalTime,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 6.8
    //  void sendInteraction
    // (InteractionClassHandle theInteraction,
    //  ParameterHandleValueMap const & theParameterValues,
    //  VariableLengthData const & theUserSuppliedTag)
    //   throw (InteractionClassNotPublished,
    //          InteractionClassNotDefined,
    //          InteractionParameterNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  MessageRetractionHandle sendInteraction
    // (InteractionClassHandle theInteraction,
    //  ParameterHandleValueMap const & theParameterValues,
    //  VariableLengthData const & theUserSuppliedTag,
    //  LogicalTime const & theTime)
    //   throw (InteractionClassNotPublished,
    //          InteractionClassNotDefined,
    //          InteractionParameterNotDefined,
    //          InvalidLogicalTime,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 6.10
    //  void deleteObjectInstance
    // (ObjectInstanceHandle theObject,
    //  VariableLengthData const & theUserSuppliedTag)
    //   throw (DeletePrivilegeNotHeld,
    //          ObjectInstanceNotKnown,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  MessageRetractionHandle deleteObjectInstance
    // (ObjectInstanceHandle theObject,
    //  VariableLengthData const & theUserSuppliedTag,
    //  LogicalTime  const & theTime)
    //   throw (DeletePrivilegeNotHeld,
    //          ObjectInstanceNotKnown,
    //          InvalidLogicalTime,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 6.12
    //  void localDeleteObjectInstance
    // (ObjectInstanceHandle theObject)
    //   throw (ObjectInstanceNotKnown,
    //          FederateOwnsAttributes,
    //          OwnershipAcquisitionPending,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 6.13
    //  void changeAttributeTransportationType
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & theAttributes,
    //  TransportationType theType)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          AttributeNotOwned,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 6.14
    //  void changeInteractionTransportationType
    // (InteractionClassHandle theClass,
    //  TransportationType theType)
    //   throw (InteractionClassNotDefined,
    //          InteractionClassNotPublished,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 6.17
    //  void requestAttributeValueUpdate
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & theAttributes,
    //  VariableLengthData const & theUserSuppliedTag)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    //  void requestAttributeValueUpdate
    // (ObjectClassHandle theClass,
    //  AttributeHandleSet const & theAttributes,
    //  VariableLengthData const & theUserSuppliedTag)
    //   throw (ObjectClassNotDefined,
    //          AttributeNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // ///////////////////////////////////
    // // Ownership Management Services //
    // ///////////////////////////////////
    // // 7.2
    //  void unconditionalAttributeOwnershipDivestiture
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & theAttributes)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          AttributeNotOwned,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 7.3
    //  void negotiatedAttributeOwnershipDivestiture
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & theAttributes,
    //  VariableLengthData const & theUserSuppliedTag)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          AttributeNotOwned,
    //          AttributeAlreadyBeingDivested,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 7.6
    //  void confirmDivestiture
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & confirmedAttributes,
    //  VariableLengthData const & theUserSuppliedTag)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          AttributeNotOwned,
    //          AttributeDivestitureWasNotRequested,
    //          NoAcquisitionPending,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 7.8
    //  void attributeOwnershipAcquisition
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & desiredAttributes,
    //  VariableLengthData const & theUserSuppliedTag)
    //   throw (ObjectInstanceNotKnown,
    //          ObjectClassNotPublished,
    //          AttributeNotDefined,
    //          AttributeNotPublished,
    //          FederateOwnsAttributes,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 7.9
    //  void attributeOwnershipAcquisitionIfAvailable
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & desiredAttributes)
    //   throw (ObjectInstanceNotKnown,
    //          ObjectClassNotPublished,
    //          AttributeNotDefined,
    //          AttributeNotPublished,
    //          FederateOwnsAttributes,
    //          AttributeAlreadyBeingAcquired,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 7.12
    //  void attributeOwnershipDivestitureIfWanted
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & theAttributes,
    //  AttributeHandleSet & theDivestedAttributes) // filled by RTI
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          AttributeNotOwned,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 7.13
    //  void cancelNegotiatedAttributeOwnershipDivestiture
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & theAttributes)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          AttributeNotOwned,
    //          AttributeDivestitureWasNotRequested,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 7.14
    //  void cancelAttributeOwnershipAcquisition
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & theAttributes)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          AttributeAlreadyOwned,
    //          AttributeAcquisitionWasNotRequested,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 7.16
    //  void queryAttributeOwnership
    // (ObjectInstanceHandle theObject,
    //  AttributeHandle theAttribute)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

    // // 7.18
    //  bool isAttributeOwnedByFederate
    // (ObjectInstanceHandle theObject,
    //  AttributeHandle theAttribute)
    //   throw (ObjectInstanceNotKnown,
    //          AttributeNotDefined,
    //          FederateNotExecutionMember,
    //          SaveInProgress,
    //          RestoreInProgress,
    //          RTIinternalError) = 0;

  void enableTimeRegulation(const rti1516::LogicalTimeInterval& logicalTimeInterval)
  {
    _ambassador->enableTimeRegulation(logicalTimeInterval);
  }

  void disableTimeRegulation()
  {
    _timeRegulationEnabled = false;
    _ambassador->disableTimeRegulation();
  }

  void enableTimeConstrained()
  {
    _ambassador->enableTimeConstrained();
  }

  void disableTimeConstrained()
  {
    _timeConstrainedEnabled = false;
    _ambassador->disableTimeConstrained();
  }

  void timeAdvanceRequest(const rti1516::LogicalTime& logicalTime)
  {
    _timeAdvancePending = true;
    _ambassador->timeAdvanceRequest(logicalTime);
  }

  bool getTimeAdvancePending() const
  { return _timeAdvancePending; }
  bool verifyGrantedLogicalTime(const rti1516::LogicalTime& logicalTime) const
  { return logicalTime == *_grantedLogicalTime; }

  void timeAdvanceRequestAvailable(const rti1516::LogicalTime& logicalTime)
  {
    _timeAdvancePending = true;
    _ambassador->timeAdvanceRequestAvailable(logicalTime);
  }

  void nextMessageRequest(const rti1516::LogicalTime& logicalTime)
  {
    _timeAdvancePending = true;
    _ambassador->nextMessageRequest(logicalTime);
  }

  void nextMessageRequestAvailable(const rti1516::LogicalTime& logicalTime)
  {
    _timeAdvancePending = true;
    _ambassador->nextMessageRequestAvailable(logicalTime);
  }

    //  void flushQueueRequest
    // (LogicalTime const & theTime)

    //  void enableAsynchronousDelivery ()

    //  void disableAsynchronousDelivery ()

    //  bool queryGALT (LogicalTime & theTime)

    //  void queryLogicalTime (LogicalTime & theTime)

    //  bool queryLITS (LogicalTime & theTime)

    //  void modifyLookahead

    //  void queryLookahead (LogicalTimeInterval & interval)

    //  void retract
    // (MessageRetractionHandle theHandle)

    //  void changeAttributeOrderType
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSet const & theAttributes,
    //  OrderType theType)

    //  void changeInteractionOrderType
    // (InteractionClassHandle theClass,
    //  OrderType theType)

    //  RegionHandle createRegion
    // (DimensionHandleSet const & theDimensions)

    //  void commitRegionModifications
    // (RegionHandleSet const & theRegionHandleSet)

    //  void deleteRegion
    // (RegionHandle theRegion)

    //  ObjectInstanceHandle registerObjectInstanceWithRegions
    // (ObjectClassHandle theClass,
    //  AttributeHandleSetRegionHandleSetPairVector const &
    //  theAttributeHandleSetRegionHandleSetPairVector)

    //  ObjectInstanceHandle registerObjectInstanceWithRegions
    // (ObjectClassHandle theClass,
    //  AttributeHandleSetRegionHandleSetPairVector const &
    //  theAttributeHandleSetRegionHandleSetPairVector,
    //  std::wstring const & theObjectInstanceName)

    //  void associateRegionsForUpdates
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSetRegionHandleSetPairVector const &
    //  theAttributeHandleSetRegionHandleSetPairVector)

    //  void unassociateRegionsForUpdates
    // (ObjectInstanceHandle theObject,
    //  AttributeHandleSetRegionHandleSetPairVector const &
    //  theAttributeHandleSetRegionHandleSetPairVector)

    //  void subscribeObjectClassAttributesWithRegions
    // (ObjectClassHandle theClass,
    //  AttributeHandleSetRegionHandleSetPairVector const &
    //  theAttributeHandleSetRegionHandleSetPairVector,
    //  bool active = true)

    //  void unsubscribeObjectClassAttributesWithRegions
    // (ObjectClassHandle theClass,
    //  AttributeHandleSetRegionHandleSetPairVector const &
    //  theAttributeHandleSetRegionHandleSetPairVector)

    //  void subscribeInteractionClassWithRegions
    // (InteractionClassHandle theClass,
    //  RegionHandleSet const & theRegionHandleSet,
    //  bool active = true)

    //  void unsubscribeInteractionClassWithRegions
    // (InteractionClassHandle theClass,
    //  RegionHandleSet const & theRegionHandleSet)

    //  void sendInteractionWithRegions
    // (InteractionClassHandle theInteraction,
    //  ParameterHandleValueMap const & theParameterValues,
    //  RegionHandleSet const & theRegionHandleSet,
    //  VariableLengthData const & theUserSuppliedTag)

    //  MessageRetractionHandle sendInteractionWithRegions
    // (InteractionClassHandle theInteraction,
    //  ParameterHandleValueMap const & theParameterValues,
    //  RegionHandleSet const & theRegionHandleSet,
    //  VariableLengthData const & theUserSuppliedTag,
    //  LogicalTime const & theTime)

    //  void requestAttributeValueUpdateWithRegions
    // (ObjectClassHandle theClass,
    //  AttributeHandleSetRegionHandleSetPairVector const & theSet,
    //  VariableLengthData const & theUserSuppliedTag)

  rti1516::ObjectClassHandle getObjectClassHandle(std::wstring const & theName)
  {
    return _ambassador->getObjectClassHandle(theName);
  }

  std::wstring getObjectClassName(rti1516::ObjectClassHandle theHandle)
  {
    return _ambassador->getObjectClassName(theHandle);
  }

  rti1516::AttributeHandle getAttributeHandle(rti1516::ObjectClassHandle whichClass, std::wstring const & theAttributeName)
  {
    return _ambassador->getAttributeHandle(whichClass, theAttributeName);
  }

  std::wstring getAttributeName(rti1516::ObjectClassHandle whichClass, rti1516::AttributeHandle theHandle)
  {
    return _ambassador->getAttributeName(whichClass, theHandle);
  }

  rti1516::InteractionClassHandle getInteractionClassHandle(std::wstring const & theName)
  {
    return _ambassador->getInteractionClassHandle(theName);
  }

  std::wstring getInteractionClassName(rti1516::InteractionClassHandle theHandle)
  {
    return _ambassador->getInteractionClassName(theHandle);
  }

  rti1516::ParameterHandle getParameterHandle(rti1516::InteractionClassHandle whichClass, std::wstring const & theName)
  {
    return _ambassador->getParameterHandle(whichClass, theName);
  }

  std::wstring getParameterName(rti1516::InteractionClassHandle whichClass, rti1516::ParameterHandle theHandle)
  {
    return _ambassador->getParameterName(whichClass, theHandle);
  }

  rti1516::ObjectInstanceHandle getObjectInstanceHandle(std::wstring const & theName)
  {
    return _ambassador->getObjectInstanceHandle(theName);
  }

  std::wstring getObjectInstanceName(rti1516::ObjectInstanceHandle theHandle)
  {
    return _ambassador->getObjectInstanceName(theHandle);
  }

  rti1516::DimensionHandle getDimensionHandle(std::wstring const & theName)
  {
    return _ambassador->getDimensionHandle(theName);
  }

  std::wstring getDimensionName(rti1516::DimensionHandle theHandle)
  {
    return _ambassador->getDimensionName(theHandle);
  }

  unsigned long getDimensionUpperBound(rti1516::DimensionHandle theHandle)
  {
    return _ambassador->getDimensionUpperBound(theHandle);
  }

  rti1516::DimensionHandleSet getAvailableDimensionsForClassAttribute(rti1516::ObjectClassHandle theClass,
                                                                      rti1516::AttributeHandle theHandle)
  {
    return _ambassador->getAvailableDimensionsForClassAttribute(theClass, theHandle);
  }

  rti1516::ObjectClassHandle getKnownObjectClassHandle(rti1516::ObjectInstanceHandle theObject)
  {
    return _ambassador->getKnownObjectClassHandle(theObject);
  }

  rti1516::DimensionHandleSet getAvailableDimensionsForInteractionClass(rti1516::InteractionClassHandle theClass)
  {
    return _ambassador->getAvailableDimensionsForInteractionClass(theClass);
  }

  rti1516::TransportationType getTransportationType(std::wstring const & transportationName)
  {
    return _ambassador->getTransportationType(transportationName);
  }

  std::wstring getTransportationName(rti1516::TransportationType transportationType)
  {
    return _ambassador->getTransportationName(transportationType);
  }

  rti1516::OrderType getOrderType(std::wstring const & orderName)
  {
    return _ambassador->getOrderType(orderName);
  }

  std::wstring getOrderName(rti1516::OrderType orderType)
  {
    return _ambassador->getOrderName(orderType);
  }

  void enableObjectClassRelevanceAdvisorySwitch()
  {
    _ambassador->enableObjectClassRelevanceAdvisorySwitch();
  }

  void disableObjectClassRelevanceAdvisorySwitch()
  {
    _ambassador->disableObjectClassRelevanceAdvisorySwitch();
  }

  void enableAttributeRelevanceAdvisorySwitch()
  {
    _ambassador->enableAttributeRelevanceAdvisorySwitch();
  }

  void disableAttributeRelevanceAdvisorySwitch()
  {
    _ambassador->disableAttributeRelevanceAdvisorySwitch();
  }

  void enableAttributeScopeAdvisorySwitch()
  {
    _ambassador->enableAttributeScopeAdvisorySwitch();
  }

  void disableAttributeScopeAdvisorySwitch()
  {
    _ambassador->disableAttributeScopeAdvisorySwitch();
  }

  void enableInteractionRelevanceAdvisorySwitch()
  {
    _ambassador->enableInteractionRelevanceAdvisorySwitch();
  }

  void disableInteractionRelevanceAdvisorySwitch()
  {
    _ambassador->disableInteractionRelevanceAdvisorySwitch();
  }

  rti1516::DimensionHandleSet getDimensionHandleSet(rti1516::RegionHandle regionHandle)
  {
    return _ambassador->getDimensionHandleSet(regionHandle);
  }

  rti1516::RangeBounds getRangeBounds(rti1516::RegionHandle theRegionHandle, rti1516::DimensionHandle theDimensionHandle)
  {
    return _ambassador->getRangeBounds(theRegionHandle, theDimensionHandle);
  }

  void setRangeBounds(rti1516::RegionHandle theRegionHandle, rti1516::DimensionHandle theDimensionHandle,
                      rti1516::RangeBounds const & theRangeBounds)
  {
    return _ambassador->setRangeBounds(theRegionHandle, theDimensionHandle, theRangeBounds);
  }

  unsigned long normalizeFederateHandle(rti1516::FederateHandle theFederateHandle)
  {
    return _ambassador->normalizeFederateHandle(theFederateHandle);
  }

  unsigned long normalizeServiceGroup(rti1516::ServiceGroupIndicator theServiceGroup)
  {
    return _ambassador->normalizeServiceGroup(theServiceGroup);
  }

  bool evokeCallback(double approximateMinimumTimeInSeconds)
  {
    return _ambassador->evokeCallback(approximateMinimumTimeInSeconds);
  }

  bool evokeMultipleCallbacks(double approximateMinimumTimeInSeconds,
                              double approximateMaximumTimeInSeconds)
  {
    return _ambassador->evokeMultipleCallbacks(approximateMinimumTimeInSeconds, approximateMaximumTimeInSeconds);
  }

  void enableCallbacks()
  {
    _ambassador->enableCallbacks();
  }

  void disableCallbacks()
  {
    _ambassador->disableCallbacks();
  }

  rti1516::FederateHandle decodeFederateHandle(rti1516::VariableLengthData const& encodedValue) const
  {
    return _ambassador->decodeFederateHandle(encodedValue);
  }

  rti1516::ObjectClassHandle decodeObjectClassHandle(rti1516::VariableLengthData const & encodedValue) const
  {
    return _ambassador->decodeObjectClassHandle(encodedValue);
  }

  rti1516::InteractionClassHandle decodeInteractionClassHandle(rti1516::VariableLengthData const & encodedValue) const
  {
    return _ambassador->decodeInteractionClassHandle(encodedValue);
  }

  rti1516::ObjectInstanceHandle decodeObjectInstanceHandle(rti1516::VariableLengthData const & encodedValue) const
  {
    return _ambassador->decodeObjectInstanceHandle(encodedValue);
  }

  rti1516::AttributeHandle decodeAttributeHandle(rti1516::VariableLengthData const & encodedValue) const
  {
    return _ambassador->decodeAttributeHandle(encodedValue);
  }

  rti1516::ParameterHandle decodeParameterHandle(rti1516::VariableLengthData const & encodedValue) const
  {
    return _ambassador->decodeParameterHandle(encodedValue);
  }

  rti1516::DimensionHandle decodeDimensionHandle(rti1516::VariableLengthData const & encodedValue) const
  {
    return _ambassador->decodeDimensionHandle(encodedValue);
  }

  rti1516::MessageRetractionHandle decodeMessageRetractionHandle(rti1516::VariableLengthData const & encodedValue) const
  {
    return _ambassador->decodeMessageRetractionHandle(encodedValue);
  }

  rti1516::RegionHandle decodeRegionHandle(rti1516::VariableLengthData const & encodedValue) const
  {
    return _ambassador->decodeRegionHandle(encodedValue);
  }

protected:
  virtual void synchronizationPointRegistrationSucceeded(const std::wstring& label)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void synchronizationPointRegistrationFailed(const std::wstring& label, rti1516::SynchronizationFailureReason reason)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void announceSynchronizationPoint(const std::wstring& label, const rti1516::VariableLengthData& tag)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationSynchronized(const std::wstring& label)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void initiateFederateSave(const std::wstring& label)
      throw (rti1516::UnableToPerformSave,
             rti1516::FederateInternalError)
  {
  }

  virtual void initiateFederateSave(const std::wstring& label, const rti1516::LogicalTime& logicalTime)
      throw (rti1516::UnableToPerformSave,
             rti1516::InvalidLogicalTime,
             rti1516::FederateInternalError)
  {
  }

  virtual void federationSaved()
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationNotSaved(rti1516::SaveFailureReason theSaveFailureReason)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationSaveStatusResponse(const rti1516::FederateHandleSaveStatusPairVector& theFederateStatusVector)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void requestFederationRestoreSucceeded(const std::wstring& label)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void requestFederationRestoreFailed(const std::wstring& label)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationRestoreBegun()
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void initiateFederateRestore(const std::wstring& label, rti1516::FederateHandle handle)
    throw (rti1516::SpecifiedSaveLabelDoesNotExist,
           rti1516::CouldNotInitiateRestore,
           rti1516::FederateInternalError)
  {
  }

  virtual void federationRestored()
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationNotRestored(rti1516::RestoreFailureReason theRestoreFailureReason)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void federationRestoreStatusResponse(const rti1516::FederateHandleRestoreStatusPairVector& theFederateStatusVector)
    throw (rti1516::FederateInternalError)
  {
  }

  virtual void startRegistrationForObjectClass(rti1516::ObjectClassHandle)
    throw (rti1516::ObjectClassNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void stopRegistrationForObjectClass(rti1516::ObjectClassHandle)
    throw (rti1516::ObjectClassNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void turnInteractionsOn(rti1516::InteractionClassHandle)
    throw (rti1516::InteractionClassNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void turnInteractionsOff(rti1516::InteractionClassHandle)
    throw (rti1516::InteractionClassNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void objectInstanceNameReservationSucceeded(const std::wstring&)
    throw (rti1516::UnknownName,
           rti1516::FederateInternalError)
  {
  }

  virtual void objectInstanceNameReservationFailed(const std::wstring&)
    throw (rti1516::UnknownName,
           rti1516::FederateInternalError)
  {
  }

  virtual void discoverObjectInstance(rti1516::ObjectInstanceHandle, rti1516::ObjectClassHandle, const std::wstring&)
    throw (rti1516::CouldNotDiscover,
           rti1516::ObjectClassNotKnown,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle objectInstanceHandle,
                                      const rti1516::AttributeHandleValueMap& attributeHandleValueMap,
                                      const rti1516::VariableLengthData& tag, rti1516::OrderType, rti1516::TransportationType)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
    // _objectInstanceMap[objectInstanceHandle]._tag = tag;
    // _objectInstanceMap[objectInstanceHandle]._attributeHandleValueMap = attributeHandleValueMap;
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::RegionHandleSet&)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::LogicalTime&, rti1516::OrderType)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::LogicalTime&, rti1516::OrderType, const rti1516::RegionHandleSet&)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::LogicalTime&, rti1516::OrderType, rti1516::MessageRetractionHandle)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void reflectAttributeValues(rti1516::ObjectInstanceHandle, const rti1516::AttributeHandleValueMap&,
                                      const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                      const rti1516::LogicalTime&, rti1516::OrderType, rti1516::MessageRetractionHandle,
                                      const rti1516::RegionHandleSet&)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle, const rti1516::ParameterHandleValueMap&,
                                  const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle, const rti1516::ParameterHandleValueMap&,
                                  const rti1516::VariableLengthData&, rti1516::OrderType, rti1516::TransportationType,
                                  const rti1516::RegionHandleSet&)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle theInteraction,
                                  rti1516::ParameterHandleValueMap const & theParameterValues,
                                  rti1516::VariableLengthData const & theUserSuppliedTag,
                                  rti1516::OrderType sentOrder,
                                  rti1516::TransportationType theType,
                                  rti1516::LogicalTime const & theTime,
                                  rti1516::OrderType receivedOrder)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle theInteraction,
                                  rti1516::ParameterHandleValueMap const & theParameterValues,
                                  rti1516::VariableLengthData const & theUserSuppliedTag,
                                  rti1516::OrderType sentOrder,
                                  rti1516::TransportationType theType,
                                  rti1516::LogicalTime const & theTime,
                                  rti1516::OrderType receivedOrder,
                                  rti1516::RegionHandleSet const & theSentRegionHandleSet)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle theInteraction,
                                  rti1516::ParameterHandleValueMap const & theParameterValues,
                                  rti1516::VariableLengthData const & theUserSuppliedTag,
                                  rti1516::OrderType sentOrder,
                                  rti1516::TransportationType theType,
                                  rti1516::LogicalTime const & theTime,
                                  rti1516::OrderType receivedOrder,
                                  rti1516::MessageRetractionHandle theHandle)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void receiveInteraction(rti1516::InteractionClassHandle theInteraction,
                                  rti1516::ParameterHandleValueMap const & theParameterValues,
                                  rti1516::VariableLengthData const & theUserSuppliedTag,
                                  rti1516::OrderType sentOrder,
                                  rti1516::TransportationType theType,
                                  rti1516::LogicalTime const & theTime,
                                  rti1516::OrderType receivedOrder,
                                  rti1516::MessageRetractionHandle theHandle,
                                  rti1516::RegionHandleSet const & theSentRegionHandleSet)
    throw (rti1516::InteractionClassNotRecognized,
           rti1516::InteractionParameterNotRecognized,
           rti1516::InteractionClassNotSubscribed,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void removeObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                    rti1516::VariableLengthData const & theUserSuppliedTag,
                                    rti1516::OrderType sentOrder)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::FederateInternalError)
  {
  }

  virtual void removeObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                    rti1516::VariableLengthData const & theUserSuppliedTag,
                                    rti1516::OrderType sentOrder,
                                    rti1516::LogicalTime const & theTime,
                                    rti1516::OrderType receivedOrder)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::FederateInternalError)
  {
  }

  virtual void removeObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                    rti1516::VariableLengthData const & theUserSuppliedTag,
                                    rti1516::OrderType sentOrder,
                                    rti1516::LogicalTime const & theTime,
                                    rti1516::OrderType receivedOrder,
                                    rti1516::MessageRetractionHandle theHandle)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::InvalidLogicalTime,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributesInScope(rti1516::ObjectInstanceHandle theObject,
                                 rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributesOutOfScope(rti1516::ObjectInstanceHandle theObject,
                                    rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotSubscribed,
           rti1516::FederateInternalError)
  {
  }

  virtual void provideAttributeValueUpdate(rti1516::ObjectInstanceHandle theObject,
                                           rti1516::AttributeHandleSet const & theAttributes,
                                           rti1516::VariableLengthData const & theUserSuppliedTag)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::FederateInternalError)
  {
  }

  virtual void turnUpdatesOnForObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                              rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::FederateInternalError)
  {
  }

  virtual void turnUpdatesOffForObjectInstance(rti1516::ObjectInstanceHandle theObject,
                                               rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::FederateInternalError)
  {
  }

  virtual void requestAttributeOwnershipAssumption(rti1516::ObjectInstanceHandle theObject,
                                                   rti1516::AttributeHandleSet const & offeredAttributes,
                                                   rti1516::VariableLengthData const & theUserSuppliedTag)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeAlreadyOwned,
           rti1516::AttributeNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void requestDivestitureConfirmation(rti1516::ObjectInstanceHandle theObject,
                                              rti1516::AttributeHandleSet const & releasedAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::AttributeDivestitureWasNotRequested,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributeOwnershipAcquisitionNotification(rti1516::ObjectInstanceHandle theObject,
                                                         rti1516::AttributeHandleSet const & securedAttributes,
                                                         rti1516::VariableLengthData const & theUserSuppliedTag)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeAcquisitionWasNotRequested,
           rti1516::AttributeAlreadyOwned,
           rti1516::AttributeNotPublished,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributeOwnershipUnavailable(rti1516::ObjectInstanceHandle theObject,
                                             rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeAlreadyOwned,
           rti1516::AttributeAcquisitionWasNotRequested,
           rti1516::FederateInternalError)
  {
  }

  virtual void requestAttributeOwnershipRelease(rti1516::ObjectInstanceHandle theObject,
                                                rti1516::AttributeHandleSet const & candidateAttributes,
                                                rti1516::VariableLengthData const & theUserSuppliedTag)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeNotOwned,
           rti1516::FederateInternalError)
  {
  }

  virtual void confirmAttributeOwnershipAcquisitionCancellation(rti1516::ObjectInstanceHandle theObject,
                                                                rti1516::AttributeHandleSet const & theAttributes)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::AttributeAlreadyOwned,
           rti1516::AttributeAcquisitionWasNotCanceled,
           rti1516::FederateInternalError)
  {
  }

  virtual void informAttributeOwnership(rti1516::ObjectInstanceHandle theObject,
                                        rti1516::AttributeHandle theAttribute,
                                        rti1516::FederateHandle theOwner)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributeIsNotOwned(rti1516::ObjectInstanceHandle theObject,
                                   rti1516::AttributeHandle theAttribute)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::FederateInternalError)
  {
  }

  virtual void attributeIsOwnedByRTI(rti1516::ObjectInstanceHandle theObject,
                                     rti1516::AttributeHandle theAttribute)
    throw (rti1516::ObjectInstanceNotKnown,
           rti1516::AttributeNotRecognized,
           rti1516::FederateInternalError)
  {
  }

  virtual void timeRegulationEnabled(const rti1516::LogicalTime& logicalTime)
    throw (rti1516::InvalidLogicalTime,
           rti1516::NoRequestToEnableTimeRegulationWasPending,
           rti1516::FederateInternalError)
  {
    _timeRegulationEnabled = true;
    *_grantedLogicalTime = logicalTime;
  }

  virtual void timeConstrainedEnabled(const rti1516::LogicalTime& logicalTime)
    throw (rti1516::InvalidLogicalTime,
           rti1516::NoRequestToEnableTimeConstrainedWasPending,
           rti1516::FederateInternalError)
  {
    _timeConstrainedEnabled = true;
    *_grantedLogicalTime = logicalTime;
  }

  virtual void timeAdvanceGrant(const rti1516::LogicalTime& logicalTime)
    throw (rti1516::InvalidLogicalTime,
           rti1516::JoinedFederateIsNotInTimeAdvancingState,
           rti1516::FederateInternalError)
  {
    _timeAdvancePending = false;
    *_grantedLogicalTime = logicalTime;
  }

  virtual void requestRetraction(rti1516::MessageRetractionHandle theHandle)
    throw (rti1516::FederateInternalError)
  {
  }

private:
  std::auto_ptr<rti1516::RTIambassador> _ambassador;

  std::wstring _logicalTimeImplementationName;
  std::auto_ptr<rti1516::LogicalTimeFactory> _logicalTimeFactory;

  std::auto_ptr<rti1516::LogicalTime> _grantedLogicalTime;
  bool _timeRegulationEnabled;
  bool _timeConstrainedEnabled;
  bool _timeAdvancePending;

  // struct ObjectInstance {
  //   rti1516::AttributeHandleValueMap _attributeHandleValueMap;
  //   rti1516::VariableLengthData _tag;
  //   rti1516::VariableLengthData _timeStamp;
  // };
  // typedef std::map<rti1516::ObjectInstanceHandle, ObjectInstance> ObjectInstanceMap;

  // ObjectInstanceMap _objectInstanceMap;
};

}

#endif