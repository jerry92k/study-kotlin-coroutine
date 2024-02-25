package com.gigi.studykotlincoroutine.chapter11

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class WithContextTest {

    @Test
    fun test() {
        runBlocking(CoroutineName("Parent")) {
            log("before")
            val beforeTime = System.currentTimeMillis()
            println(beforeTime)
            withContext(CoroutineName("Child 1")) {
                delay(1000)
                log("hello 1")
            }
            val afterChild1Time = System.currentTimeMillis()
            println(afterChild1Time - beforeTime)
            log("processing")
            withContext(CoroutineName("Child 2")) {
                delay(1000)
                log("hello 2")
            }
            val afterChild2Time = System.currentTimeMillis()
            println(afterChild2Time - afterChild1Time)

            log("after")
        }
    }

    fun CoroutineScope.log(text: String) {
        val name = this.coroutineContext[CoroutineName]?.name
        println("[$name] $text")
    }
}
