#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_ming_com_avlearner_ctest_CTestActivity_stringFromJNI(JNIEnv *env, jobject instance) {

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}