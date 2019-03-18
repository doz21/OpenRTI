/*
 * hla_rti1516e_openrti_RTIambassadorImpl.cpp
 *
 *  Created on: 22.06.2017
 *      Author: V7PS4AG
 */

#include "Utils.hpp"
#include "hla_rti1516e_openrti_RTIambassadorImpl.h"

#include "FederateAmbassadorJava.hpp"

#include <RTI/RTIambassador.h>

#include <RTI/LogicalTimeInterval.h>
#include <RTI/time/HLAfloat64Time.h>
#include <RTI/time/HLAinteger64Time.h>

using namespace rti1516e;


JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_sendInteraction_1rti
  (JNIEnv *env, jobject me, jobject theInteraction, jobjectArray theParameters, jbyteArray userSuppliedTag)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	InteractionClassHandle* _theInteraction = (InteractionClassHandle*)env->GetLongField(theInteraction, env->GetFieldID(env->GetObjectClass(theInteraction), "ptr", "J"));
	ParameterHandleValueMap parameters = Utils::toParameterHandleValueMap(env, theParameters);
	VariableLengthData _userSuppliedTag = Utils::toVariableLengthData(env, userSuppliedTag);

	try
	{
		amb->sendInteraction(*_theInteraction, parameters, _userSuppliedTag);
	}
	catch (const InteractionClassNotPublished& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InteractionClassNotPublished", ex.what());
	}
	catch (const InteractionParameterNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InteractionParameterNotDefined", ex.what());
	}
	catch (const InteractionClassNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InteractionClassNotDefined", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

/*
 * Class:     hla_rti1516e_openrti_RTIambassadorImpl
 * Method:    sendInteractionInteger64_rti
 * Signature: (Lhla/rti1516e/InteractionClassHandle;[Ljava/lang/Object;[BJ)Lhla/rti1516e/MessageRetractionReturn;
 */
JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_sendInteractionInteger64_1rti
  (JNIEnv *, jobject, jobject, jobjectArray, jbyteArray, jlong)
{
	return NULL;
}

/*
 * Class:     hla_rti1516e_openrti_RTIambassadorImpl
 * Method:    sendInteractionFloat64_rti
 * Signature: (Lhla/rti1516e/InteractionClassHandle;[Ljava/lang/Object;[BD)Lhla/rti1516e/MessageRetractionReturn;
 */
JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_sendInteractionFloat64_1rti
  (JNIEnv *, jobject, jobject, jobjectArray, jbyteArray, jdouble)
{
	return NULL;
}


JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_requestAttributeValueUpdate_1rti__Lhla_rti1516e_ObjectInstanceHandle_2_3Ljava_lang_Object_2_3B
  (JNIEnv *env, jobject me, jobject theObject, jobjectArray theAttributes, jbyteArray userSuppliedTag)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectInstanceHandle* _theObject = (ObjectInstanceHandle*)env->GetLongField(theObject, env->GetFieldID(env->GetObjectClass(theObject), "ptr", "J"));
	AttributeHandleSet attributes = Utils::toAttributeHandleSet(env, theAttributes);
	VariableLengthData _userSuppliedTag = Utils::toVariableLengthData(env, userSuppliedTag);

	try
	{
		amb->requestAttributeValueUpdate(*_theObject, attributes, _userSuppliedTag);
	}
	catch (const AttributeNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotDefined", ex.what());
	}
	catch (const ObjectInstanceNotKnown& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectInstanceNotKnown", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}


}


JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_requestAttributeValueUpdate_1rti__Lhla_rti1516e_ObjectClassHandle_2_3Ljava_lang_Object_2_3B
(JNIEnv *env, jobject me, jobject theClass, jobjectArray theAttributes, jbyteArray userSuppliedTag)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectClassHandle* _theObject = (ObjectClassHandle*)env->GetLongField(theClass, env->GetFieldID(env->GetObjectClass(theClass), "ptr", "J"));
	AttributeHandleSet attributes = Utils::toAttributeHandleSet(env, theAttributes);
	VariableLengthData _userSuppliedTag = Utils::toVariableLengthData(env, userSuppliedTag);

	try
	{
		amb->requestAttributeValueUpdate(*_theObject, attributes, _userSuppliedTag);
	}
	catch (const AttributeNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotDefined", ex.what());
	}
	catch (const ObjectClassNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectClassNotDefined", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

}

//TODO : MessageRetractionReturn
JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_updateAttributeValuesInteger64_1rti
(JNIEnv *env, jobject me, jobject theObject, jobjectArray theAttributes, jbyteArray userSuppliedTag, jlong theTime)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectInstanceHandle* _theObject = (ObjectInstanceHandle*)env->GetLongField(theObject, env->GetFieldID(env->GetObjectClass(theObject), "ptr", "J"));
	AttributeHandleValueMap attributes = Utils::toAttributeHandleValueMap(env, theAttributes);
	VariableLengthData _userSuppliedTag = Utils::toVariableLengthData(env, userSuppliedTag);

	try
	{
		std::shared_ptr<HLAinteger64Time> pTime(new HLAinteger64Time(theTime));
		amb->updateAttributeValues(*_theObject, attributes, _userSuppliedTag, *pTime);
	}
	catch (const InvalidLogicalTime& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidLogicalTime", ex.what());
	}
	catch (const AttributeNotOwned& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotOwned", ex.what());
	}
	catch (const AttributeNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotDefined", ex.what());
	}
	catch (const ObjectInstanceNotKnown& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectInstanceNotKnown", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

//TODO : MessageRetractionReturn
JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_updateAttributeValuesFloat64_1rti
(JNIEnv *env, jobject me, jobject theObject, jobjectArray theAttributes, jbyteArray userSuppliedTag, jdouble theTime)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectInstanceHandle* _theObject = (ObjectInstanceHandle*)env->GetLongField(theObject, env->GetFieldID(env->GetObjectClass(theObject), "ptr", "J"));
	AttributeHandleValueMap attributes = Utils::toAttributeHandleValueMap(env, theAttributes);
	VariableLengthData _userSuppliedTag = Utils::toVariableLengthData(env, userSuppliedTag);

	try
	{
		std::shared_ptr<HLAfloat64Time> pTime(new HLAfloat64Time(theTime));
		amb->updateAttributeValues(*_theObject, attributes, _userSuppliedTag, *pTime);
	}
	catch (const InvalidLogicalTime& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidLogicalTime", ex.what());
	}
	catch (const AttributeNotOwned& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotOwned", ex.what());
	}
	catch (const AttributeNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotDefined", ex.what());
	}
	catch (const ObjectInstanceNotKnown& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectInstanceNotKnown", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_updateAttributeValues_1rti
(JNIEnv *env, jobject me, jobject theObject, jobjectArray theAttributes, jbyteArray userSuppliedTag)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectInstanceHandle* _theObject = (ObjectInstanceHandle*)env->GetLongField(theObject, env->GetFieldID(env->GetObjectClass(theObject), "ptr", "J"));
	AttributeHandleValueMap attributes = Utils::toAttributeHandleValueMap(env, theAttributes);
	VariableLengthData _userSuppliedTag = Utils::toVariableLengthData(env, userSuppliedTag);

	try
	{
		amb->updateAttributeValues(*_theObject, attributes, _userSuppliedTag);
	}
	catch (const AttributeNotOwned& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotOwned", ex.what());
	}
	catch (const AttributeNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotDefined", ex.what());
	}
	catch (const ObjectInstanceNotKnown& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectInstanceNotKnown", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

}

JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_registerObjectInstance_1rti__Lhla_rti1516e_ObjectClassHandle_2
  (JNIEnv *env, jobject me, jobject objectClassHandle)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectClassHandle* _objectClassHandle = (ObjectClassHandle*)env->GetLongField(objectClassHandle, env->GetFieldID(env->GetObjectClass(objectClassHandle), "ptr", "J"));

	try
	{
		return Utils::createJavaHandle(env, amb->registerObjectInstance(*_objectClassHandle), "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	}
	catch (const ObjectClassNotPublished& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectClassNotPublished", ex.what());
	}
	catch (const ObjectClassNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectClassNotDefined", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_registerObjectInstance_1rti__Lhla_rti1516e_ObjectClassHandle_2Ljava_lang_String_2
  (JNIEnv *env, jobject me, jobject objectClassHandle, jstring name)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectClassHandle* _objectClassHandle = (ObjectClassHandle*)env->GetLongField(objectClassHandle, env->GetFieldID(env->GetObjectClass(objectClassHandle), "ptr", "J"));
	std::wstring _name = Utils::toWideString(env, name);

	try
	{
		return Utils::createJavaHandle(env, amb->registerObjectInstance(*_objectClassHandle, _name), "hla/rti1516e/openrti/ObjectInstanceHandleImpl");
	}
	catch (const ObjectInstanceNameInUse& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectInstanceNameInUse", ex.what());
	}
	catch (const ObjectInstanceNameNotReserved& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectInstanceNameNotReserved", ex.what());
	}
	catch (const ObjectClassNotPublished& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectClassNotPublished", ex.what());
	}
	catch (const ObjectClassNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectClassNotDefined", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_publishInteractionClass_1rti
  (JNIEnv *env, jobject me, jobject interactionClassHandle)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	InteractionClassHandle* handle = (InteractionClassHandle*)env->GetLongField(interactionClassHandle, env->GetFieldID(env->GetObjectClass(interactionClassHandle), "ptr", "J"));

	try
	{
		amb->publishInteractionClass(*handle);
	}
	catch (const InteractionClassNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InteractionClassNotDefined", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_publishObjectClassAttributes_1rti
  (JNIEnv *env, jobject me, jobject objectClassHandle, jobjectArray attributeList)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectClassHandle* _objectClassHandle = (ObjectClassHandle*)env->GetLongField(objectClassHandle, env->GetFieldID(env->GetObjectClass(objectClassHandle), "ptr", "J"));
	AttributeHandleSet _attributeList = Utils::toAttributeHandleSet(env, attributeList);

	try
	{
		amb->publishObjectClassAttributes(*_objectClassHandle, _attributeList);
	}
	catch (const AttributeNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotDefined", ex.what());
	}
	catch (const ObjectClassNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectClassNotDefined", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_subscribeInteractionClass_1rti
  (JNIEnv *env, jobject me, jobject interaction)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	InteractionClassHandle* handle = (InteractionClassHandle*)env->GetLongField(interaction, env->GetFieldID(env->GetObjectClass(interaction), "ptr", "J"));

	try
	{
		amb->subscribeInteractionClass(*handle);
	}
	catch (const FederateServiceInvocationsAreBeingReportedViaMOM& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateServiceInvocationsAreBeingReportedViaMOM", ex.what());
	}
	catch (const InteractionClassNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InteractionClassNotDefined", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_subscribeObjectClassAttributes_1rti__Lhla_rti1516e_ObjectClassHandle_2_3Ljava_lang_Object_2
  (JNIEnv *env, jobject me, jobject objectClassHandle, jobjectArray attributeList)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectClassHandle* _objectClassHandle = (ObjectClassHandle*)env->GetLongField(objectClassHandle, env->GetFieldID(env->GetObjectClass(objectClassHandle), "ptr", "J"));

	AttributeHandleSet _attributeList;
	jsize size = env->GetArrayLength(attributeList);
	for (int i = 0; i < size; i++)
	{
		jobject obj = env->GetObjectArrayElement(attributeList, i);
		AttributeHandle* _attributeHandle = (AttributeHandle*)env->GetLongField(obj, env->GetFieldID(env->GetObjectClass(obj), "ptr", "J"));
		_attributeList.insert(*_attributeHandle);
	}

	try
	{
		amb->subscribeObjectClassAttributes(*_objectClassHandle, _attributeList);
	}
	catch (const AttributeNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotDefined", ex.what());
	}
	catch (const ObjectClassNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ObjectClassNotDefined", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_getParameterHandle_1rti
  (JNIEnv *env, jobject me, jobject interaction, jstring name)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	InteractionClassHandle* handle = (InteractionClassHandle*)env->GetLongField(interaction, env->GetFieldID(env->GetObjectClass(interaction), "ptr", "J"));
	std::wstring _name = Utils::toWideString(env, name);

	try
	{
		return Utils::createJavaHandle(env, amb->getParameterHandle(*handle, _name), "hla/rti1516e/openrti/ParameterHandleImpl");
	}
	catch (const NameNotFound& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NameNotFound", ex.what());
	}
	catch (const InvalidObjectClassHandle& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidObjectClassHandle", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_getAttributeHandle_1rti
  (JNIEnv *env , jobject me, jobject objectClassHandle, jstring name)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectClassHandle* _objectClassHandle = (ObjectClassHandle*)env->GetLongField(objectClassHandle, env->GetFieldID(env->GetObjectClass(objectClassHandle), "ptr", "J"));
	std::wstring _name = Utils::toWideString(env, name);

	try
	{
		return Utils::createJavaHandle(env, amb->getAttributeHandle(*_objectClassHandle, _name), "hla/rti1516e/openrti/AttributeHandleImpl");
	}
	catch (const NameNotFound& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NameNotFound", ex.what());
	}
	catch (const InvalidObjectClassHandle& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidObjectClassHandle", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT jstring JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_getAttributeName_1rti
  (JNIEnv *env , jobject me , jobject objectClassHandle, jobject attributeHandle)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectClassHandle* _objectClassHandle = (ObjectClassHandle*)env->GetLongField(objectClassHandle, env->GetFieldID(env->GetObjectClass(objectClassHandle), "ptr", "J"));
	AttributeHandle* _attributeHandle = (AttributeHandle*)env->GetLongField(attributeHandle, env->GetFieldID(env->GetObjectClass(attributeHandle), "ptr", "J"));

	try
	{
		std::wstring name = amb->getAttributeName(*_objectClassHandle, *_attributeHandle);
		return env->NewStringUTF(Utils::toShortString(name).c_str());
	}
	catch (const AttributeNotDefined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AttributeNotDefined", ex.what());
	}
	catch (const InvalidAttributeHandle& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidAttributeHandle", ex.what());
	}
	catch (const InvalidObjectClassHandle& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidObjectClassHandle", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT jstring JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_getInteractionClassName_1rti
  (JNIEnv *env, jobject me, jobject handle)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	InteractionClassHandle* _handle = (InteractionClassHandle*)env->GetLongField(handle, env->GetFieldID(env->GetObjectClass(handle), "ptr", "J"));

	try
	{
		std::wstring name = amb->getInteractionClassName(*_handle);
		return env->NewStringUTF(Utils::toShortString(name).c_str());
	}
	catch (const InvalidInteractionClassHandle& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidInteractionClassHandle", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_getInteractionClassHandle_1rti
  (JNIEnv *env, jobject me, jstring name)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	std::wstring _name = Utils::toWideString(env, name);

	try
	{
		return Utils::createJavaHandle(env, amb->getInteractionClassHandle(_name), "hla/rti1516e/openrti/InteractionClassHandleImpl");
	}
	catch (const NameNotFound& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NameNotFound", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}
JNIEXPORT jstring JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_getObjectClassName_1rti
  (JNIEnv *env, jobject me, jobject handle)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	ObjectClassHandle* _handle = (ObjectClassHandle*)env->GetLongField(handle, env->GetFieldID(env->GetObjectClass(handle), "ptr", "J"));

	try
	{
		std::wstring name = amb->getObjectClassName(*_handle);
		return env->NewStringUTF(Utils::toShortString(name).c_str());
	}
	catch (const InvalidObjectClassHandle& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidObjectClassHandle", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_getObjectClassHandle_1rti
  (JNIEnv *env, jobject me, jstring name)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	std::wstring _name = Utils::toWideString(env, name);

	try
	{
		return Utils::createJavaHandle(env, amb->getObjectClassHandle(_name), "hla/rti1516e/openrti/ObjectClassHandleImpl");
	}
	catch (const NameNotFound& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NameNotFound", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_destroyFederationExecution_1rti
  (JNIEnv *env, jobject me, jstring federationExecutionName)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	std::wstring _federationExecutionName = Utils::toWideString(env, federationExecutionName);
	try
	{
		amb->destroyFederationExecution(_federationExecutionName);
	}
	catch (const FederatesCurrentlyJoined& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederatesCurrentlyJoined", ex.what());
	}
	catch (const FederationExecutionDoesNotExist& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederationExecutionDoesNotExist", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_resignFederationExecution_1rti
  (JNIEnv *env, jobject me, jobject action)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));

    jmethodID getValueMethod = env->GetMethodID(env->FindClass("hla/rti1516e/ResignAction"), "ordinal", "()I");
    ResignAction actionValue = static_cast<ResignAction>(env->CallIntMethod(action, getValueMethod));
	try
	{
		amb->resignFederationExecution(actionValue);
	}
	catch (const InvalidResignAction& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidResignAction", ex.what());
	}
	catch (const OwnershipAcquisitionPending& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/OwnershipAcquisitionPending", ex.what());
	}
	catch (const FederateOwnsAttributes& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateOwnsAttributes", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const CallNotAllowedFromWithinCallback& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CallNotAllowedFromWithinCallback", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_connect_1rti
  (JNIEnv *env, jobject me, jobject federateAmbassador, jobject callbackModel, jstring localSettingsDesignator)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));

    jmethodID callbackModelGetValueMethod = env->GetMethodID(env->FindClass("hla/rti1516e/CallbackModel"), "ordinal", "()I");
    CallbackModel callbackModelValue = static_cast<CallbackModel>(env->CallIntMethod(callbackModel, callbackModelGetValueMethod));


	std::wstring address = Utils::toWideString(env, localSettingsDesignator);

	FederateAmbassador* fed = new FederateAmbassadorJava(env, federateAmbassador);

	try
	{
		amb->connect(*fed, callbackModelValue, address);
	}
	catch (const ConnectionFailed& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ConnectionFailed", ex.what());
	}
	catch (const InvalidLocalSettingsDesignator& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidLocalSettingsDesignator", ex.what());
	}
	catch (const UnsupportedCallbackModel& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/UnsupportedCallbackModel", ex.what());
	}
	catch (const AlreadyConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/AlreadyConnected", ex.what());
	}
	catch (const CallNotAllowedFromWithinCallback& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CallNotAllowedFromWithinCallback", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT jboolean JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_evokeMultipleCallbacks_1rti
  (JNIEnv *env, jobject me, jdouble min, jdouble max)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	return amb->evokeMultipleCallbacks(min, max);
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_disconnect_1rti
  (JNIEnv *env, jobject me)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	try
	{
		amb->disconnect();
	}
	catch (const FederateIsExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateIsExecutionMember", ex.what());
	}
	catch (const CallNotAllowedFromWithinCallback& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CallNotAllowedFromWithinCallback", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_createFederationExecution_1rti__Ljava_lang_String_2_3Ljava_net_URL_2Ljava_net_URL_2Ljava_lang_String_2
  (JNIEnv *env, jobject me, jstring name, jobjectArray foms, jobject mim, jstring time)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	std::wstring _name = Utils::toWideString(env, name);
	std::vector<std::wstring> _foms = Utils::toPathVector(env, foms);
	std::wstring _mim = Utils::toPath(env, mim);
	std::wstring _time = Utils::toWideString(env, time);

	try
	{
		amb->createFederationExecutionWithMIM(_name, _foms, _mim, _time);
	}
	catch (const CouldNotCreateLogicalTimeFactory& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CouldNotCreateLogicalTimeFactory", ex.what());
	}
	catch (const InconsistentFDD& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InconsistentFDD", ex.what());
	}
	catch (const ErrorReadingFDD& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ErrorReadingFDD", ex.what());
	}
	catch (const CouldNotOpenFDD& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CouldNotOpenFDD", ex.what());
	}
	catch (const CouldNotOpenMIM& ex) // TODO: nicht in der Schnittstelle
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CouldNotOpenMIM", ex.what());
	}
	catch (const DesignatorIsHLAstandardMIM& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/DesignatorIsHLAstandardMIM", ex.what());
	}
	catch (const ErrorReadingMIM& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ErrorReadingMIM", ex.what());
	}
	catch (const FederationExecutionAlreadyExists& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederationExecutionAlreadyExists", ex.what());
	}
	catch (const CallNotAllowedFromWithinCallback& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CallNotAllowedFromWithinCallback", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT jobject JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_joinFederationExecution_1rti__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2_3Ljava_net_URL_2
  (JNIEnv *env, jobject me, jstring federateName, jstring federateType, jstring federationExecutionName, jobjectArray additionalFomModules)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	std::wstring fedName = Utils::toWideString(env, federateName);
	std::wstring fedType = Utils::toWideString(env, federateType);
	std::wstring federationName = Utils::toWideString(env, federationExecutionName);
	std::vector<std::wstring> foms = Utils::toPathVector(env, additionalFomModules);

	try
	{
		return Utils::createJavaHandle(env, amb->joinFederationExecution(fedName, fedType, federationName, foms), "hla/rti1516e/openrti/FederateHandleImpl");
	}
	catch (const CouldNotCreateLogicalTimeFactory& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CouldNotCreateLogicalTimeFactory", ex.what());
	}
	catch (const FederationExecutionDoesNotExist& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederationExecutionDoesNotExist", ex.what());
	}
	catch (const InconsistentFDD& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InconsistentFDD", ex.what());
	}
	catch (const ErrorReadingFDD& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/ErrorReadingFDD", ex.what());
	}
	catch (const CouldNotOpenFDD& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CouldNotOpenFDD", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateAlreadyExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateAlreadyExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const CallNotAllowedFromWithinCallback& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/CallNotAllowedFromWithinCallback", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}

	return NULL;
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_enableTimeConstrained_1rti
  (JNIEnv *env, jobject me)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));

	try
	{
		amb->enableTimeConstrained();
	}
	catch (const InTimeAdvancingState& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InTimeAdvancingState", ex.what());
	}
	catch (const RequestForTimeConstrainedPending& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RequestForTimeConstrainedPending", ex.what());
	}
	catch (const TimeConstrainedAlreadyEnabled& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/TimeConstrainedAlreadyEnabled", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_timeAdvanceRequestFloat64_1rti
  (JNIEnv *env, jobject me, jdouble theTime)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));

	try
	{
		std::shared_ptr<HLAfloat64Time> pTime(new HLAfloat64Time(theTime));
		amb->timeAdvanceRequest(*pTime);
	}
	catch (const LogicalTimeAlreadyPassed& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/LogicalTimeAlreadyPassed", ex.what());
	}
	catch (const InvalidLogicalTime& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidLogicalTime", ex.what());
	}
	catch (const InTimeAdvancingState& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InTimeAdvancingState", ex.what());
	}
	catch (const RequestForTimeRegulationPending& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RequestForTimeRegulationPending", ex.what());
	}
	catch (const RequestForTimeConstrainedPending& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RequestForTimeConstrainedPending", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
	catch (const Exception& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_timeAdvanceRequestInteger64_1rti
  (JNIEnv *env, jobject me, jlong theTime)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));

	try
	{
		std::shared_ptr<HLAinteger64Time> pTime(new HLAinteger64Time(theTime));
		amb->timeAdvanceRequest(*pTime);
	}
	catch (const LogicalTimeAlreadyPassed& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/LogicalTimeAlreadyPassed", ex.what());
	}
	catch (const InvalidLogicalTime& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidLogicalTime", ex.what());
	}
	catch (const InTimeAdvancingState& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InTimeAdvancingState", ex.what());
	}
	catch (const RequestForTimeRegulationPending& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RequestForTimeRegulationPending", ex.what());
	}
	catch (const RequestForTimeConstrainedPending& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RequestForTimeConstrainedPending", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_enableTimeRegulationFloat64_1rti
  (JNIEnv *env, jobject me, jdouble lookahead)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));

	try
	{
		std::shared_ptr<HLAfloat64Interval> interval(new HLAfloat64Interval(lookahead));
		amb->enableTimeRegulation(*interval);
	}
	catch (const InvalidLookahead& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidLookahead", ex.what());
	}
	catch (const InTimeAdvancingState& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InTimeAdvancingState", ex.what());
	}
	catch (const RequestForTimeRegulationPending& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RequestForTimeRegulationPending", ex.what());
	}
	catch (const TimeRegulationAlreadyEnabled& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/TimeRegulationAlreadyEnabled", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_RTIambassadorImpl_enableTimeRegulationInteger64_1rti
  (JNIEnv *env, jobject me, jlong lookahead)
{
	RTIambassador* amb = (RTIambassador*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));

	try
	{
		std::shared_ptr<HLAinteger64Interval> interval(new HLAinteger64Interval(lookahead));
		amb->enableTimeRegulation(*interval);
	}
	catch (const InvalidLookahead& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InvalidLookahead", ex.what());
	}
	catch (const InTimeAdvancingState& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/InTimeAdvancingState", ex.what());
	}
	catch (const RequestForTimeRegulationPending& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RequestForTimeRegulationPending", ex.what());
	}
	catch (const TimeRegulationAlreadyEnabled& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/TimeRegulationAlreadyEnabled", ex.what());
	}
	catch (const SaveInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/SaveInProgress", ex.what());
	}
	catch (const RestoreInProgress& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RestoreInProgress", ex.what());
	}
	catch (const FederateNotExecutionMember& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/FederateNotExecutionMember", ex.what());
	}
	catch (const NotConnected& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/NotConnected", ex.what());
	}
	catch (const RTIinternalError& ex)
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", ex.what());
	}
}

