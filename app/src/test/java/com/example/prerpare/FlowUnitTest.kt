package com.example.prerpare

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FlowUnitTest {
    @Test
    fun testFlow() {

        val flow1 = flow {
            emit(1)
            emit(12)
            emit(13)
            throw RuntimeException("s")
        }
        runBlocking {
            flow1.map {
                it * 2
            }.catch { e ->
                emit(666)
            }.collect {
                println(it)
            }
        }
    }
}