package com.kennycason.catuskoti.catuskoti.math

import com.kennycason.catuskoti.catuskoti.QuadBit

class And : QuadBitFunction {
    override fun apply(a: QuadBit, b: QuadBit): QuadBit {
        return QuadBit(a.left and b.left, a.right and b.right)
    }
}