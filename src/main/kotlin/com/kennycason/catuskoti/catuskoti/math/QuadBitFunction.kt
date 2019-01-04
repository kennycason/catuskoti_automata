package com.kennycason.catuskoti.catuskoti.math

import com.kennycason.catuskoti.catuskoti.QuadBit


interface QuadBitFunction {
    fun apply(a: QuadBit, b: QuadBit) : QuadBit
}