// package declaration
package jni_api

object JniAPI {
  /* Define and load the path of the bridge C library */
  val soPath = os.pwd / "target" / "native" / "include" / "libjnibridge.so"
  println(soPath.toString())
  val check_property = System.getProperty("jnibridge.loaded");
  println(check_property)
  if (System.getProperty("jnibridge.loaded") != "true") {
    println("Loading libjnibridge")
    System.load(soPath.toString())
    System.setProperty("jnibridge.loaded", "true")
  }

  /* Takes in a path and returns a unique shared object id that corresponds with the shared object at that path */
  @native def load_so(path: String): Int
  
  /* Takes in shared object id and calls C library which elegates the call to the shared object associated with the provided id */
  @native def call_add_one(id: Int, arg: Int): Int

  def main(args: Array[String]): Unit = {
    // Load two shared objects with same interface but different implementations
    val soPath = os.pwd / "src" / "resources" / "temp_lib.so"
    println(soPath.toString())
    val id_1 = JniAPI.load_so(soPath.toString())

    val soPath_2 = os.pwd / "src" / "resources" / "temp_lib_2.so"
    println(soPath_2.toString())
    val id_2 = JniAPI.load_so(soPath_2.toString())

    // Call same native function on the different shared objects
    val result_1 = call_add_one(id_1, 1)
    val result_2 = JniAPI.call_add_one(id_2, 1)
    
    // Print output to ensure calls where properly delegated and output is expected
    println(result_1)
    println(result_2)
  }
}