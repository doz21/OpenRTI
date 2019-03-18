#include "Utils.hpp"
#include "hla_rti1516e_openrti_RtiFactoryImpl.h"

#include <RTI/encoding/BasicDataElements.h>
#include <RTI/RTIambassadorFactory.h>

#include <memory>
#include <string>
#include <iostream>

using namespace rti1516e;


JNIEXPORT jobject JNICALL  Java_hla_rti1516e_openrti_RtiFactoryImpl_getRtiAmbassador_1rti(JNIEnv * env, jobject object)
{
	RTIambassadorFactory factory;
	RTIambassador* ambassadorRTI;
	try
	{
		ambassadorRTI = factory.createRTIambassador().release();
	}
	catch(const RTIinternalError& ex)
	{
		jclass errorClass = env->FindClass("hla/rti1516e/exceptions/RTIinternalError");
		std::wstring s = ex.what();
		std::string msg(s.begin(), s.end());
		env->ThrowNew(errorClass, msg.c_str());
	}

	jclass ambassadorClass = env->FindClass("hla/rti1516e/openrti/RTIambassadorImpl");
	jmethodID ctor = env->GetMethodID(ambassadorClass, "<init>", "()V");
	jobject ambassador = env->NewObject(ambassadorClass, ctor);

	jfieldID handleID = env->GetFieldID(ambassadorClass, "ptr", "J");
	jlong ptr = (jlong)ambassadorRTI;
	env->SetLongField(ambassador, handleID, ptr);

	return ambassador;
}

