package ru.smak.ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class GraphicsPanel : JPanel(){

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let{
            it.color = Color.GREEN
            it.fillRect(20, 40, 200, 300)
            it.color = Color.BLUE
            it.fillOval(250, 40, 200, 200)
        }
    }

}