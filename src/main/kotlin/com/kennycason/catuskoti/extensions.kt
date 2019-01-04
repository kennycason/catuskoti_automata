package com.kennycason.cellular.automata

/**
 * Created by kenny on 11/23/16.
 */
inline fun <reified INNER> array2d(
        x: Int, y: Int,
        noinline init: (Int)->INNER): Array<Array<INNER>> =
        Array(x) {
            Array<INNER>(y, init) }

inline fun <reified INNER> array3d(
        x: Int, y: Int, z: Int,
        noinline init: (Int)->INNER): Array<Array<Array<INNER>>> =
        Array(x) {
            Array(y) {
                Array<INNER>(z, init) }}
