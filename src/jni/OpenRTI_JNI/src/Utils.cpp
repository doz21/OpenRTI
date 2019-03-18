#include "Utils.hpp"


template<typename T>
jobject Utils::createJavaHandle(JNIEnv *env, const T& handle, const std::string& className)
{
	T* _handle = new T(handle);
	jclass cls = env->FindClass(className.c_str());
	jobject result = env->NewObject(cls, env->GetMethodID(cls, "<init>", "()V"));
	jfieldID ptrID = env->GetFieldID(cls, "ptr", "J");
	jlong ptr = (jlong)_handle;
	env->SetLongField(result, ptrID, ptr);
	jfieldID hashID = env->GetFieldID(cls, "hash", "J");
	jlong hash = (jlong)_handle->hash();
	env->SetLongField(result, hashID, hash);
	return result;
}

template jobject Utils::createJavaHandle(JNIEnv *env, const ObjectClassHandle& handle, const std::string& className);
template jobject Utils::createJavaHandle(JNIEnv *env, const ObjectInstanceHandle& handle, const std::string& className);
template jobject Utils::createJavaHandle(JNIEnv *env, const InteractionClassHandle& handle, const std::string& className);
template jobject Utils::createJavaHandle(JNIEnv *env, const AttributeHandle& handle, const std::string& className);
template jobject Utils::createJavaHandle(JNIEnv *env, const ParameterHandle& handle, const std::string& className);
template jobject Utils::createJavaHandle(JNIEnv *env, const DimensionHandle& handle, const std::string& className);
template jobject Utils::createJavaHandle(JNIEnv *env, const FederateHandle& handle, const std::string& className);
template jobject Utils::createJavaHandle(JNIEnv *env, const MessageRetractionHandle& handle, const std::string& className);
template jobject Utils::createJavaHandle(JNIEnv *env, const RegionHandle& handle, const std::string& className);


jobject Utils::createJavaOrderType(JNIEnv *env, const OrderType& order)
{
	std::string sname;
	switch(order)
	{
		case RECEIVE:
			sname = "RECEIVE";
			break;
		case TIMESTAMP:
			sname = "TIMESTAMP";
			break;
		default:
			sname = "";
	}
	jstring name = env->NewStringUTF(sname.c_str());
	jobject result = NULL;
	jclass cls = env->FindClass("hla/rti1516e/OrderType");
	jmethodID valueOf = env->GetStaticMethodID(cls, "valueOf", "(Ljava/lang/String;)Lhla/rti1516e/OrderType;");
	jobject type = env->CallStaticObjectMethod(cls, valueOf, name);
	return type;
}

std::wstring Utils::toWideString(std::string shortString)
{
	std::wstring wideString;
	return wideString.assign(shortString.begin(), shortString.end());
}

std::string Utils::toShortString(std::wstring wideString)
{
	std::string shortString;
	return shortString.assign(wideString.begin(), wideString.end());
}

jstring Utils::fromWideString(JNIEnv *env, std::wstring wideString)
{
	return env->NewStringUTF(toShortString(wideString).c_str());
}

std::wstring Utils::toWideString(JNIEnv *env, jstring javaString)
{
	const jchar *characters = env->GetStringChars(javaString, NULL);
	const jchar *pointer = characters;
	std::wstring wideload;
	while (*pointer)
	{
		wideload += *(pointer++);
	}
	env->ReleaseStringChars(javaString, characters);
	return wideload;
}

std::wstring Utils::toPath( JNIEnv *env, jobject url )
{
	jclass cls = env->GetObjectClass(url);
	jmethodID pathID = env->GetMethodID(cls, "getPath", "()Ljava/lang/String;");
	std::wstring path = toWideString(env, (jstring)env->CallObjectMethod(url, pathID));
#if (defined(WIN32) || defined(_WIN32) || defined(__WIN32)) && !defined(__GNUC__)
	if (path.substr(0, 1) == L"/")
	{
		path = path.substr(1);
	}
#else
	//path = path.replace(path.find(L":"), 1, L"");
	path = path.substr(1);
	path = path.replace(path.find(L"/"), 1, L"\\");
#endif
	return path;
}

std::vector<std::wstring> Utils::toPathVector(JNIEnv *env, jobjectArray urlArray)
{
	jsize size = env->GetArrayLength(urlArray);
	std::vector<std::wstring> stringVector;
	for (int i = 0; i < size; i++)
	{
		jobject obj = env->GetObjectArrayElement(urlArray, i);
		stringVector.push_back(toPath(env, obj));
	}

	return stringVector;
}

std::set<std::wstring> Utils::toWideStringSet(JNIEnv *env, jobjectArray stringArray)
{
	jsize size = env->GetArrayLength(stringArray);
	std::set<std::wstring> stringSet;
	for (int i = 0; i < size; i++)
	{
		jstring temp = (jstring) env->GetObjectArrayElement(stringArray, i);
		stringSet.insert(toWideString(env, temp));
	}

	return stringSet;
}

void Utils::throwException(JNIEnv* env, const std::string& name, const std::wstring& msg)
{
	jclass errorClass = env->FindClass(name.c_str());
	env->ThrowNew(errorClass, toShortString(msg).c_str());
}

jobject Utils::createJavaSupplementalRemoveInfo(JNIEnv* env, const SupplementalRemoveInfo& info)
{
	jobject handle = info.hasProducingFederate ? Utils::createJavaHandle(env, info.producingFederate, "hla/rti1516e/FederateHandleImpl") : NULL;
	jclass cls = env->FindClass("hla/rti1516e/openrti/SupplementalRemoveInfoImpl");
	return  env->NewObject(cls, env->GetMethodID(cls, "<init>", "(Lhla/rti1516e/FederateHandle;)V"), handle);
}

VariableLengthData Utils::toVariableLengthData(JNIEnv* env, jbyteArray array)
{
	if (array == NULL)
		return VariableLengthData();
	jboolean isCopy;
	jbyte* b = env->GetByteArrayElements(array, &isCopy);
	jint size = env->GetArrayLength(array);
	VariableLengthData data(b, size);
	return data;
}

ParameterHandleValueMap Utils::toParameterHandleValueMap(JNIEnv* env, jobjectArray array)
{
	ParameterHandleValueMap parameters;

	jsize size = env->GetArrayLength(array);
	for (int i = 0; i < size; i++)
	{
		jobject entry = env->GetObjectArrayElement(array, i);
		jclass cls = env->GetObjectClass(entry);
		jmethodID getKey = env->GetMethodID(cls, "getKey", "()Ljava/lang/Object;");
		jmethodID getValue = env->GetMethodID(cls, "getValue", "()Ljava/lang/Object;");
		jobject key = env->CallObjectMethod(entry, getKey);
		jbyteArray value = static_cast<jbyteArray>(env->CallObjectMethod(entry, getValue));
		ParameterHandle* _parameterHandle = (ParameterHandle*)env->GetLongField(key, env->GetFieldID(env->GetObjectClass(key), "ptr", "J"));

		parameters[*_parameterHandle] = toVariableLengthData(env, value);
	}

	return parameters;
}

AttributeHandleValueMap Utils::toAttributeHandleValueMap(JNIEnv* env, jobjectArray array)
{
	AttributeHandleValueMap attributes;

	jsize size = env->GetArrayLength(array);
	for (int i = 0; i < size; i++)
	{
		jobject entry = env->GetObjectArrayElement(array, i);
		jclass cls = env->GetObjectClass(entry);
		jmethodID getKey = env->GetMethodID(cls, "getKey", "()Ljava/lang/Object;");
		jmethodID getValue = env->GetMethodID(cls, "getValue", "()Ljava/lang/Object;");
		jobject key = env->CallObjectMethod(entry, getKey);
		jbyteArray value = static_cast<jbyteArray>(env->CallObjectMethod(entry, getValue));
		AttributeHandle* _attributeHandle = (AttributeHandle*)env->GetLongField(key, env->GetFieldID(env->GetObjectClass(key), "ptr", "J"));

		attributes[*_attributeHandle] = toVariableLengthData(env, value);
	}

	return attributes;
}

AttributeHandleSet Utils::toAttributeHandleSet(JNIEnv* env, jobjectArray array)
{
	AttributeHandleSet attributes;
	jsize size = env->GetArrayLength(array);
	for (int i = 0; i < size; i++)
	{
		jobject obj = env->GetObjectArrayElement(array, i);
		AttributeHandle* attributeHandle = (AttributeHandle*)env->GetLongField(obj, env->GetFieldID(env->GetObjectClass(obj), "ptr", "J"));
		attributes.insert(*attributeHandle);
	}
	return attributes;
}

jobject Utils::createJavaAttributeHandleValueMap(JNIEnv* env, const AttributeHandleValueMap& map)
{
	jclass cls = env->FindClass("hla/rti1516e/openrti/AttributeHandleValueMapImpl");
	jobject result = env->NewObject(cls, env->GetMethodID(cls, "<init>", "(I)V"), map.size());

	jmethodID put = env->GetMethodID(cls, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
	for (auto i : map)
	{
		jobject attributeHandle = Utils::createJavaHandle(env, i.first, "hla/rti1516e/openrti/AttributeHandleImpl");
		jsize size = (jsize)i.second.size();
		jbyteArray array = env->NewByteArray(size);
	    env->SetByteArrayRegion(array, 0, size, (jbyte*)i.second.data());
	    env->CallObjectMethod(result, put, attributeHandle, array);
	}

	return result;
}

jobject Utils::createJavaParameterHandleValueMap(JNIEnv* env, const ParameterHandleValueMap& map)
{
	jclass cls = env->FindClass("hla/rti1516e/openrti/ParameterHandleValueMapImpl");
	jobject result = env->NewObject(cls, env->GetMethodID(cls, "<init>", "(I)V"), map.size());

	jmethodID put = env->GetMethodID(cls, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
	for (auto i : map)
	{
		jobject parameterHandle = Utils::createJavaHandle(env, i.first, "hla/rti1516e/openrti/ParameterHandleImpl");
		jsize size = (jsize)i.second.size();
		jbyteArray array = env->NewByteArray(size);
	    env->SetByteArrayRegion(array, 0, size, (jbyte*)i.second.data());
	    env->CallObjectMethod(result, put, parameterHandle, array);
	}

	return result;
}

jobject Utils::createJavaSupplementalReflectInfo(JNIEnv* env, const SupplementalReflectInfo& info)
{
	// TODO
	return NULL;
}


jobject Utils::createJavaSupplementalReceiveInfo(JNIEnv* env, const SupplementalReceiveInfo& info)
{
	// TODO
	return NULL;
}

jobject Utils::createJavaTransportationTypeHandle(JNIEnv* env, const TransportationType& transportationType)
{
	// TODO
	return NULL;
}

jobject Utils::createJavaMessageRetractionHandle(JNIEnv* env, const MessageRetractionHandle& handle)
{
	// TODO
	return NULL;
}
