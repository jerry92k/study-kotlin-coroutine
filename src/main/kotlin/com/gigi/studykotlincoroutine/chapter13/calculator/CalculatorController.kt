package com.gigi.studykotlincoroutine.chapter13.calculator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.coroutineContext

@RestController
class CalculatorController(
    private val calculatorService: CalculatorService
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/v1/calculate")
    suspend fun calculate(
        @RequestParam(value="startValue") startValue: Int,
        @RequestParam(value="endValue") endValue: Int,
    ): Int {
        // MonoCoroutine, Dispatchers.Unconfined
        logger.info("${Thread.currentThread()} [CONTEXT]${coroutineContext}, [PARENT] : ${coroutineContext.job.parent}")
        val startTime = System.currentTimeMillis()
        val result = calculatorService.calculate(startValue, endValue)
        val endTime = System.currentTimeMillis()
        logger.info("times : ${endTime-startTime}")
        logger.info("${Thread.currentThread()}")
        return result
    }

    @GetMapping("/v1/calculate-sync")
    fun calculateSync(
        @RequestParam(value="startValue") startValue: Int,
        @RequestParam(value="endValue") endValue: Int,
    ): Int {
        runBlocking(Dispatchers.IO) {
            logger.info("${Thread.currentThread()} [CONTEXT]${kotlin.coroutines.coroutineContext}, [PARENT] : ${kotlin.coroutines.coroutineContext.job.parent}")
            val startTime = System.currentTimeMillis()
            val result = calculatorService.calculateSync(startValue, endValue)
            val endTime = System.currentTimeMillis()
            logger.info("times : ${endTime-startTime}")

        }
        return 1
    }
}
