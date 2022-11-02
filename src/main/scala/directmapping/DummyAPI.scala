package directmapping

import com.sun.jna._

object DummyAPI {
            
    // @native def cos(x: Double): Double
    // @native def sin(x: Double): Double
    @native def add_one(i: Int): Int
    
    // Native.register(Platform.C_LIBRARY_NAME)
    Native.register("dummyapi/libdummyapi.so")

    def main(args: Array[String]): Unit = {
        // println("cos(0)=" + cos(0))
        // println("sin(0)=" + sin(0))
        println("add_one(1)=" + add_one(1))
    }

}