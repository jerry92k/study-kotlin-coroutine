package com.gigi.studykotlincoroutine.chapter3

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContinuationTest {

    var continuation: Continuation<Unit>? = null

    @BeforeEach
    fun setUp() {
        continuation = null
    }

    @Test
    fun `다른 스레드나 다른 코루틴으로 재개하지 않으면 프로그램은 실행된 상태로 유지된다`() {
        runBlocking {
            println("before")

            suspendAndSetContinuation()
            println("함수가 아닌 코루틴을 중단시킨 것이므로, 코루틴이 중단되어 있어서 여기가 출력될 수 없다.")
            continuation?.resume(Unit)
            println("After")
        }
    }

    @Test
    fun `After까지 출력되지만 메모리 누수가 발생할 수 있으므로 이렇게 구현하면 안된다`() {
        runBlocking {
            println("before")

            launch {
                delay(1000)
                continuation?.resume(Unit)
            }

            suspendAndSetContinuation()
            println("After")
        }
    }


    suspend fun suspendAndSetContinuation() {
        suspendCoroutine<Unit> { cont -> continuation = cont }
    }
}
