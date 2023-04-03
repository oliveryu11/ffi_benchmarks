// package declaration
package jni_api

import scala.jdk.CollectionConverters._


object JniAPI {
  /* Define and load the path of the bridge C library */
  val soPath = os.pwd / "target" / "native" / "include" / "libjnibridge.so"
  System.load(soPath.toString())

  /* Takes in a path and returns a unique shared object id that corresponds with the shared object at that path */
  @native def load_so(path: String): Int

  @native def call_sim_init(so_id: Int): Long 

  /* Takes in shared object id and calls C library which elegates the call to the shared object associated with the provided id */
  // @native def call_add_one(id: Int, arg: Int): Int

  @native def call_step(so_id: Int, s: Long, cycles: Int): Int

  @native def call_update(so_id: Int, s: Long): Unit

  @native def call_finish(so_id: Int, s: Long): Unit

  @native def call_resetCoverage(so_id: Int, s: Long): Unit

  @native def call_writeCoverage(so_id: Int, s: Long, filename: String): Unit

  @native def call_poke(so_id: Int, s: Long, id: Int, value: Int): Int

  @native def call_peek(so_id: Int, s: Long, id: Int): Int

  @native def call_poke_wide(so_id: Int, s: Long, id: Int, offset: Int, value: Int): Unit

  @native def call_peek_wide(so_id: Int, s: Long, id: Int, offset: Int): Int

  @native def call_set_args(so_id: Int, s: Long, argc: Int, argv: String): Unit

  def main(args: Array[String]): Unit = {

    val soPath = os.pwd / "src" / "resources" / "VFoo"
    val vfoo_id = JniAPI.load_so(soPath.toString())
    val vfoo_id2 = JniAPI.load_so(soPath.toString())


    val ptr = call_sim_init(vfoo_id)
    val ptr2 = call_sim_init(vfoo_id2)

    call_poke(vfoo_id, ptr, 1, 10)
    call_poke(vfoo_id2, ptr2, 1, 20)
    // call_update(vfoo_id, ptr) // force reevaluation of the DUT (for combinational paths)
    // call_finish(vfoo_id, ptr)
    // call_resetCoverage(vfoo_id, ptr)
    // call_writeCoverage(vfoo_id, ptr)
    // call_poke_wide(vfoo_id, ptr)
    // call_peek_wide(vfoo_id, ptr)
    // call_set_args
    val output = call_peek(vfoo_id, ptr, 1)
    val output2 = call_peek(vfoo_id2, ptr2, 1)

    println(output)
    println(output2)
    assert(output == 10)
    assert(output2 == 20)

    call_poke(vfoo_id, ptr, 1, 30)
    call_poke(vfoo_id2, ptr2, 1, 40)

    val output_1 = call_peek(vfoo_id, ptr, 1)
    val output2_1 = call_peek(vfoo_id2, ptr2, 1)

    println(output_1)
    println(output2_1)
    assert(output_1 == 30)
    assert(output2_1 == 40)
  }
}

