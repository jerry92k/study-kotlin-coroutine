package com.gigi.studykotlincoroutine.chapter13.calculator

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CalculatorService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    suspend fun calculate(startValue: Int, endValue: Int) = supervisorScope {
        logger.info("[CONTEXT]${kotlin.coroutines.coroutineContext}, [PARENT] : ${kotlin.coroutines.coroutineContext.job.parent}")
        val chunks = (startValue..endValue).chunked(endValue - startValue / 10)
        val job = SupervisorJob()
        logger.info("[CONTEXT]${kotlin.coroutines.coroutineContext}, [PARENT] : ${kotlin.coroutines.coroutineContext.job.parent}")
        val deferredSums: List<Deferred<Int>> = chunks.map {
            async(job) {
                it.sum()
            }
        }
        var total = 0
        try {
            total = awaitAll(*deferredSums.toTypedArray()).sum()
        } catch (ex: Exception) {
            logger.error("[calculator scope] job catch exception ${coroutineContext.job}", ex)
            throw ex
        }
        total
    }

    fun calculateSync(startValue: Int, endValue: Int): Int {
        val chunks = (startValue..endValue).chunked(endValue - startValue / 10)
        return chunks.sumOf { it.sum() }
    }
}
