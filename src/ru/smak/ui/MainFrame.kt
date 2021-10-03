package ru.smak.ui

import java.awt.Color
import java.awt.Dimension
import javax.swing.*

class MainFrame : JFrame(){

    val mainPanel: JPanel
    val controlPanel: JPanel

    val xMin: JSpinner
    val xMinM: SpinnerNumberModel

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(600, 400)

        mainPanel = JPanel().apply {
            background = Color.WHITE
        }

        controlPanel = JPanel().apply{

        }
        layout = GroupLayout(contentPane).apply{
            setHorizontalGroup(createSequentialGroup()
                .addGap(4)
                .addGroup(
                    createParallelGroup()
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                        .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                )
                .addGap(4)
            )

            setVerticalGroup(createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
                .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
            )
        }

        xMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        xMin = JSpinner(xMinM)

        controlPanel.layout = GroupLayout(controlPanel).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(xMin, 100, 100, GroupLayout.PREFERRED_SIZE)
                    .addGap(4)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(xMin, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(4)
            )
        }
    }
}