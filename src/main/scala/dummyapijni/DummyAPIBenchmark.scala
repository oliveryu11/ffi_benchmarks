package dummyapijni

import os._
import org.openjdk.jmh.annotations.Benchmark

class DummyAPIBenchmark {
    
    @Benchmark
    def testMethod() = {
        DummyAPI.add_one(1)
    }
    
}