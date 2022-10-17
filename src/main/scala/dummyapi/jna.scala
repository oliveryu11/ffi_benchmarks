package dummyapi

import com.sun.jna._

// class HelloWorld { 
//   Native.register("m"); 
//   @native def cos(d: Double): Double; 
//   @native def sin(d: Double): Double;
// }

// trait libdummy_api extends Library {
//   def get_favorite_number():Int
// }

class DummyApi {
  // private var _libc:libc = null
  // def run():libc = {
  //   if ( _libc == null ) {
  //     _libc = Native.load("dummy_api", classOf[libc]).asInstanceOf[libc]
  //   }
  //   _libc
  // }

  
  // Native.load("/home/oliveryu/jna_test/src/main/scala/dummyapi/libdummyapi.so")
  // System.loadLibrary("dummyapi")
  // val libPath = os.
  // val javaHome = System.getProperty("java.home")
  // val osIncludeName = System.getProperty("os.name")
  // println(javaHome, osIncludeName);

  val source = this.getClass.getResource("/libdummyapi.so").toString().substring(5)
  val so = NativeLibrary.getInstance(source)
  val c_add_one = so.getFunction("add_one")

  def addOne(x: Int): Unit = { 
    c_add_one.invoke(Array(c_add_one.invokePointer(Array()), Integer.valueOf(x)))
  }

  // val source = this.getClass.getResource("/libdummyapi.so").toString().substring(5)
  // println(source)
  // System.load(source)

  // @native def addOne(x: Int): Int
}