package com.gigi.studykotlincoroutine.chapter12

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class CoroutineException {

    @Test
    fun `SupervisorJob의 자식은 일반 job인 경우 SupervisorJob까지는 예외가 전파된다`() {
        runBlocking {
            println("1 job ${coroutineContext.job}")
            launch(SupervisorJob()) { // job2를 SupervisorJob로 정의하는게 아니고, job2의 부모를 SupervisorJob 으로 정의한다.
                println("2 job ${coroutineContext.job}, parent ${coroutineContext.job.parent}")
                // job2는 부모가 SupervisorJob일뿐, 자신은 일반 job이여서 예외가 전파된다.
                launch {
                    println("3 job ${coroutineContext.job}, parent ${coroutineContext.job.parent}")
                    delay(1000)
                    throw RuntimeException()
                }
                launch {
                    println("4 job ${coroutineContext.job}, parent ${coroutineContext.job.parent}")
                    delay(2000)
                    println("will not be printed")
                }
            }
            delay(3000)
        }
    }

    @Test
    fun `SupervisorJob을 변수화해서 주입한다`() {
        runBlocking {
            val job = SupervisorJob()
            println("1 job ${coroutineContext.job}")
            println("2 job ${coroutineContext.job}, parent ${coroutineContext.job.parent}")
            launch(job) {
                println("3 job ${coroutineContext.job}, parent ${coroutineContext.job.parent}")
                delay(1000)
                throw RuntimeException()
            }
            launch(job) {
                println("4 job ${coroutineContext.job}, parent ${coroutineContext.job.parent}")
                delay(2000)
                println("will be printed")
            }
            delay(3000)
        }
    }

    @Test
    fun `SupervisorScope을 사용한다`() {
        runBlocking {
            println("1 job ${coroutineContext.job}")
            supervisorScope {
                println("2 job ${coroutineContext.job}, parent ${coroutineContext.job.parent}")
                launch {
                    println("3 job ${coroutineContext.job}, parent ${coroutineContext.job.parent}")
                    delay(1000)
                    throw RuntimeException()
                }
                launch {
                    println("4 job ${coroutineContext.job}, parent ${coroutineContext.job.parent}")
                    delay(2000)
                    println("will be printed")
                }
            }
            delay(3000)
        }
    }

    @Test
    fun `async를 사용하는 경우`() {
        runBlocking {
            supervisorScope {
                val str1 = async<String> {
                    delay(1000)
                    throw RuntimeException()
                }

                val str2 = async {
                    delay(2000)
                    "Text2"
                }

                try {
                    println(str1.await())
                }catch (e: Exception){
                    println(e)
                }
                println(str2.await())
            }
        }
    }
}
