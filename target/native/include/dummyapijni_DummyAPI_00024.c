#include "dummyapijni_DummyAPI_00024.h"

JNIEXPORT jint JNICALL Java_dummyapijni_DummyAPI_00024_add_1one
  (JNIEnv* env, jobject obj, jint num) {
    return num + 1; 
  }