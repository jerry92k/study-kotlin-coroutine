package com.gigi.studykotlincoroutine.chapter13

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class CalculatorTest {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun `Job을 가진 scope 하위에서는 예외가 전파되고 scope을 재사용 할 수 없다`() {
        val calculator = Calculator.getInstanceWithJob()
        runBlocking {
            logger.info("runBlocking thread : ${Thread.currentThread()}")
            withContext(Dispatchers.Default) { // 병렬 실행을 위해 main 스레드가 아닌 DefaultDispatcher 스레드풀로 변경한다.
                logger.info("withContext thread : ${Thread.currentThread()}")
                launch {
                    logger.info("launch coroutine 1")
                    calculator.calculateWithLaunch(true)
                }
                launch {
                    logger.info("launch coroutine 2")
                    calculator.calculateWithLaunch()
                }
                delay(1000)
                launch {
                    // calculator 내 scope은 취소되어 실행해도 동작하지 않는다.
                    logger.info("launch coroutine 3")
                    calculator.calculateWithLaunch()
                }
                delay(2000)
            }
        }
    }

    @Test
    fun `SupervisorJob을 가진 scope 하위에서는 예외가 전파되지 않고 scope을 재사용 할 수 있다`() {
        val calculator = Calculator.getInstanceWithSupervisorJob()
        runBlocking {
            logger.info("runBlocking thread : ${Thread.currentThread()}")
            withContext(Dispatchers.Default) { // 병렬 실행을 위해 main 스레드가 아닌 DefaultDispatcher 스레드풀로 변경한다.
                logger.info("withContext thread : ${Thread.currentThread()}")
                launch {
                    logger.info("launch coroutine 1")
                    calculator.calculateWithLaunch(true)
                }
                launch {
                    logger.info("launch coroutine 2")
                    calculator.calculateWithLaunch()
                }
                delay(1000)
                launch {
                    // calculator 내 scope은 취소되어 실행해도 동작하지 않는다.
                    logger.info("launch coroutine 3")
                    calculator.calculateWithLaunch()
                }
                delay(2000)
            }
        }
    }
}
