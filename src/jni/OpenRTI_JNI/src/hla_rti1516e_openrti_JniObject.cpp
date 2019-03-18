/*
 * hla_rti1516e_openrti_JniObject.cpp
 *
 *  Created on: 01.07.2017
 *      Author: Vitali
 */

#include "Utils.hpp"
#include "hla_rti1516e_openrti_JniObject.h"

JNIEXPORT void JNICALL Java_hla_rti1516e_openrti_JniObject_dispose (JNIEnv *env, jobject me)
{
	void* ptr = (void*)env->GetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"));
	if (ptr != NULL)
	{
		env->SetLongField(me, env->GetFieldID(env->GetObjectClass(me), "ptr", "J"), NULL);
		delete ptr;
	}
	else
	{
		Utils::throwException(env, "hla/rti1516e/exceptions/RTIinternalError", L"inconsitent object");
	}
}


