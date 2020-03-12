/*
 * hla_rti1516e_openrti_FederateHandleImpl.cpp
 *
 *  Created on: 27.08.2017
 *      Author: Vitali
 */

#include "Utils.hpp"
#include "hla_rti1516e_openrti_FederateHandleImpl.h"
#include <RTI/RTIambassador.h>

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_FederateHandleImpl_encode_1rti
  (JNIEnv *env, jobject me, jbyteArray data, jint offset)
{
	FederateHandle* _handle = (FederateHandle*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	jboolean isCopy;
	jbyte* d = env->GetByteArrayElements(data, &isCopy);
	_handle->encode(d + offset, _handle->encodedLength());
	env->ReleaseByteArrayElements(data, d, 0);
}

JNIEXPORT jint JNICALL Java_hla_rti1516e_openrti_FederateHandleImpl_encodedLength_1rti
  (JNIEnv *env, jobject me)
{
	FederateHandle* _handle = (FederateHandle*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	return (jint)_handle->encodedLength();
}



