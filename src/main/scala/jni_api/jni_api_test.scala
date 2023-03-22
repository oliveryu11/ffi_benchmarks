// package declaration
package jni_api

import scala.jdk.CollectionConverters._


object JniAPI {
  /* Define and load the path of the bridge C library */
  val soPath = os.pwd / "target" / "native" / "include" / "libjnibridge.so"
  System.load(soPath.toString())

  /* Takes in a path and returns a unique shared object id that corresponds with the shared object at that path */
  @native def load_so(path: String): Int

  @native def call_sim_init(so_id: Int): long

  /* Takes in shared object id and calls C library which elegates the call to the shared object associated with the provided id */
  // @native def call_add_one(id: Int, arg: Int): Int

  @native def call_step(so_id: Int, cycles: Int): Int

  @native def call_update(so_id: Int): Unit

  @native def call_finish(so_id: Int): Unit

  @native def call_resetCoverage(so_id: Int): Unit

  @native def call_writeCoverage(so_id: Int, filename: String): Unit

  @native def call_poke(so_id: Int, id: Int, value: Int): Int

  @native def call_peek(so_id: Int, id: Int): Int

  @native def call_poke_wide(so_id: Int, id: Int, offset: Int, value: Int): Unit

  @native def call_peek_wide(so_id: Int, id: Int, offset: Int): Int

  @native def set_args(so_id: Int, argc: Int, argv: String): Unit

  def main(args: Array[String]): Unit = {
    // Load two shared objects with same interface but different implementations
    // val soPath = os.pwd / "src" / "resources" / "temp_lib.so"
    // println(soPath.toString())
    // val id_1 = JniAPI.load_so(soPath.toString())

    // val soPath_2 = os.pwd / "src" / "resources" / "temp_lib_2.so"
    // println(soPath_2.toString())
    // val id_2 = JniAPI.load_so(soPath_2.toString())

    // // Call same native function on the different shared objects
    // val result_1 = call_add_one(id_1, 1)
    // val result_2 = JniAPI.call_add_one(id_2, 1)
    
    // // Print output to ensure calls where properly delegated and output is expected
    // println(result_1)
    // println(result_2)

    val soPath = os.pwd / "src" / "resources" / "VFoo"

    val vfoo_id = JniAPI.load_so(soPath.toString())

    val ptr = call_sim_init(vfoo_id)
    println(ptr)
    call_poke(vfoo_id, 0, 1)
    // call_update(vfoo_id) // force reevaluation of the DUT (for combinational paths)
    // val output = call_peek(vfoo_id, 1)
    // assert(output == 1)
  }
}

