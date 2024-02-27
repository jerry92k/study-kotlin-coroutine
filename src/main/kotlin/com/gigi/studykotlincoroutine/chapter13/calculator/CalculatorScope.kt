package com.gigi.studykotlincoroutine.chapter13.calculator

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

class CalculatorScope(override val coroutineContext: CoroutineContext) : CoroutineScope {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    suspend fun calculate(cancel: Boolean = false) {
        logger.info("[calculator scope] job : ${coroutineContext.job}, parent job : ${coroutineContext.job.parent}")
        launch {
            if (cancel) {
                logger.info("[calculator scope] job exception occur : ${coroutineContext.job}")
                throw RuntimeException()
            }
            try {
                delay(1000)
            } catch (ex: CancellationException) {
                logger.error("[calculator scope] job cancelled : ${kotlin.coroutines.coroutineContext.job}", ex)
                throw ex
            }
            logger.info("[calculator scope] job success : ${coroutineContext.job}")
        }
    }
}


