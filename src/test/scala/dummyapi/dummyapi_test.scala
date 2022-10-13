package dummyapi

import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class dummy_api_test extends AnyFlatSpec {

  "DummyApi" should "get my favorite number" in {
    val myLibrary = new DummyApi()
    println(myLibrary.add_one(1))

    
    // val x = new HelloWorld()
    // println("d", x.sin(42))
    // assert(stack.pop() === 2)
  }
    
}