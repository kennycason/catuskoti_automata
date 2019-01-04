package com.kennycason.catuskoti.catuskoti.math

import com.kennycason.catuskoti.catuskoti.QuadBit

class Xor : QuadBitFunction {
    override fun apply(a: QuadBit, b: QuadBit): QuadBit {
        return QuadBit(a.left xor b.left, a.right xor b.right)
    }
}