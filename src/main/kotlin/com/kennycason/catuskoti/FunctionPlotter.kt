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
    FunctionPlotter().run()
}

class FunctionPlotter {
    val random = Random()
    val screenWidth = 2000
    val screenHeight = 1600
    val cellDim = 1
    val width = screenWidth / cellDim
    val height = screenHeight / cellDim
    val saveImage = false

    var canvas: BufferedImage = BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB)
    var graphics = canvas.createGraphics()

    var t1 = array2d(width, height, { random.nextBoolean() })
    var t2 = array2d(width, height, { random.nextBoolean() })

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
         //  Thread.sleep(100)
        }
    }

    private fun step() {
        (0.. width - 1).forEach { x ->
            (0.. height - 1).forEach { y ->
                var on = if (get(x - 1, y)) { 1 } else { 0 } + if (get(x + 1, y)) { 1 } else { 0
                } + if (get(x, y - 1)) { 1 } else { 0 } + if (get(x, y + 1)) { 1 } else { 0
                } + if (get(x - 1, y - 1)) { 1 } else { 0 } + if (get(x + 1, y - 1)) { 1 } else { 0
                } + if (get(x -1, y + 1)) { 1 } else { 0 } + if (get(x + 1, y + 1)) { 1 } else { 0 }
                //t2[x][y] = get(x - 1, y) xor get(x, y - 1)
                //println(on)
                if (on == 8) {
                    on = 0
                } else if (on == 0) {
                    on = 8
                }

                t2[x][y] = on > 4

//                t2[x][y] = get(x - 1, y) xor get(x + 1, y) or get(x, y - 1) xor get(x, y + 1) or get(x, y)
//                if (random.nextInt(1000) == 0) {
//                    t2[x][y] = !t2[x][y]
//                }

            }
        }
        val tmp = t1
        t1 = t2
        t2 = tmp
    }

    private fun isSingleSquare(x: Int, y: Int) : Boolean {
        return t1[x][y] &&
                !get(x - 1, y) &&
                !get(x + 1, y) &&
                !get(x, y - 1) &&
                !get(x, y + 1) &&
                !get(x - 1, y - 1) &&
                !get(x + 1, y - 1) &&
                !get(x - 1, y + 1) &&
                !get(x + 1, y + 1)
        }

    private fun countSingleSquares() {
        var count = 0
        (0.. width - 1).forEach { x ->
            (0.. height - 1).forEach { y ->
                if (t1[x][y]) {
                    var on = if (get(x - 1, y)) {
                        1
                    } else {
                        0
                    } + if (get(x + 1, y)) {
                        1
                    } else {
                        0
                    } + if (get(x, y - 1)) {
                        1
                    } else {
                        0
                    } + if (get(x, y + 1)) {
                        1
                    } else {
                        0
                    } + if (get(x - 1, y - 1)) {
                        1
                    } else {
                        0
                    } + if (get(x + 1, y - 1)) {
                        1
                    } else {
                        0
                    } + if (get(x - 1, y + 1)) {
                        1
                    } else {
                        0
                    } + if (get(x + 1, y + 1)) {
                        1
                    } else {
                        0
                    }

                    if (on == 0) {
                        count++
                    }
                }

            }
        }
        println(count)
    }

    private fun get(x: Int, y: Int) = t1[(x + width) % width][(y + height) % height]

    private fun draw() {
        (0.. width - 1).forEach { x ->
            (0.. height - 1).forEach { y ->
                val color = if (isSingleSquare(x, y)) { Color.WHITE } else { Color.BLACK }
                if (cellDim == 1) {
                    canvas.setRGB(x * cellDim, y * cellDim, color.rgb)
                } else {
                    drawRectangle(x * cellDim, y * cellDim, color.rgb)
                }
            }
        }
    }

    private fun drawRectangle(startX: Int, startY: Int, rgb: Int) {
        (0.. cellDim - 1).forEach { x ->
            (0.. cellDim - 1).forEach { y ->
                canvas.setRGB(startX + x, startY + y, rgb)
            }
        }
    }

}
