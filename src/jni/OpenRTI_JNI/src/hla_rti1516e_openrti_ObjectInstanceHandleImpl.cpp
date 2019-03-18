/*
 * hla_rti1516e_openrti_ObjectInstanceHandleImpl.cpp
 *
 *  Created on: 27.08.2017
 *      Author: Vitali
 */

#include "Utils.hpp"
#include "hla_rti1516e_openrti_ObjectInstanceHandleImpl.h"
#include <RTI/RTIambassador.h>

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_ObjectInstanceHandleImpl_encode_1rti
  (JNIEnv *env, jobject me, jbyteArray data, jint offset)
{
	ObjectInstanceHandle* _handle = (ObjectInstanceHandle*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	jboolean isCopy;
	jbyte* d = env->GetByteArrayElements(data, &isCopy);
	_handle->encode(d + offset, _handle->encodedLength());
	env->ReleaseByteArrayElements(data, d, 0);
}

JNIEXPORT jint JNICALL Java_hla_rti1516e_openrti_ObjectInstanceHandleImpl_encodedLength_1rti
  (JNIEnv *env, jobject me)
{
	ObjectInstanceHandle* _handle = (ObjectInstanceHandle*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	return (jint)_handle->encodedLength();
}




