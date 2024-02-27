package com.gigi.studykotlincoroutine.chapter13

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.coroutineContext

class Calculator private constructor(
    private val scope: CoroutineScope
) {

    companion object {

        private val logger = LoggerFactory.getLogger(this.javaClass)

        val coroutineExceptionHandler = CoroutineExceptionHandler { it, throwable ->
            logger.error("[EXCEPTION OCCOURED][${it.job}]", throwable) // CancellationException 은 무시된다.
        }

        fun getInstanceWithJob(): Calculator {
            val coroutineScope = CoroutineScope(
                Dispatchers.Default
                        + Job()
                        + coroutineExceptionHandler
            )
            return Calculator(coroutineScope)
        }

        fun getInstanceWithSupervisorJob(): Calculator {
            val coroutineScope = CoroutineScope(
                Dispatchers.Default
                        + SupervisorJob()
                        + coroutineExceptionHandler
            )
            return Calculator(coroutineScope)
        }
    }

    suspend fun calculateWithLaunch(cancel: Boolean = false) {
        logger.info("[calculator scope] job : ${scope.coroutineContext.job}, parent job : ${scope.coroutineContext.job.parent}")
        scope.launch {
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

    suspend fun calculateWithAsync(cancel: Boolean = false): Long {
        logger.info("[calculator scope] job : ${scope.coroutineContext.job}, parent job : ${scope.coroutineContext.job.parent}")
        val chunks = (1..3000L).chunked(1000)
        val deferredSums: List<Deferred<Long>> = chunks.map {
            scope.async {
                if (cancel) {
                    logger.info("[calculator scope] job exception occur : ${coroutineContext.job}")
                    throw RuntimeException()
                }
                it.sum()
            }
        }
        var total = 0L
        try {
            total = awaitAll(*deferredSums.toTypedArray()).sum()
        } catch (ex: Exception) {
            logger.info("[calculator scope] job catch exception ${coroutineContext.job} ")
            println(ex)
        }
        println(coroutineContext.job)
        return total
    }
}

