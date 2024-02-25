package com.gigi.studykotlincoroutine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class SpringBootCoroutineApplication

fun main(args: Array<String>) {
    runApplication<SpringBootCoroutineApplication>(*args)
}
