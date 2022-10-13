package dummyapi

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Platform

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
  System.load("/home/oliveryu/jna_test/src/main/scala/dummyapi/libdummyapi.so")
  // System.loadLibrary("dummyapi")
  @native def add_one(x: Int): Int
}