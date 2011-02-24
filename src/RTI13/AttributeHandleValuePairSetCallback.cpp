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

#include "AttributeHandleValuePairSetCallback.h"

AttributeHandleValuePairSetCallback::AttributeHandleValuePairSetCallback(RTI::TransportType transportType, RTI::OrderType orderType) :
  _transportType(transportType),
  _orderType(orderType)
{
}

AttributeHandleValuePairSetCallback::~AttributeHandleValuePairSetCallback()
{
}

RTI::ULong
AttributeHandleValuePairSetCallback::size() const
{
  return _attributeValues.size();
}

RTI::Handle
AttributeHandleValuePairSetCallback::getHandle(RTI::ULong index) const
  throw (RTI::ArrayIndexOutOfBounds)
{
  if (_attributeValues.size() <= index)
    throw RTI::ArrayIndexOutOfBounds("Array Index out of bounds in getHandle()");
  return _attributeValues[index].getAttributeHandle();
}

RTI::ULong
AttributeHandleValuePairSetCallback::getValueLength(RTI::ULong index) const
  throw (RTI::ArrayIndexOutOfBounds)
{
  if (_attributeValues.size() <= index)
    throw RTI::ArrayIndexOutOfBounds("Array Index out of bounds in getHandle()");
  return _attributeValues[index].getValue().size();
}

void
AttributeHandleValuePairSetCallback::getValue(RTI::ULong index, char* data, RTI::ULong& length) const
  throw (RTI::ArrayIndexOutOfBounds)
{
  if (_attributeValues.size() <= index)
    throw RTI::ArrayIndexOutOfBounds("Array Index out of bounds in getHandle()");
  size_t size = _attributeValues[index].getValue().size();
  if (size < length)
    length = size;
  memcpy(data, _attributeValues[index].getValue().data(), length);
}

char*
AttributeHandleValuePairSetCallback::getValuePointer(RTI::ULong index, RTI::ULong& length) const
  throw (RTI::ArrayIndexOutOfBounds)
{
  if (_attributeValues.size() <= index)
    throw RTI::ArrayIndexOutOfBounds("Array Index out of bounds in getHandle()");
  length = _attributeValues[index].getValue().size();
  _attributeValues[index].getValue().ensurePrivate();
  return _attributeValues[index].getValue().charData();
}

RTI::TransportType
AttributeHandleValuePairSetCallback::getTransportType(RTI::ULong index) const
  throw (RTI::InvalidHandleValuePairSetContext)
{
  return _transportType;
}

RTI::OrderType
AttributeHandleValuePairSetCallback::getOrderType(RTI::ULong index) const
  throw (RTI::ArrayIndexOutOfBounds,
         RTI::InvalidHandleValuePairSetContext)
{
  return _orderType;
}

RTI::Region*
AttributeHandleValuePairSetCallback::getRegion(RTI::ULong index) const
  throw (RTI::ArrayIndexOutOfBounds,
         RTI::InvalidHandleValuePairSetContext)
{
  // FIXME
  return 0;
}

void
AttributeHandleValuePairSetCallback::add(RTI::Handle handle, const char* data, RTI::ULong length)
  throw (RTI::ValueLengthExceeded,
         RTI::ValueCountExceeded)
{
  throw RTI::RTIinternalError("Unimplemented function: This class is only intendet for constant use.");
}

void
AttributeHandleValuePairSetCallback::remove(RTI::Handle handle)
  throw (RTI::ArrayIndexOutOfBounds)
{
  throw RTI::RTIinternalError("Unimplemented function: This class is only intendet for constant use.");
}

void
AttributeHandleValuePairSetCallback::moveFrom(const RTI::AttributeHandleValuePairSet& attributeHandleValuePairSet, RTI::ULong& index)
  throw (RTI::ValueCountExceeded,
         RTI::ArrayIndexOutOfBounds)
{
  throw RTI::RTIinternalError("Unimplemented function: This class is only intendet for constant use.");
}

/// Sigh, should have been called clear???
void
AttributeHandleValuePairSetCallback::empty()
{
  throw RTI::RTIinternalError("Unimplemented function: This class is only intendet for constant use.");
}

/// Do not understand that, but guessing somewhat ...
RTI::ULong
AttributeHandleValuePairSetCallback::start() const
{
  return 0;
}

RTI::ULong
AttributeHandleValuePairSetCallback::valid(RTI::ULong i) const
{
  return i < _attributeValues.size();
}

RTI::ULong
AttributeHandleValuePairSetCallback::next(RTI::ULong i) const
{
  return i+1;
}