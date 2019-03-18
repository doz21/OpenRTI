#ifndef UTILS_HPP_
#define UTILS_HPP_
#include <memory>
#include <iostream>

#include <RTI/FederateAmbassador.h>
#include <RTI/NullFederateAmbassador.h>

#include <RTI/LogicalTime.h>
#include <RTI/time/HLAfloat64Time.h>
#include <RTI/time/HLAinteger64Time.h>


#include <jni.h>
#include <string>
#include <set>
#include <vector>

using namespace rti1516e;

class Utils
{
	public:
		static std::wstring toWideString( std::string shortString );
		static std::wstring toWideString(JNIEnv *env,  jstring javaString );
		static std::string toShortString( std::wstring wideString );
		static jstring fromWideString(JNIEnv *env, std::wstring wideString );
		static std::wstring toPath( JNIEnv *env, jobject url );

		static std::set<std::wstring> toWideStringSet( JNIEnv *env, jobjectArray stringArray );
		static std::vector<std::wstring> toPathVector( JNIEnv *env, jobjectArray stringArray );

		static void throwException(JNIEnv *env, const std::string& name, const std::wstring& msg );

		static jobject createJavaOrderType(JNIEnv *env, const OrderType& order);
		static jobject createJavaTransportationTypeHandle(JNIEnv *env, const TransportationType& transportationType);
		static jobject createJavaMessageRetractionHandle(JNIEnv *env, const MessageRetractionHandle& handle);

		static jobject createJavaSupplementalReceiveInfo(JNIEnv *env, const SupplementalReceiveInfo& info);
		static jobject createJavaSupplementalRemoveInfo(JNIEnv *env, const SupplementalRemoveInfo& info);
		static jobject createJavaSupplementalReflectInfo(JNIEnv *env, const SupplementalReflectInfo& info);
		static jobject createJavaAttributeHandleValueMap(JNIEnv *env, const AttributeHandleValueMap& map);
		static jobject createJavaParameterHandleValueMap(JNIEnv *env, const ParameterHandleValueMap& map);

		static VariableLengthData toVariableLengthData(JNIEnv *env, jbyteArray array);
		static AttributeHandleValueMap toAttributeHandleValueMap(JNIEnv *env, jobjectArray array);
		static ParameterHandleValueMap toParameterHandleValueMap(JNIEnv *env, jobjectArray array);
		static AttributeHandleSet toAttributeHandleSet(JNIEnv *env, jobjectArray array);

		template<typename T>
		static jobject createJavaHandle(JNIEnv *env, const T& handle, const std::string& className);
};



#endif /* UTILS_HPP_ */
