package com.gigi.studykotlincoroutine.chapter6

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


class CoroutineBuilderTest {

    @Test
    fun test() {
        runBlocking {
            // 같은 coroutineScope 내에서는 자식 코루틴이 바로 실행되지 않고 부모 코루틴이 끝나거나 yield를 만나기 전까지 대기한다.
            println("Hello1")
            launch {
                println("first launch start")
                delay(1L)
                println("first launch end")
            }
            println("Hello2")
            launch {
                println("second launch start")
                delay(1L)
                println("second launch end")
            }

            launch {
                println("third launch start")
                delay(1L)
                println("third launch end")
            }
            println("Hello3")
        }
    }
}
