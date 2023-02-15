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
void *add_one_ptrs[1024];

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
    printf("%s", cap);

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

    // Declare desired function
    int (*add_one)(int);

    // Get function pointer for native add_one implementation
    *(void **) (&add_one) = dlsym(so_handle, "add_one_c");

    // Store pointer to shared object in map
    // map[so_id] = so_handle; // pointer to SO
    add_one_ptrs[so_id] = add_one;

    // Return shared object id
    return so_id;
  }


/* Native implementation for call_add_one (see jni_api in jni_api_test.scala) */
JNIEXPORT jint JNICALL Java_jni_1api_JniAPI_00024_call_1add_1one
  (JNIEnv* env, jobject obj, jint so_id, jint arg) {
    // Get shared object mapped to by ID
    int (*add_one)(int);
    *(void **) (&add_one) = add_one_ptrs[so_id];
    
    char *error;
    if ((error = dlerror()) != NULL)  {
        fprintf(stderr, "%s\n", error);
        exit(EXIT_FAILURE);
    }

    // Invoke API on given shared object
    return add_one(arg);

    // return 1;
  }