package com.gigi.studykotlincoroutine.chapter13.calculator

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import java.lang.RuntimeException

class Calculator(private val scope: CoroutineScope) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    suspend fun calculate(cancel: Boolean = false) {
        scope.launch {
            logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] launch started ")
            if (cancel) {
                logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] cancel occur ")
                throw RuntimeException()
            }
            try {
                delay(2000)
            } catch (ex: CancellationException) {
                logger.error("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}]job cancelled ", ex)
                throw ex
            }
            logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}]job success job")
        }
    }
}
