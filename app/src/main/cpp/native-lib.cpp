#include <jni.h>
#include <string>

//JNICALL
//Java_com_package_name_Keys_apiKey(JNIEnv *env, jobject object) {
//    std::string api_key = "your_api_key_goes_here";
//    return env->NewStringUTF(api_key.c_str());
//}

extern "C" JNIEXPORT jstring JNICALL
Java_th_co_mfec_androidsecuritydemo_Keys_apiKey(JNIEnv *env, jobject object) {
    std::string api_key = "1000:8692efde97c0369e6181a8a2626c90f8:870f1a6c016753c8e9a48f7cf6816aff1e6d896ed93e7055d14b2e2aee32b83e92dceda9862c338caba44a6fcf4193184fee8a0ad8144d33b511fe8876145428";
    return env->NewStringUTF(api_key.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_th_co_mfec_androidsecuritydemo_Keys_signatureKey(JNIEnv *env, jobject object) {
    std::string signature_key = "1000:8692efde97c0369e6181a8a2626c90f8:870f1a6c016753c8e9a48f7cf6816aff1e6d896ed93e7055d14b2e2aee32b83e92dceda9862c338caba44a6fcf4193184fee8a0ad8144d33b511fe8876145428";
    return env->NewStringUTF(signature_key.c_str());
}