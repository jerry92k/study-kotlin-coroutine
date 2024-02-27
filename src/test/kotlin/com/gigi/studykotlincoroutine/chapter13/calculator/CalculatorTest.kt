package com.gigi.studykotlincoroutine.chapter13.calculator

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class CalculatorTest {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    val coroutineExceptionHandler = CoroutineExceptionHandler { it, throwable ->
        logger.error("[EXCEPTION OCCOURED][${it.job}]", throwable) // CancellationException 은 무시된다.
    }

    @Test
    fun `Job을 가진 scope 하위에서는 예외가 전파되고 scope을 재사용 할 수 없다`() {
        val coroutineContext = Dispatchers.Default + Job() + coroutineExceptionHandler
        val coroutineScope = CoroutineScope(coroutineContext)
        val calculator = Calculator(coroutineScope)
        val FAIL = true
        runBlocking {
            logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] runBlocking")
            withContext(Dispatchers.Default) { // 병렬 실행을 위해 main 스레드가 아닌 DefaultDispatcher 스레드풀로 변경한다.
                logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] withContext")
                launch {
                    logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] launch coroutine 1")
                    delay(1000)
                    calculator.calculate(FAIL)
                }
                launch {
                    logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] launch coroutine 2")
                    calculator.calculate()
                }
                delay(3000)
                launch {
                    // calculator 내 scope은 취소되어 실행해도 동작하지 않는다.
                    logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] launch coroutine 3")
                    calculator.calculate()
                }
                delay(2000)
            }
        }
    }

    @Test
    fun `SupervisorJob을 가진 scope 하위에서는 예외가 전파되지 않고 scope을 재사용 할 수 있다`() {
        val coroutineContext = Dispatchers.Default + SupervisorJob() + coroutineExceptionHandler
        val coroutineScope = CoroutineScope(coroutineContext)
        val calculator = Calculator(coroutineScope)
        val FAIL = true
        runBlocking {
            logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] runBlocking")
            withContext(Dispatchers.Default) { // 병렬 실행을 위해 main 스레드가 아닌 DefaultDispatcher 스레드풀로 변경한다.
                logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] withContext")
                launch {
                    logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] launch coroutine 1")
                    delay(1000)
                    calculator.calculate(FAIL)
                }
                launch {
                    logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] launch coroutine 2")
                    calculator.calculate()
                }
                delay(3000)
                launch {
                    // calculator 내 scope은 취소되어 실행해도 동작하지 않는다.
                    logger.info("[${Thread.currentThread()}][JOB-${coroutineContext.job}],[PARENT-${coroutineContext.job.parent}] launch coroutine 3")
                    calculator.calculate()
                }
                delay(2000)
            }
        }
    }
}
