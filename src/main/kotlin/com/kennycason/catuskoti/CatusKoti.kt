package com.kennycason.catuskoti

import com.kennycason.cellular.automata.array2d
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

/**
 * Created by kenny on 9/1/17.
 */

fun main(args: Array<String>) {
    CatusKoti().run()
}

class CatusKoti {
    val random = Random()
    val screenWidth = 1000
    val screenHeight = 800
    val cellDim = 5
    val width = screenWidth / cellDim
    val height = screenHeight / cellDim
    val saveImage = false

    var canvas: BufferedImage = BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB)
    var graphics = canvas.createGraphics()

    val T = QuadBit(true, true)
    val F = QuadBit(false, false)
    var t1 = array2d(width, height) { QuadBit(random.nextBoolean(), random.nextBoolean()) }
    var t2 = array2d(width, height) { QuadBit(random.nextBoolean(), random.nextBoolean()) }

    fun run() {
        val frame = JFrame()
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        frame.setSize(screenWidth, screenHeight)
        frame.setVisible(true)

        var i = 0
        val panel = object: JPanel() {
            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)
                step()
             //   countSingleSquares()
                draw()
                g.drawImage(canvas, 0, 0, screenWidth, screenHeight, this)

                if (saveImage) {
                    ImageIO.write(canvas, "png", File("/tmp/t1.png"))
                }
                i++
            }
        }
        frame.add(panel)
        panel.revalidate()

        while (true) {
            panel.repaint()
            Thread.sleep(100)
        }
    }

    private fun step() {
        (0 until width).forEach { x ->
            (0 until height).forEach { y ->
                pattern1(x, y)
               // pattern2(x, y)
                //pattern3(x, y)
                //pattern4(x, y)
            }
        }
        val tmp = t1
        t1 = t2
        t2 = tmp
    }

    private fun pattern1(x: Int, y: Int) {
        var on = if (get(x - 1, y) == T) { 1 } else { 0 } + if (get(x + 1, y) == T) { 1 } else { 0
        } + if (get(x, y - 1) == T) { 1 } else { 0 } + if (get(x, y + 1) == T) { 1 } else { 0
        } + if (get(x - 1, y - 1) == T) { 1 } else { 0 } + if (get(x + 1, y - 1) == T) { 1 } else { 0
        } + if (get(x -1, y + 1) == T) { 1 } else { 0 } + if (get(x + 1, y + 1) == T) { 1 } else { 0 }

        if (on == 8) {
            on = 0
        } else if (on == 0) {
            on = 8
        }

        t2[x][y] = if (on > 4) { T } else { t1[x][y] }
    }

    private fun pattern2(x: Int, y: Int) {
        var on = if (get(x - 1, y) == TRUE) { 1 } else { 0 } + if (get(x + 1, y) == TRUE) { 1 } else { 0
        } + if (get(x, y - 1) == TRUE) { 1 } else { 0 } + if (get(x, y + 1) == TRUE) { 1 } else { 0
        } + if (get(x - 1, y - 1) == TRUE) { 1 } else { 0 } + if (get(x + 1, y - 1) == TRUE) { 1 } else { 0
        } + if (get(x -1, y + 1) == TRUE) { 1 } else { 0 } + if (get(x + 1, y + 1) == TRUE) { 1 } else { 0 }

        if (on == 8) {
            on = 0
        } else if (on == 0) {
            on = 8
        }

        t2[x][y] = if (on >= 5) { TRUE } else { t1[x][y] }
    }

    private fun pattern3(x: Int, y: Int) {
        var on = if (get(x - 1, y) == TRUE) { 1 } else { 0 } + if (get(x + 1, y) == TRUE) { 1 } else { 0
        } + if (get(x, y - 1) == TRUE) { 1 } else { 0 } + if (get(x, y + 1) == TRUE) { 1 } else { 0 }

        if (on == 4) {
            on = 0
        } else if (on == 0) {
            on = 4
        }

        t2[x][y] = if (on > 3) { TRUE } else { t1[x][y] }
    }

    private fun pattern4(x: Int, y: Int) {
        t2[x][y] =
                xor(get(x - 1, y),
                        xor(get(x + 1, y),
                                xor(get(x, y - 1), get(x, y + 1))))
    }

    private fun isSingleSquare(x: Int, y: Int) : Boolean {
        return t1[x][y] == T &&
                get(x - 1, y) == F &&
                get(x + 1, y) == F &&
                get(x, y - 1) == F &&
                get(x, y + 1) == F &&
                get(x - 1, y - 1) == F &&
                get(x + 1, y - 1) == F &&
                get(x - 1, y + 1) == F &&
                get(x + 1, y + 1) == F
        }

    private fun get(x: Int, y: Int) = t1[(x + width) % width][(y + height) % height]

    private fun draw() {
        (0 until width).forEach { x ->
            (0 until height).forEach { y ->
               //  val color = if (isSingleSquare(x, y)) { Color.WHITE } else { Color.BLACK }
                val color = when (get(x, y)) {
                    FALSE -> Color.BLACK
                    TRUE -> Color.WHITE
                    BOTH -> Color.BLUE
                    NEITHER -> Color.RED
                    else -> throw IllegalStateException("Invalid state: " + get(x, y))
                }
                if (cellDim == 1) {
                    canvas.setRGB(x * cellDim, y * cellDim, color.rgb)
                } else {
                    drawRectangle(x * cellDim, y * cellDim, color.rgb)
                }
            }
        }
    }

    private fun drawRectangle(startX: Int, startY: Int, rgb: Int) {
        (0 until cellDim).forEach { x ->
            (0 until cellDim).forEach { y ->
                canvas.setRGB(startX + x, startY + y, rgb)
            }
        }
    }

    // not used, more for representational understanding
    companion object States {
        val NEITHER = QuadBit(false, true)
        val BOTH = QuadBit(true, false)
        val TRUE = QuadBit(true, true)
        val FALSE = QuadBit(false, false)
    }

    private fun or(a: QuadBit, b: QuadBit) : QuadBit {
        return QuadBit(a.left or b.left, a.right or b.right)
    }

    private fun and(a: QuadBit, b: QuadBit) : QuadBit {
        return QuadBit(a.left and b.left, a.right and b.right)
    }

    private fun xor(a: QuadBit, b: QuadBit) : QuadBit {
        return QuadBit(a.left xor b.left, a.right xor b.right)
    }

    private fun not(a: QuadBit) : QuadBit {
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

}
