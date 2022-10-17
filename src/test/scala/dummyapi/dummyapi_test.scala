package dummyapi

import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class dummy_api_test extends AnyFlatSpec {

  "DummyApi" should "get my favorite number" in {
    val myLibrary = new DummyApi()
    println(myLibrary.addOne(1))
    // println(System.getProperty("java.home"))
    // println(System.getProperty("os.name"))
    
    // val x = new HelloWorld()
    // println("d", x.sin(42))
    // assert(stack.pop() === 2)
  }
    
}