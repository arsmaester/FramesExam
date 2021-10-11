package ru.smak.ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class GraphicsPanel : JPanel() {

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let {
            it.color = Color.RED
            it.fillRect(40, 50, 200, 200)
            it.color = Color.BLUE
            it.fillOval(250, 50, 200, 200)
        }
    }
}