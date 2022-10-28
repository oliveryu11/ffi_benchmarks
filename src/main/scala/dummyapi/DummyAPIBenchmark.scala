package dummyapi

import com.sun.jna._
import os._
import org.openjdk.jmh.annotations.Benchmark

class DummyAPIBenchmark {

    // val dummyapi = new DummyAPI()
    
    @Benchmark
    def testMethod() = {
        // val soPath = os.pwd / "dummyapi" / "libdummyapi.so"
        // val so = NativeLibrary.getInstance(soPath.toString())
        // val add_one = so.getFunction("add_one")
        DummyAPI.add_one(1)
        // val ret = add_one.invokePointer(Array(1))
        // println(ret)
    }
    
}