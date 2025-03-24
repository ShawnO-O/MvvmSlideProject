package com.shawn.mvvmslideproject

import com.shawn.mvvmslideproject.util.MyMath
import org.junit.Assert
import org.junit.Test

class TDDTest {
    @Test
    fun addTest() {
        val expected = 3
        val actual = MyMath().add(1, 2)
        Assert.assertEquals(expected, actual)
    }
}