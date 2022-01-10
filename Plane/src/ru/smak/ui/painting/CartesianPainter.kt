package ru.smak.ui.painting

import java.awt.*
import kotlin.math.abs
import kotlin.math.roundToInt

class CartesianPainter(private val plane: CartesianPlane) : Painter {

    override fun paint(g: Graphics) {
        paintAxis(g)
        paintXTicks(g)
        paintYTicks(g)
    }

    private fun paintAxis(g: Graphics) {
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(2F)
                if (xMin > 0 || xMax < 0) {
                    drawLine(width, 0, width, height)
                    drawLine(0, 0, 0, height)
                } else drawLine(xCrt2Scr(0.0), 0, xCrt2Scr(0.0), height)
                if (yMin > 0 || yMax < 0) {
                    drawLine(0, 0, width, 0)
                    drawLine(0, height, width, height)
                } else drawLine(0, yCrt2Scr(0.0), width, yCrt2Scr(0.0))
            }
        }
    }


    private var tickHeight = 0.1
    private var signIndent = 10
    private var coeff = 10


    private fun paintXTicks(g: Graphics) {
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(2F)
                font = Font("Consolas", Font.BOLD, 12)

                if (abs(xMin) + abs(xMax) >= 20) coeff = 50

                if (abs(xMin) + abs(xMax) <= 3) {
                    coeff = 1
                    tickHeight = 0.05
                }

                var n = (xMin * 10)
                while (n < xMax * 10) {
                    val (tW, tH) = fontMetrics.getStringBounds((n / 10).toString(), g)
                        .run { Pair(width.toFloat(), height.toFloat()) }
                    if (n.round(1) % coeff == 0.0) {
                        if (n == 0.0) {
                            drawString(
                                (n / 10).round(1).toString(),
                                xCrt2Scr(n / 10) - tW / 2 + 2 * signIndent,
                                yCrt2Scr(0.0) + tH + signIndent
                            )
                        } else {
                            color = Color.RED
                            drawLine(
                                xCrt2Scr(n / 10),
                                yCrt2Scr(-tickHeight),
                                xCrt2Scr(n / 10),
                                yCrt2Scr(tickHeight)
                            )
                            color = Color.BLACK
                            drawString(
                                (n / 10).round(1).toString(),
                                xCrt2Scr(n / 10) - tW / 2,
                                yCrt2Scr(0.0) + tH + signIndent
                            )
                        }
                    } else if ((n.round(1) % 5 == 0.0) and (coeff != 1)) {
                        color = Color.BLUE
                        drawLine(
                            xCrt2Scr(n / 10),
                            yCrt2Scr(-tickHeight / 2),
                            xCrt2Scr(n / 10),
                            yCrt2Scr(tickHeight / 2)
                        )
                    } else if (coeff != 50) {
                        color = Color.BLACK
                        drawLine(
                            xCrt2Scr(n / 10),
                            yCrt2Scr(-tickHeight / 3),
                            xCrt2Scr(n / 10),
                            yCrt2Scr(tickHeight / 3)
                        )
                    }
                    n++
                }
            }
        }

    }

    private fun paintYTicks(g: Graphics) {
        with(plane) {
            (g as Graphics2D).apply {
                stroke = BasicStroke(1F)
                if (abs(yMin) + abs(yMax) >= 15) coeff = 50
                if (abs(yMin) + abs(yMax) <= 2) {
                    coeff = 1
                    tickHeight = 0.01
                }
                var n = (yMin * 10)
                font = Font("Consolas", Font.BOLD, 12)

                while (n < yMax * 10) {
                    val (tW, tH) = fontMetrics.getStringBounds((n / 10).toString(), g)
                        .run { Pair(width.toFloat(), height.toFloat()) }
                    if ((n % coeff == 0.0) and (n != 0.0)) {
                        color = Color.RED
                        drawLine(
                            xCrt2Scr(-tickHeight),
                            yCrt2Scr(n / 10),
                            xCrt2Scr(tickHeight),
                            yCrt2Scr(n / 10)
                        )
                        color = Color.BLACK
                        drawString(
                            (n / 10).round(1).toString(),
                            xCrt2Scr(0.0) + tH + signIndent,
                            yCrt2Scr(n / 10) + tH / 4
                        )
                    } else if ((n.round(1) % 5 == 0.0) and (coeff != 1)) {
                        color = Color.BLUE
                        drawLine(
                            xCrt2Scr(-tickHeight / 2),
                            yCrt2Scr(n / 10),
                            xCrt2Scr(tickHeight / 2),
                            yCrt2Scr(n / 10)
                        )
                    } else if (coeff != 50) {
                        color = Color.BLACK
                        drawLine(
                            xCrt2Scr(-tickHeight / 4),
                            yCrt2Scr(n / 10),
                            xCrt2Scr(tickHeight / 4),
                            yCrt2Scr(n / 10)
                        )
                    }
                    n += 1
                }
            }
        }
    }

    private fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

}

