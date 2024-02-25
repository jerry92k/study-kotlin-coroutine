package com.gigi.studykotlincoroutine.chapter13

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory

class Calculator private constructor(
    private val scope: CoroutineScope
) {

    companion object {

        private val logger = LoggerFactory.getLogger(this.javaClass)

        fun getInstance(): Calculator {
            val coroutineScope = CoroutineScope(Dispatchers.Default
                        + SupervisorJob()
                        + CoroutineExceptionHandler { it, throwable ->
                    logger.error("[EXCEPTION OCCOURED][${it.job}]", throwable)
                })
            return Calculator(coroutineScope)
        }
    }


    suspend fun calculate(endValue: Int): Int {
        val half = endValue/2
//        delay(10)
        val subSum1 = scope.async {
            delay(10)
            (1..half).sum()
        }

        val subSum2 = scope.async {
            delay(10)
            (half+1 .. endValue).sum()
        }

       return subSum1.await() + subSum2.await()
    }
}
