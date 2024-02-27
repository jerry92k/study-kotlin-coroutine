package com.gigi.studykotlincoroutine.controller

import com.gigi.studykotlincoroutine.service.HelloService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.coroutineContext

@RestController
class HelloController(
    private val helloService: HelloService
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/v1/hello-sync")
    fun sayHelloSync(): String {
        // MonoCoroutine, Dispatchers.Unconfined
        "test"
        runBlocking {
            logger.info("[mvc controller scope] context : ${coroutineContext}, parent : ${coroutineContext.job.parent}\n ")
            helloService.hello()
        }

        return "hello"
    }

    @GetMapping("/v1/hello")
    suspend fun sayHello(): String {
        // MonoCoroutine, Dispatchers.Unconfined
        logger.info("[mvc controller scope] context : ${coroutineContext}, parent : ${coroutineContext.job.parent}\n ")
        helloService.hello()
        return "hello"
    }

}
