#include "jni_lib.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h> 
#include <pthread.h>

// Maps ID --> ptr to shared object
static void *map[1024];

// id_counter (increment by one)
pthread_mutex_t id_lock; 
static int id_counter = 0; // synchronize the id_counter to be thread-safe

int loadSO(char *so_name) {
    // Load shared object
    void *so_handle = dlopen(so_name, RTLD_LAZY);

    // Check if load was successful
    if (so_handle == NULL) {
        fprintf (stderr, "%s\n", dlerror());
        exit(1);
    }

    pthread_mutex_lock(&id_lock);
    int so_id = id_counter;
    id_counter++;
    pthread_mutex_unlock(&id_lock);

    // Store pointer to shared object in map
    map[so_id] = so_handle; // pointer to SO

    // Return shared object id
    return so_id;
}

int call_add_one(int so_id, int arg) { 
    // Get shared object mapped to by ID
    void *handle = map[so_id];
    
    // Declare desired function
    int (*add_one)(int);

    // *(void **) (&add_one) = dlsym(handle, "Java_dummyapijni_DummyAPI_00024_add_1one");

    // Testing non JNI shared object
    *(void **) (&add_one) = dlsym(handle, "add_one_c");

    char *error;
    if ((error = dlerror()) != NULL)  {
        fprintf(stderr, "%s\n", error);
        exit(EXIT_FAILURE);
    }

    // Invoke API on given shared object
    return add_one(arg);
}

void init_jni_lib() {
    pthread_mutex_init(&id_lock, NULL);
}

int main(int argc, char *argv[]) {
    init_jni_lib();

    // int so_id = loadSO("/home/oliveryu/ffi_benchmarks/target/native/include/libdummyapi.so");
    int so_id = loadSO("/home/oliveryu/ffi_benchmarks/src/resources/temp_lib.so");

    printf("%d\n", (int) call_add_one(so_id, 10));
}