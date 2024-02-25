package com.gigi.studykotlincoroutine.chapter13

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class CalculatorTest {

    @Test
    fun test() {
        runBlocking {
            val calculator = Calculator.getInstance()
            launch {
                println(1)
                delay(100)
                println(calculator.calculate(1000))
            }
            launch  {
                println(2)
//                delay(100)
                println(calculator.calculate(3524))
            }

            launch  {
                println(3)
//                delay(100)
                println(calculator.calculate(36352))
            }

            launch {
                println(4)
                delay(100)
                println(calculator.calculate(432))
            }
            delay(3000)
        }
    }
}
