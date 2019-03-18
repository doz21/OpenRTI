cmake_minimum_required(VERSION 2.8)

project(OpenRTI_JNI)

set(CMAKE_CXX_FLAGS_RELEASE "-std=c++14 -O2 -DNDEBUG -DRTI_USES_STD_FSTREAM")
set(CMAKE_BUILD_TYPE "Release")

find_path(RTI_INCLUDE_DIRS NAMES RTI
 PATH_SUFFIXES include/rti1516e
 DOC "The rti1516e include files"
)
if (RTI_INCLUDE_DIRS)
  message(STATUS "rti1516e headers found: ${RTI_INCLUDE_DIRS}")
else ()
  message(FATAL_ERROR "rti1516e headers NOT found")
endif (RTI_INCLUDE_DIRS)
set(RTI_LIBRARIES rti1516e fedtime1516e)


if(MSYS)
  set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}  -Wno-deprecated -Wno-deprecated-declarations -s")
  include_directories(${RTI_INCLUDE_DIRS} V:/jdk1.8.0_144/include V:/jdk1.8.0_144/include/win32)
elseif(MSVC)
  set(CMAKE_CXX_FLAGS "/MP /EHsc /DRTI_USES_STD_FSTREAM /wd4251 /wd4290")
  include_directories(V:/opt/openRTI/v0_9_0/release/include/rti1516e V:/jdk1.8.0_144/include V:/jdk1.8.0_144/include/win32)
  link_directories(V:/opt/openRTI/v0_9_0/msys/release/lib)	
endif(MSYS)


add_library(javarti SHARED 
                Utils.hpp
                Utils.cpp
                hla_rti1516e_openrti_JniObject.h
                hla_rti1516e_openrti_JniObject.cpp
                hla_rti1516e_openrti_RtiFactoryImpl.h
                hla_rti1516e_openrti_RtiFactoryImpl.cpp
                hla_rti1516e_openrti_RTIambassadorImpl.h
                hla_rti1516e_openrti_RTIambassadorImpl.cpp
                hla_rti1516e_openrti_AttributeHandleImpl.h
                hla_rti1516e_openrti_AttributeHandleImpl.cpp
                hla_rti1516e_openrti_ParameterHandleImpl.h
                hla_rti1516e_openrti_ParameterHandleImpl.cpp
                hla_rti1516e_openrti_FederateHandleImpl.h
                hla_rti1516e_openrti_FederateHandleImpl.cpp
                hla_rti1516e_openrti_ObjectClassHandleImpl.h
                hla_rti1516e_openrti_ObjectClassHandleImpl.cpp
                hla_rti1516e_openrti_ObjectInstanceHandleImpl.h
                hla_rti1516e_openrti_ObjectInstanceHandleImpl.cpp
                FederateAmbassadorJava.hpp
                FederateAmbassadorJava.cpp
                )
                   
target_link_libraries(javarti ${RTI_LIBRARIES})

                        
                        