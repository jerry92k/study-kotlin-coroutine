package com.gigi.studykotlincoroutine.service

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.coroutines.coroutineContext

@Service
class HelloService(
    private val coroutineExceptionHandler: CoroutineExceptionHandler
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    suspend fun hello() {
        logger.info("1 ${coroutineContext.job}, parent : ${coroutineContext.job.parent}")
//        val coroutineScope = CoroutineScope(SupervisorJob() + coroutineExceptionHandler)
//        supervisorScope {
//            val job = coroutineContext.job
//            val numbers = (1..100_000L).chunked(10000)
//            launch(coroutineExceptionHandler) {
//                    throw Error()
////                coroutineContext.job.cancel()
//                println("canceled")
//            }
//            numbers.forEach {
//
//                launch(coroutineExceptionHandler) {
//                    logger.info("4 ${coroutineContext.job}, parent: ${coroutineContext.job.parent}, context : ${coroutineContext}")
//                    println(it.sum())
//                    delay(3000L)
//                    println(it.sum())
//                    println("done")
//                }
//            }
////            }
//
//            println("here1")
//            delay(100)
////            job.cancel()
//            println("here2")
//            println(job)
//        }
    }
}

