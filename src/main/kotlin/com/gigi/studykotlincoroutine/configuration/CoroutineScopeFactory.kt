package com.gigi.studykotlincoroutine.configuration

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoroutineScopeFactory {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun remoteClientCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Bean
    fun computationCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Bean(name = ["coroutineExceptionHandler"])
    fun coroutineExceptionHandler() = CoroutineExceptionHandler { it, throwable ->
        logger.error("exception occured ${it.job}", throwable)
        // call monitoring service
    }
}
