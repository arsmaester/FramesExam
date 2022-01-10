package ru.smak.ui.painting

import java.awt.*

class ParamFunctionPainter(private val plane: CartesianPlane) : Painter {

    lateinit var x: (Double) -> Double
    lateinit var y: (Double) -> Double
    var tMin: Double = -10.0
    var tMax: Double = 10.0
    var funColor: Color = Color.BLACK

    override fun paint(g: Graphics) {
        with(g as Graphics2D) {
            stroke = BasicStroke(3F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            val rh = mapOf(
                RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
            )
            setRenderingHints(rh)
            with(plane) {
                color = funColor
                var i = tMin
                while (i < tMax) {
                    drawLine(
                        xCrt2Scr(x(i)),
                        yCrt2Scr(y(i)),
                        xCrt2Scr(x((i + 0.1))),
                        yCrt2Scr(y((i + 0.1)))
                    )
                    i += 0.1
                }
            }
        }
    }
}