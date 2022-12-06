#include <dlfcn.h> // https://linux.die.net/man/3/dlopen


/* Takes in (absolute) path to shared object and returns a unique shared object identifier to a handle  */
int loadSO(char *so_name);

/* Invokes the "add_one" method of the shared object corresponding to the given so_id */
int call_add_one(int so_id, int arg);