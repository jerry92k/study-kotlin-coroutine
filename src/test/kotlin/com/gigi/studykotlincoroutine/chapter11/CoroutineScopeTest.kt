package com.gigi.studykotlincoroutine.chapter11

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class CoroutineScopeTest {

    @Test
    fun test() {

        runBlocking {
            val job = launch(CoroutineName("Parent")) {
                longTask()
                println("parent finish")
            }
            println("1")
            delay(1500)
            println("5")
            job.cancel()
            println("6")
        }
    }

    suspend fun longTask() = coroutineScope{
        launch {
            println("2")
            delay(1000)
            println("4")
            val name = coroutineContext[CoroutineName]?.name
            println("[$name] Finished Task 1")
        }

        launch {
            println("3")
            delay(2000)
            println("7")
            val name = coroutineContext[CoroutineName]?.name
            println("[$name] Finished Task 2")
        }
    }
}
