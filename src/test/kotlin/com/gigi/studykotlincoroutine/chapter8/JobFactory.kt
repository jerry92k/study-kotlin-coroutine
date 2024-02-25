package com.gigi.studykotlincoroutine.chapter8

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class JobFactory {

    @Test
    fun `영원히 대기하게 된다`() {
        runBlocking {
            val job = Job()
            launch(job) {
                delay(1000)
                println("text 1")
            }

            launch(job) {
                delay(2000)
                println("text 2")
            }

            job.join() // job이 Active 상태여서 영원히 대기하게 됨
            println("Will not be printed")
        }
    }

    @Test
    fun `job 상태를 Complete로 명시적으로 변경하면 정상 종료 가능하다`() {
        runBlocking {
            val job = Job()
            launch(job) {
                delay(1000)
                println("text 1")
            }

            launch(job) {
                delay(2000)
                println("text 2")
            }
            job.complete()
            job.join() // job이 Active 상태여서 영원히 대기하게 됨
        }
    }

    @Test
    fun `자식들의 job을 명시적으로 join을 호출한다`() {
        runBlocking {
            val job = Job()
            launch(job) {
                delay(1000)
                println("text 1")
            }

            launch(job) {
                delay(2000)
                println("text 2")
            }
            job.children.forEach { it.join() }
        }
    }
}
