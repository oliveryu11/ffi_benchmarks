package dummyapijna

import com.sun.jna._
import os._

object DummyAPI {

  // val soPath = os.pwd / "dummyapi" / "libdummyapi.so"
  // val so = NativeLibrary.getInstance(soPath.toString())
  // val add_one = so.getFunction("add_one")
  // val ret = add_one.invokePointer(Array(1))
  // println(ret)

  val soPath = os.pwd / "dummyapi" / "libdummyapi.so"
  val so = NativeLibrary.getInstance(soPath.toString())
  val add_one_native = so.getFunction("add_one")

  def add_one(i: Int) = {
    val ret = add_one_native.invokePointer(Array(i))
  }
  
  // Native.load("/home/oliveryu/jna_test/src/main/scala/dummyapi/libdummyapi.so")
  // System.loadLibrary("dummyapi")
  // val libPath = os.
  // val javaHome = System.getProperty("java.home")
  // val osIncludeName = System.getProperty("os.name")
  // println(javaHome, osIncludeName);

  //val so = os.resource() / "libdummyapi.so"

  //val source = this.getClass.getResource("/libdummyapi.so").toString().substring(5)
  //val so = NativeLibrary.getInstance(source)
  //val c_add_one = so.getFunction("add_one")

  //def addOne(x: Int): Unit = {
    //c_add_one.invoke(Array(c_add_one.invokePointer(Array()), Integer.valueOf(x)))
  //}

  // val source = this.getClass.getResource("/libdummyapi.so").toString().substring(5)
  // println(source)
  // System.load(source)

  // @native def addOne(x: Int): Int
}