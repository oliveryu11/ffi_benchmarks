#include "jni_1api_JniAPI_00024.h"
#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h> 
#include <pthread.h>
#define _GNU_SOURCE
#include <dlfcn.h>

// Maps ID --> ptr to shared object
void *map[1024];
// void *add_one_ptrs[1024];
void *sim_init_ptrs[1024];
void *step_ptrs[1024];
void *finish_ptrs[1024];
void *update_ptrs[1024];
void *reset_coverage_ptrs[1024];
void *write_coverage_ptrs[1024];
void *poke_ptrs[1024];
void *peek_ptrs[1024];
void *poke_wide_ptrs[1024];
void *peek_wide_ptrs[1024];
void *set_args_ptrs[1024];

// id_counter (increment by one)
pthread_mutex_t id_lock; 
int id_counter = 0; // synchronize the id_counter to be thread-safe

/* Native implementation for load_so (see jni_api in jni_api_test.scala) */
JNIEXPORT jint JNICALL Java_jni_1api_JniAPI_00024_load_1so
  (JNIEnv* env, jobject obj, jstring path) {
    // Convert jstring --> C string
    const char* path_str = (*env)->GetStringUTFChars(env, path, 0);
    char cap[128];
    strcpy(cap, path_str);
    (*env)->ReleaseStringUTFChars(env, path, path_str);
    // printf("%s", cap);

    // Load shared object
    void *so_handle = dlopen(cap, RTLD_LAZY);

    // Check if load was successful
    if (so_handle == NULL) {
        fprintf (stderr, "%s\n", dlerror());
        exit(1);
    }

    pthread_mutex_lock(&id_lock);
    int so_id = id_counter;
    id_counter++;
    pthread_mutex_unlock(&id_lock);

    // // Declare desired function
    // int (*add_one)(int);

    // // Get function pointer for native add_one implementation
    // *(void **) (&add_one) = dlsym(so_handle, "add_one_c");

    char *error;
    if ((error = dlerror()) != NULL)  {
        fprintf(stderr, "%s\n", error);
        exit(EXIT_FAILURE);
    }

    // Store pointer to shared object in map
    // map[so_id] = so_handle; // pointer to SO
    // add_one_ptrs[so_id] = add_one;

    /* Accessing Harness APIs */
    // TODO: make it into some for loop for simplification

    void* (*sim_init)();
    *(void **) (&sim_init) = dlsym(so_handle, "sim_init");
    sim_init_ptrs[so_id] = sim_init;

    int (*step)(int);
    *(void **) (&step) = dlsym(so_handle, "step");
    step_ptrs[so_id] = step;

    int (*update)(int);
    *(void **) (&update) = dlsym(so_handle, "update");
    step_ptrs[so_id] = step;

    int (*finish)(int);
    *(void **) (&finish) = dlsym(so_handle, "finish");
    finish_ptrs[so_id] = finish;

    int (*reset_coverage)(int);
    *(void **) (&reset_coverage) = dlsym(so_handle, "resetCoverage");
    reset_coverage_ptrs[so_id] = reset_coverage;

    int (*write_coverage)(int);
    *(void **) (&write_coverage) = dlsym(so_handle, "writeCoverage");
    write_coverage_ptrs[so_id] = step;

    int (*poke)(int);
    *(void **) (&poke) = dlsym(so_handle, "poke");
    poke_ptrs[so_id] = poke;

    int (*peek)(int);
    *(void **) (&peek) = dlsym(so_handle, "peek");
    peek_ptrs[so_id] = peek;

    int (*poke_wide)(int);
    *(void **) (&poke_wide) = dlsym(so_handle, "poke_wide");
    poke_wide_ptrs[so_id] = poke_wide;

    int (*peek_wide)(int);
    *(void **) (&peek_wide) = dlsym(so_handle, "peek_wide");
    peek_wide_ptrs[so_id] = peek_wide;

    int (*set_args)(int);
    *(void **) (&set_args) = dlsym(so_handle, "set_args");
    set_args_ptrs[so_id] = set_args;

    // Return shared object id
    return so_id;
  }

JNIEXPORT void JNICALL Java_jni_1api_JniAPI_00024_call_1sim_1init
  (JNIEnv *env, jobject obj, jint so_id) {
    void (*sim_init)();
    *(void **) (&sim_init) = sim_init_ptrs[so_id];

    // Invoke API on given shared object
    return sim_init();
  }

/*
 * Class:      jni_1api_JniAPI_00024
 * Method:     call_1step
 * Signature:  (I)I
 */
JNIEXPORT jint JNICALL Java_jni_1api_JniAPI_00024_call_1step
  (JNIEnv *env, jobject obj, jint so_id, jint cycles) {
    int64_t (*step)(int32_t);
    *(void **) (&step) = step_ptrs[so_id];

    return step(cycles);
  }

/*
 * Class:      jni_1api_JniAPI_00024
 * Method:     call_1update
 * Signature:  ()V
 */
JNIEXPORT void JNICALL Java_jni_1api_JniAPI_00024_call_1update
  (JNIEnv *env, jobject obj, jint so_id) {
    void (*update)();
    *(void **) (&update) = update_ptrs[so_id];

    update();
  }

/*
 * Class:      jni_1api_JniAPI_00024
 * Method:     call_1finish
 * Signature:  ()V
 */
JNIEXPORT void JNICALL Java_jni_1api_JniAPI_00024_call_1finish
  (JNIEnv *env, jobject obj, jint so_id) {
    void (*finish)();
    *(void **) (&finish) = finish_ptrs[so_id];

    finish();
  }

/*
 * Class:      jni_1api_JniAPI_00024
 * Method:     call_1resetCoverage
 * Signature:  ()V
 */
JNIEXPORT void JNICALL Java_jni_1api_JniAPI_00024_call_1resetCoverage
  (JNIEnv *env, jobject obj, jint so_id) {
    void (*reset_coverage)();
    *(void **) (&reset_coverage) = reset_coverage_ptrs[so_id];

    reset_coverage();
  }

/*
 * Class:      jni_1api_JniAPI_00024
 * Method:     call_1writeCoverage
 * Signature:  (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_jni_1api_JniAPI_00024_call_1writeCoverage
  (JNIEnv *env, jobject obj, jint so_id, jstring filename) {
    const char* filename_str = (*env)->GetStringUTFChars(env, filename, 0);
    char cap[128];
    strcpy(cap, filename_str);
    (*env)->ReleaseStringUTFChars(env, filename, filename_str);
    void (*write_coverage)(char*);
    *(void **) (&write_coverage) = write_coverage_ptrs[so_id];

    write_coverage(cap);
  }

/*
 * Class:      jni_1api_JniAPI_00024
 * Method:     call_1poke
 * Signature:  (II)I
 */
JNIEXPORT jint JNICALL Java_jni_1api_JniAPI_00024_call_1poke
  (JNIEnv *env, jobject obj, jint so_id, jint id, jint value) {
    void (*poke)(int32_t, int64_t);
    *(void **) (&poke) = poke_ptrs[so_id];

    poke(id, value);
  }

/*
 * Class:      jni_1api_JniAPI_00024
 * Method:     call_1peek
 * Signature:  (III)I
 */
JNIEXPORT jint JNICALL Java_jni_1api_JniAPI_00024_call_1peek
  (JNIEnv *env, jobject obj, jint so_id, jint id) {
    int64_t (*peek)(int32_t);
    *(void **) (&peek) = peek_ptrs[so_id];

    return peek(id);
  }

/*
 * Class:      jni_1api_JniAPI_00024
 * Method:     call_1poke_1wide
 * Signature:  (III)V
 */
JNIEXPORT void JNICALL Java_jni_1api_JniAPI_00024_call_1poke_1wide
  (JNIEnv *env, jobject obj, jint so_id, jint id, jint offset, jint value) {
    void (*poke_wide)(int32_t, int32_t, int64_t);
    *(void **) (&poke_wide) = poke_wide_ptrs[so_id];

    poke_wide(id, offset, value);
  }

/*
 * Class:      jni_1api_JniAPI_00024
 * Method:     call_1peek_1wide
 * Signature:  (II)I
 */
JNIEXPORT jint JNICALL Java_jni_1api_JniAPI_00024_call_1peek_1wide
  (JNIEnv *env, jobject obj, jint so_id, jint id, jint offset) {
    int64_t (*peek_wide)(int32_t, int32_t);
    *(void **) (&peek_wide) = peek_wide_ptrs[so_id];

    return peek_wide(id, offset);
  }

JNIEXPORT void JNICALL Java_jni_1api_JniAPI_00024_set_1args
  (JNIEnv *env, jobject obj, jint so_id, jint argc, jstring argv) {
    const char* argv_str = (*env)->GetStringUTFChars(env, argv, 0);
    char cap[128];
    strcpy(cap, argv_str);
    (*env)->ReleaseStringUTFChars(env, argv, argv_str);
    void (*set_args)(int32_t, char **);
    *(void **) (&set_args) = set_args_ptrs[so_id];

    set_args(argc, (char **) cap);
  }

