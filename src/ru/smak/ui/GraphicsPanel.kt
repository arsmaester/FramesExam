package ru.smak.ui

import ru.smak.ui.painting.CartesianPainter
import ru.smak.ui.painting.Painter
import java.awt.Graphics
import javax.swing.JPanel

class GraphicsPanel(private val painters: MutableList<Painter>) : JPanel() {
    override fun paint(g: Graphics?) {
        super.paint(g)
        painters.forEach {
            if (g != null) {
                it.paint(g)
            }
        }
    }
}