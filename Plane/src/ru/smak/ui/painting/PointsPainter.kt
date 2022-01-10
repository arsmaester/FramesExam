package ru.smak.ui.painting

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D

class PointsPainter(private val plane: CartesianPlane) : Painter {

    var points: MutableList<Pair<Double, Double>> = mutableListOf()

    var pointsColor: Color = Color.RED

    override fun paint(g: Graphics) {
        with(g as Graphics2D) {
            with(plane) {
                color = pointsColor
                points.forEach {
                    fillOval(
                        xCrt2Scr(it.first) - 5,
                        yCrt2Scr(it.second) - 5,
                        10, 10
                    )
                }
            }
        }
    }
}