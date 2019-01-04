package com.kennycason.catuskoti.catuskoti.math

import com.kennycason.catuskoti.catuskoti.QuadBit

class Or : QuadBitFunction {
    override fun apply(a: QuadBit, b: QuadBit): QuadBit {
        return QuadBit(a.left or b.left, a.right or b.right)
    }
}