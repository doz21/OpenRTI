/*
 * FederateAmbassadorJava.cpp
 *
 *  Created on: 22.06.2017
 *      Author: V7PS4AG
 */


#include "FederateAmbassadorJava.hpp"

#include "Utils.hpp"

#include <iostream>

JavaVM* vm = NULL;

jobject FederateAmbassadorJava::createJavaTime(const LogicalTime& theTime) const
{
	if (L"HLAfloat64Time" == theTime.implementationName())
	{
		const HLAfloat64Time& castTime = dynamic_cast<const HLAfloat64Time&>(theTime);
		jclass cls = _env->FindClass("hla/rti1516e/time/openrti/DoubleTime");
		return _env->NewObject(cls, _env->GetMethodID(cls, "<init>", "(D)V"), (jdouble)castTime.getTime());
	}
	else
	{
		const HLAinteger64Time& castTime = dynamic_cast<const HLAinteger64Time&>(theTime);
		jclass cls = _env->FindClass("hla/rti1516e/time/openrti/LongTime");
		return _env->NewObject(cls, _env->GetMethodID(cls, "<init>", "(D)V"), (jlong)castTime.getTime());
	}
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved)
{
    vm = jvm;
    return JNI_VERSION_1_8;
}

FederateAmbassadorJava::FederateAmbassadorJava(JNIEnv* env, const jobject& object) throw (FederateInternalError)   : _env(env)
{
	_object = _env->NewGlobalRef(object);
	_class = _env->GetObjectClass(_object);

	callback_timeAdvanceGrant = env->GetMethodID(_class, "timeAdvanceGrant", "(Lhla/rti1516e/LogicalTime;)V");
	callback_timeRegulationEnabled = env->GetMethodID(_class, "timeRegulationEnabled", "(Lhla/rti1516e/LogicalTime;)V");
	callback_timeConstrainedEnabled = env->GetMethodID(_class, "timeConstrainedEnabled", "(Lhla/rti1516e/LogicalTime;)V");

	callback_discoverObjectInstance_3 = env->GetMethodID(_class, "discoverObjectInstance", "(Lhla/rti1516e/ObjectInstanceHandle;Lhla/rti1516e/ObjectClassHandle;Ljava/lang/String;)V");
	callback_discoverObjectInstance_4 = env->GetMethodID(_class, "discoverObjectInstance", "(Lhla/rti1516e/ObjectInstanceHandle;Lhla/rti1516e/ObjectClassHandle;Ljava/lang/String;Lhla/rti1516e/FederateHandle;)V");

	callback_removeObjectInstance_4 = env->GetMethodID(_class, "removeObjectInstance", "(Lhla/rti1516e/ObjectInstanceHandle;[BLhla/rti1516e/OrderType;Lhla/rti1516e/FederateAmbassador$SupplementalRemoveInfo;)V");
	callback_removeObjectInstance_6 = env->GetMethodID(_class, "removeObjectInstance", "(Lhla/rti1516e/ObjectInstanceHandle;[BLhla/rti1516e/OrderType;Lhla/rti1516e/LogicalTime;Lhla/rti1516e/OrderType;Lhla/rti1516e/FederateAmbassador$SupplementalRemoveInfo;)V");
	callback_removeObjectInstance_7 = env->GetMethodID(_class, "removeObjectInstance", "(Lhla/rti1516e/ObjectInstanceHandle;[BLhla/rti1516e/OrderType;Lhla/rti1516e/LogicalTime;Lhla/rti1516e/OrderType;Lhla/rti1516e/MessageRetractionHandle;Lhla/rti1516e/FederateAmbassador$SupplementalRemoveInfo;)V");

	callback_reflectAttributeValues_6 = env->GetMethodID(_class, "reflectAttributeValues", "(Lhla/rti1516e/ObjectInstanceHandle;Lhla/rti1516e/AttributeHandleValueMap;[BLhla/rti1516e/OrderType;Lhla/rti1516e/TransportationTypeHandle;Lhla/rti1516e/FederateAmbassador$SupplementalReflectInfo;)V");
	callback_reflectAttributeValues_8 = env->GetMethodID(_class, "reflectAttributeValues", "(Lhla/rti1516e/ObjectInstanceHandle;Lhla/rti1516e/AttributeHandleValueMap;[BLhla/rti1516e/OrderType;Lhla/rti1516e/TransportationTypeHandle;Lhla/rti1516e/LogicalTime;Lhla/rti1516e/OrderType;Lhla/rti1516e/FederateAmbassador$SupplementalReflectInfo;)V");
	callback_reflectAttributeValues_9 = env->GetMethodID(_class, "reflectAttributeValues", "(Lhla/rti1516e/ObjectInstanceHandle;Lhla/rti1516e/AttributeHandleValueMap;[BLhla/rti1516e/OrderType;Lhla/rti1516e/TransportationTypeHandle;Lhla/rti1516e/LogicalTime;Lhla/rti1516e/OrderType;Lhla/rti1516e/MessageRetractionHandle;Lhla/rti1516e/FederateAmbassador$SupplementalReflectInfo;)V");

	callback_receiveInteraction_6 = env->GetMethodID(_class, "receiveInteraction", "(Lhla/rti1516e/InteractionClassHandle;Lhla/rti1516e/ParameterHandleValueMap;[BLhla/rti1516e/OrderType;Lhla/rti1516e/TransportationTypeHandle;Lhla/rti1516e/FederateAmbassador$SupplementalReceiveInfo;)V");
	callback_receiveInteraction_8 = env->GetMethodID(_class, "receiveInteraction", "(Lhla/rti1516e/InteractionClassHandle;Lhla/rti1516e/ParameterHandleValueMap;[BLhla/rti1516e/OrderType;Lhla/rti1516e/TransportationTypeHandle;Lhla/rti1516e/LogicalTime;Lhla/rti1516e/OrderType;Lhla/rti1516e/FederateAmbassador$SupplementalReceiveInfo;)V");

	callback_receiveInteraction_9 = env->GetMethodID(_class, "receiveInteraction", "(Lhla/rti1516e/InteractionClassHandle;Lhla/rti1516e/ParameterHandleValueMap;[BLhla/rti1516e/OrderType;Lhla/rti1516e/TransportationTypeHandle;Lhla/rti1516e/LogicalTime;Lhla/rti1516e/OrderType;Lhla/rti1516e/MessageRetractionHandle;Lhla/rti1516e/FederateAmbassador$SupplementalReceiveInfo;)V");

	callback_connectionLost = env->GetMethodID(_class, "connectionLost", "(Ljava/lang/String;)V");

	/*
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
    {
        return -1;
    }*/
}

void FederateAmbassadorJava::connectionLost(const std::wstring& faultDescription) throw (FederateInternalError)
{
	jstring reason = _env->NewStringUTF(Utils::toShortString(faultDescription).c_str());
	_env->CallVoidMethod(_object, callback_connectionLost, reason);
}

void FederateAmbassadorJava::reportFederationExecutions(
		const FederationExecutionInformationVector& theFederationExecutionInformationList) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::synchronizationPointRegistrationSucceeded(const std::wstring& label)
		throw (FederateInternalError)
{
}

void FederateAmbassadorJava::synchronizationPointRegistrationFailed(const std::wstring& label,
		SynchronizationPointFailureReason reason) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::announceSynchronizationPoint(const std::wstring& label,
		const VariableLengthData& theUserSuppliedTag) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::federationSynchronized(const std::wstring& label, const FederateHandleSet& failedToSyncSet)
		throw (FederateInternalError)
{
}

void FederateAmbassadorJava::initiateFederateSave(const std::wstring& label) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::initiateFederateSave(const std::wstring& label, const LogicalTime& theTime)
		throw (FederateInternalError)
{
}

void FederateAmbassadorJava::federationSaved() throw (FederateInternalError)
{
}

void FederateAmbassadorJava::federationNotSaved(SaveFailureReason theSaveFailureReason) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::federationSaveStatusResponse(
		const FederateHandleSaveStatusPairVector& theFederateStatusVector) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::requestFederationRestoreSucceeded(const std::wstring& label) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::requestFederationRestoreFailed(const std::wstring& label) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::federationRestoreBegun() throw (FederateInternalError)
{
}

void FederateAmbassadorJava::initiateFederateRestore(const std::wstring& label, const std::wstring& federateName,
		FederateHandle handle) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::federationRestored() throw (FederateInternalError)
{
}

void FederateAmbassadorJava::federationNotRestored(RestoreFailureReason theRestoreFailureReason)
		throw (FederateInternalError)
{
}

void FederateAmbassadorJava::federationRestoreStatusResponse(
		const FederateRestoreStatusVector& theFederateRestoreStatusVector) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::startRegistrationForObjectClass(ObjectClassHandle theClass) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::stopRegistrationForObjectClass(ObjectClassHandle theClass) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::turnInteractionsOn(InteractionClassHandle theHandle) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::turnInteractionsOff(InteractionClassHandle theHandle) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::objectInstanceNameReservationSucceeded(const std::wstring& theObjectInstanceName)
		throw (FederateInternalError)
{
}

void FederateAmbassadorJava::objectInstanceNameReservationFailed(const std::wstring& theObjectInstanceName)
		throw (FederateInternalError)
{
}

void FederateAmbassadorJava::multipleObjectInstanceNameReservationSucceeded(
		const std::set<std::wstring>& theObjectInstanceNames) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::multipleObjectInstanceNameReservationFailed(
		const std::set<std::wstring>& theObjectInstanceNames) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::discoverObjectInstance(ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass,
		const std::wstring& theObjectInstanceName) throw (FederateInternalError)
{
	jobject objectInstance = Utils::createJavaHandle(_env, theObject, "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	jobject objectClass = Utils::createJavaHandle(_env, theObjectClass, "hla/rti1516e/openrti/ObjectClassHandleImpl");
	jstring name = _env->NewStringUTF(Utils::toShortString(theObjectInstanceName).c_str());

	_env->CallVoidMethod(_object, callback_discoverObjectInstance_3, objectInstance, objectClass, name);
}

void FederateAmbassadorJava::discoverObjectInstance(ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass,
		const std::wstring& theObjectInstanceName, FederateHandle producingFederate) throw (FederateInternalError)
{
	jobject objectInstance = Utils::createJavaHandle(_env, theObject, "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	jobject objectClass = Utils::createJavaHandle(_env, theObjectClass, "hla/rti1516e/openrti/ObjectClassHandleImpl");
	jobject producer = Utils::createJavaHandle(_env, producingFederate, "hla/rti1516e/openrti/FederateHandleImpl");
	jstring name = _env->NewStringUTF(Utils::toShortString(theObjectInstanceName).c_str());

	_env->CallVoidMethod(_object, callback_discoverObjectInstance_4, objectInstance, objectClass, name, producer);
}

void FederateAmbassadorJava::reflectAttributeValues(ObjectInstanceHandle theObject,
		const AttributeHandleValueMap& theAttributeValues, const VariableLengthData& theUserSuppliedTag,
		OrderType sentOrder, TransportationType theType, SupplementalReflectInfo theReflectInfo)
				throw (FederateInternalError)
{
	jobject objectInstance = Utils::createJavaHandle(_env, theObject, "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	jobject attributes = Utils::createJavaAttributeHandleValueMap(_env, theAttributeValues);
	jbyteArray tag = _env->NewByteArray((jsize)theUserSuppliedTag.size());
	jobject sent = Utils::createJavaOrderType(_env, sentOrder);
	jobject type = Utils::createJavaTransportationTypeHandle(_env, theType);
	jobject info = Utils::createJavaSupplementalReflectInfo(_env, theReflectInfo);

	_env->CallVoidMethod(_object, callback_reflectAttributeValues_6, objectInstance, attributes, tag, sent, type, info);
}

void FederateAmbassadorJava::reflectAttributeValues(ObjectInstanceHandle theObject,
		const AttributeHandleValueMap& theAttributeValues, const VariableLengthData& theUserSuppliedTag,
		OrderType sentOrder, TransportationType theType, const LogicalTime& theTime, OrderType receivedOrder,
		SupplementalReflectInfo theReflectInfo) throw (FederateInternalError)
{
	jobject objectInstance = Utils::createJavaHandle(_env, theObject, "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	jobject attributes = Utils::createJavaAttributeHandleValueMap(_env, theAttributeValues);
	jbyteArray tag = _env->NewByteArray((jsize)theUserSuppliedTag.size());
	jobject sent = Utils::createJavaOrderType(_env, sentOrder);
	jobject type = Utils::createJavaTransportationTypeHandle(_env, theType);
	jobject time = createJavaTime(theTime);
	jobject receive = Utils::createJavaOrderType(_env, receivedOrder);
	jobject info = Utils::createJavaSupplementalReflectInfo(_env, theReflectInfo);

	_env->CallVoidMethod(_object, callback_reflectAttributeValues_8, objectInstance, attributes, tag, sent, type, time, receive, info);
}

void FederateAmbassadorJava::reflectAttributeValues(ObjectInstanceHandle theObject,
		const AttributeHandleValueMap& theAttributeValues, const VariableLengthData& theUserSuppliedTag,
		OrderType sentOrder, TransportationType theType, const LogicalTime& theTime, OrderType receivedOrder,
		MessageRetractionHandle theHandle, SupplementalReflectInfo theReflectInfo) throw (FederateInternalError)
{
	jobject objectInstance = Utils::createJavaHandle(_env, theObject, "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	jobject attributes = Utils::createJavaAttributeHandleValueMap(_env, theAttributeValues);
	jbyteArray tag = _env->NewByteArray((jsize)theUserSuppliedTag.size());
	jobject sent = Utils::createJavaOrderType(_env, sentOrder);
	jobject type = Utils::createJavaTransportationTypeHandle(_env, theType);
	jobject time = createJavaTime(theTime);
	jobject receive = Utils::createJavaOrderType(_env, receivedOrder);
	jobject handle = Utils::createJavaMessageRetractionHandle(_env, theHandle);
	jobject info = Utils::createJavaSupplementalReflectInfo(_env, theReflectInfo);

	_env->CallVoidMethod(_object, callback_reflectAttributeValues_9, objectInstance, attributes, tag, sent, type, time, receive, handle, info);
}

void FederateAmbassadorJava::receiveInteraction(InteractionClassHandle theInteraction,
		const ParameterHandleValueMap& theParameterValues, const VariableLengthData& theUserSuppliedTag,
		OrderType sentOrder, TransportationType theType, SupplementalReceiveInfo theReceiveInfo)
				throw (FederateInternalError)
{
	jobject interaction = Utils::createJavaHandle(_env, theInteraction, "hla/rti1516e/openrti/InteractionClassHandleImpl");
	jobject parameters = Utils::createJavaParameterHandleValueMap(_env, theParameterValues);
	jbyteArray tag = _env->NewByteArray((jsize)theUserSuppliedTag.size());
	jobject sent = Utils::createJavaOrderType(_env, sentOrder);
	jobject type = Utils::createJavaTransportationTypeHandle(_env, theType);
	jobject info = Utils::createJavaSupplementalReceiveInfo(_env, theReceiveInfo);

	_env->CallVoidMethod(_object, callback_receiveInteraction_6, interaction, parameters, tag, sent, type, info);
}

void FederateAmbassadorJava::receiveInteraction(InteractionClassHandle theInteraction,
		const ParameterHandleValueMap& theParameterValues, const VariableLengthData& theUserSuppliedTag,
		OrderType sentOrder, TransportationType theType, const LogicalTime& theTime, OrderType receivedOrder,
		SupplementalReceiveInfo theReceiveInfo) throw (FederateInternalError)
{
	jobject interaction = Utils::createJavaHandle(_env, theInteraction, "hla/rti1516e/openrti/InteractionClassHandleImpl");
	jobject parameters = Utils::createJavaParameterHandleValueMap(_env, theParameterValues);
	jbyteArray tag = _env->NewByteArray((jsize)theUserSuppliedTag.size());
	jobject sent = Utils::createJavaOrderType(_env, sentOrder);
	jobject type = Utils::createJavaTransportationTypeHandle(_env, theType);
	jobject time = createJavaTime(theTime);
	jobject receive = Utils::createJavaOrderType(_env, receivedOrder);
	jobject info = Utils::createJavaSupplementalReceiveInfo(_env, theReceiveInfo);

	_env->CallVoidMethod(_object, callback_receiveInteraction_8, interaction, parameters, tag, sent, type, time, receive, info);
}

void FederateAmbassadorJava::receiveInteraction(InteractionClassHandle theInteraction,
		const ParameterHandleValueMap& theParameterValues, const VariableLengthData& theUserSuppliedTag,
		OrderType sentOrder, TransportationType theType, const LogicalTime& theTime, OrderType receivedOrder,
		MessageRetractionHandle theHandle, SupplementalReceiveInfo theReceiveInfo) throw (FederateInternalError)
{
	jobject interaction = Utils::createJavaHandle(_env, theInteraction, "hla/rti1516e/openrti/InteractionClassHandleImpl");
	jobject parameters = Utils::createJavaParameterHandleValueMap(_env, theParameterValues);
	jbyteArray tag = _env->NewByteArray((jsize)theUserSuppliedTag.size());
	jobject sent = Utils::createJavaOrderType(_env, sentOrder);
	jobject type = Utils::createJavaTransportationTypeHandle(_env, theType);
	jobject time = createJavaTime(theTime);
	jobject receive = Utils::createJavaOrderType(_env, receivedOrder);
	jobject handle = Utils::createJavaMessageRetractionHandle(_env, theHandle);
	jobject info = Utils::createJavaSupplementalReceiveInfo(_env, theReceiveInfo);

	_env->CallVoidMethod(_object, callback_receiveInteraction_9, interaction, parameters, tag, sent, type, time, receive, handle, info);
}

void FederateAmbassadorJava::removeObjectInstance(ObjectInstanceHandle theObject,
		const VariableLengthData& theUserSuppliedTag, OrderType sentOrder, SupplementalRemoveInfo theRemoveInfo)
				throw (FederateInternalError)
{
	jobject objectInstance = Utils::createJavaHandle(_env, theObject, "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	jbyteArray userSuppliedTag = _env->NewByteArray((jsize)theUserSuppliedTag.size());
	jobject order = Utils::createJavaOrderType(_env, sentOrder);
	jobject info = Utils::createJavaSupplementalRemoveInfo(_env, theRemoveInfo);

	_env->CallVoidMethod(_object, callback_removeObjectInstance_4, objectInstance, userSuppliedTag, order, info);
}

void FederateAmbassadorJava::removeObjectInstance(ObjectInstanceHandle theObject,
		const VariableLengthData& theUserSuppliedTag, OrderType sentOrder, const LogicalTime& theTime,
		OrderType receivedOrder, SupplementalRemoveInfo theRemoveInfo) throw (FederateInternalError)
{
	jobject objectInstance = Utils::createJavaHandle(_env, theObject, "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	jbyteArray userSuppliedTag = _env->NewByteArray((jsize)theUserSuppliedTag.size());
	jobject sent = Utils::createJavaOrderType(_env, sentOrder);
	jobject receive = Utils::createJavaOrderType(_env, receivedOrder);
	jobject time = createJavaTime(theTime);
	jobject info = Utils::createJavaSupplementalRemoveInfo(_env, theRemoveInfo);

	_env->CallVoidMethod(_object, callback_removeObjectInstance_6, objectInstance, userSuppliedTag, sent, time, receive, info);
}

void FederateAmbassadorJava::removeObjectInstance(ObjectInstanceHandle theObject,
		const VariableLengthData& theUserSuppliedTag, OrderType sentOrder, const LogicalTime& theTime,
		OrderType receivedOrder, MessageRetractionHandle theHandle, SupplementalRemoveInfo theRemoveInfo)
				throw (FederateInternalError)
{
	jobject objectInstance = Utils::createJavaHandle(_env, theObject, "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	jbyteArray userSuppliedTag = _env->NewByteArray((jsize)theUserSuppliedTag.size());
	jobject sent = Utils::createJavaOrderType(_env, sentOrder);
	jobject time = createJavaTime(theTime);
	jobject receive = Utils::createJavaOrderType(_env, receivedOrder);
	jobject handle = Utils::createJavaMessageRetractionHandle(_env, theHandle);
	jobject info = Utils::createJavaSupplementalRemoveInfo(_env, theRemoveInfo);

	_env->CallVoidMethod(_object, callback_removeObjectInstance_7, objectInstance, userSuppliedTag, sent, time, receive, handle, info);
}

void FederateAmbassadorJava::attributesInScope(ObjectInstanceHandle theObject, const AttributeHandleSet& theAttributes)
		throw (FederateInternalError)
{
}

void FederateAmbassadorJava::attributesOutOfScope(ObjectInstanceHandle theObject,
		const AttributeHandleSet& theAttributes) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::provideAttributeValueUpdate(ObjectInstanceHandle theObject,
		const AttributeHandleSet& theAttributes, const VariableLengthData& theUserSuppliedTag)
				throw (FederateInternalError)
{
}

void FederateAmbassadorJava::turnUpdatesOnForObjectInstance(ObjectInstanceHandle theObject,
		const AttributeHandleSet& theAttributes) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::turnUpdatesOnForObjectInstance(ObjectInstanceHandle theObject,
		const AttributeHandleSet& theAttributes, const std::wstring& updateRateDesignator) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::turnUpdatesOffForObjectInstance(ObjectInstanceHandle theObject,
		const AttributeHandleSet& theAttributes) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::confirmAttributeTransportationTypeChange(ObjectInstanceHandle theObject,
		AttributeHandleSet theAttributes, TransportationType theTransportation) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::reportAttributeTransportationType(ObjectInstanceHandle theObject,
		AttributeHandle theAttribute, TransportationType theTransportation) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::confirmInteractionTransportationTypeChange(InteractionClassHandle theInteraction,
		TransportationType theTransportation) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::reportInteractionTransportationType(FederateHandle federateHandle,
		InteractionClassHandle theInteraction, TransportationType theTransportation) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::requestAttributeOwnershipAssumption(ObjectInstanceHandle theObject,
		const AttributeHandleSet& offeredAttributes, const VariableLengthData& theUserSuppliedTag)
				throw (FederateInternalError)
{
}

void FederateAmbassadorJava::requestDivestitureConfirmation(ObjectInstanceHandle theObject,
		const AttributeHandleSet& releasedAttributes) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::attributeOwnershipAcquisitionNotification(ObjectInstanceHandle theObject,
		const AttributeHandleSet& securedAttributes, const VariableLengthData& theUserSuppliedTag)
				throw (FederateInternalError)
{
}

void FederateAmbassadorJava::attributeOwnershipUnavailable(ObjectInstanceHandle theObject,
		const AttributeHandleSet& theAttributes) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::requestAttributeOwnershipRelease(ObjectInstanceHandle theObject,
		const AttributeHandleSet& candidateAttributes, const VariableLengthData& theUserSuppliedTag)
				throw (FederateInternalError)
{
}

void FederateAmbassadorJava::confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle theObject,
		const AttributeHandleSet& theAttributes) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::informAttributeOwnership(ObjectInstanceHandle theObject, AttributeHandle theAttribute,
		FederateHandle theOwner) throw (FederateInternalError)
{
}

void FederateAmbassadorJava::attributeIsNotOwned(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
		throw (FederateInternalError)
{
}

void FederateAmbassadorJava::attributeIsOwnedByRTI(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
		throw (FederateInternalError)
{
}

void FederateAmbassadorJava::timeRegulationEnabled(const LogicalTime& theFederateTime) throw (FederateInternalError)
{
	_env->CallVoidMethod(_object, callback_timeRegulationEnabled, createJavaTime(theFederateTime));
}

void FederateAmbassadorJava::timeConstrainedEnabled(const LogicalTime& theFederateTime) throw (FederateInternalError)
{
	_env->CallVoidMethod(_object, callback_timeConstrainedEnabled, createJavaTime(theFederateTime));
}

void FederateAmbassadorJava::timeAdvanceGrant(const LogicalTime& theTime) throw (FederateInternalError)
{
	_env->CallVoidMethod(_object, callback_timeAdvanceGrant, createJavaTime(theTime));
}

void FederateAmbassadorJava::requestRetraction(MessageRetractionHandle theHandle) throw (FederateInternalError)
{
}
