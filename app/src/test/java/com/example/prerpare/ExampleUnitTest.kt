package com.example.prerpare

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun first_day_action(){
        val a=1
        var b=2
        var c:String?="s"
        c?.length
        c=c?:"sdf"
        c="asd,$c"
        println(c)
        val result=if (c==c){
            "yes"
        }else{
            "false"
        }
        println(result)
        when(result){
            "yes"->println("成")
        }
        println(add(1,2))
        println(add(1))
        val numbers=listOf(1,2,3,4,5)
        val even=numbers.filter { it%2==1 }.map { it*2 }
        println(even)
    }

    fun add(a:Int,b:Int=100)=a+b
}