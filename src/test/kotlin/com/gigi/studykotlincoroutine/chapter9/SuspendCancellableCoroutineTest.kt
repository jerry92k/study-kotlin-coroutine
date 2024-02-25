package com.gigi.studykotlincoroutine.chapter9

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.coroutines.resume

class SuspendCancellableCoroutineTest {

    @Test
    fun `하나의 coroutine scope 내에서는 한번에 하나씩만 처리된다`() {
        runBlocking {
            println("parent: ${this.coroutineContext.job}")
            val job = launch {
                while (true) {
                    someTask()
                }
            }

            println("job1 ${job.toString()}")
            delay(1L)
            println("job2 start")
            println("job2 ${job.toString()}")
            job.cancel()
            println("job3 ${job.toString()}")
            job.join()
        }
    }

    private suspend fun someTask() = suspendCancellableCoroutine { cont ->
        cont.invokeOnCancellation {
            println("invokeOnCacellation")
        }
        var a:Long=1
        for (i in 1..1000000L){
            a= a+i
        }
        val b= false
        if(b) {
            cont.resume("resume")
        }
    }

    private suspend fun someTask2() {

        var a:Long=1
        for (i in 1..1000000L){
            a= a+i
        }
//        println("")
        while (true) {

        }
    }
}
