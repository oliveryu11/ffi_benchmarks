package jni_api

import os._
import org.openjdk.jmh.annotations.Benchmark
import dummyapijni._

object JniAPIBenchmark {
    val soPath = os.pwd / "src" / "resources" / "temp_lib.so"
    val id_1 = JniAPI.load_so(soPath.toString())
}

class JniAPIBenchmark {

    @Benchmark
    def BridgeBenchmark() = {
        JniAPI.call_add_one(JniAPIBenchmark.id_1, 1)
    }

    @Benchmark
    def NoBridgeBenchmark() = {
        DummyAPI.add_one(1)
    }
}