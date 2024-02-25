package com.gigi.studykotlincoroutine.chapter7

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class CoroutineContextAndBuilder {

    @Test
    fun `부모로부터 상속받은 컨텍스트는 재정의 될 수 있다`() {
        runBlocking(CoroutineName("main")) {
            log("started")

            // child 1
            val v1 = async(CoroutineName("child 1")) {
                delay(500)
                log("Running async")
            }

            // child 2
            launch {
                delay(1000)
                log("Running launch")
            }

            // child 3
            launch(CoroutineName("child 3")) {
                delay(1000)
                log("Running launch")
            }
        }
    }

    private fun CoroutineScope.log(msg: String) {
        val name = coroutineContext[CoroutineName]?.name
        println("[$name] $msg")
    }

}
