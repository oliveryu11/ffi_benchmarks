package dummyapijni

// import os._


object DummyAPI {
  val soPath = os.pwd / "target" / "native" / "include" / "libdummyapi.so"
  System.load(soPath.toString())

  /* Commented out for benchmarking purposes */

  // println(soPath.toString())

  // val check_property = System.getProperty("dummyapijni.loaded");
  // println(check_property)
  // if (System.getProperty("dummyapijni.loaded") != "true") {
  //   println("Loading libdummyapi")
  //   System.load(soPath.toString())
  //   System.setProperty("dummyapijni.loaded", "true")
  // }
  
  @native def add_one(i: Int): Int 

  def main(args: Array[String]): Unit = {
    val result = DummyAPI.add_one(1)
    println(s"add_one: $result")
  }
}

// object DummyAPI {
//   def main(args: Array[String]): Unit = {
//     // System.loadLibrary("DummyAPI") // requires adding DummyAPI shared object into java library path which I'm not too sure how to do
//     System.load("/home/oliveryu/jni_test/target/native/include/libdummyapi.so")
//     val sample = new DummyAPI
//     val result = sample.add_one(1)
//     println(s"add_one: $result")
//   }
// }