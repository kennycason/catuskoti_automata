package com.kennycason.catuskoti.catuskoti;

val NEITHER = QuadBit(false, true)
val BOTH = QuadBit(true, false)
val TRUE = QuadBit(true, true)
val FALSE = QuadBit(false, false)


fun or(a: QuadBit, b: QuadBit) : QuadBit {
    return QuadBit(a.left or b.left, a.right or b.right)
}

fun and(a: QuadBit, b: QuadBit) : QuadBit {
    return QuadBit(a.left and b.left, a.right and b.right)
}

fun xor(a: QuadBit, b: QuadBit) : QuadBit {
    return QuadBit(a.left xor b.left, a.right xor b.right)
}

fun not(a: QuadBit) : QuadBit {
    return QuadBit(!a.left, !a.right)
}

data class QuadBit(val left: Boolean, val right: Boolean) {
        override fun toString(): String {
            return when (this) {
                FALSE -> "F"
                TRUE -> "T"
                BOTH -> "TF"
                NEITHER -> "Ø"
                else -> throw IllegalStateException("Invalid state: " + this)
            }
        }
    }