package ru.smak.ui


import ru.smak.ui.painting.*
import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import java.awt.event.*
import java.util.concurrent.Callable
import javax.swing.*
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


class MainFrame : JFrame() {
    private val controlPanel: JPanel
    private val minDim = Dimension(600, 400)
    private val mainPanel: GraphicsPanel
    private val XMin: JLabel
    private val XMax: JLabel
    private val YMin: JLabel
    private val YMax: JLabel
    private val set2: JLabel
    private val set3: JLabel
    private val xMin: JSpinner
    private val xMax: JSpinner
    private val yMin: JSpinner
    private val yMax: JSpinner
    private val tf: JTextField
    private val cb2: JCheckBox
    private val cb3: JCheckBox
    private val cjp2: JPanel
    private val cjp3: JPanel
    private val xMinM: SpinnerNumberModel
    private val xMaxM: SpinnerNumberModel
    private val yMinM: SpinnerNumberModel
    private val yMaxM: SpinnerNumberModel
    private val tMinLbl: JLabel
    private val tMaxLbl: JLabel
    private val tMinInput: JTextField
    private val tMaxInput: JTextField

    init {
        minimumSize = minDim
        defaultCloseOperation = EXIT_ON_CLOSE
        tMinLbl = JLabel("Минимальное t: ")
        tMaxLbl = JLabel("Максимальное t: ")
        tMinInput = JTextField("-10")
        tMaxInput = JTextField("10")
        cb2 = JCheckBox()
        cb3 = JCheckBox()
        cjp2 = JPanel()
        cjp2.background = Color.BLACK
        cjp2.setSize(1, 1)
        cjp3 = JPanel()
        cjp3.background = Color.BLUE
        cjp2.setSize(1, 1)
        tf = JTextField()

        controlPanel = JPanel()
        controlPanel.background = Color.WHITE

        xMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        xMin = JSpinner(xMinM)
        xMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        xMax = JSpinner(xMaxM)
        yMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        yMin = JSpinner(yMinM)
        yMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        yMax = JSpinner(yMaxM)

        val plane = CartesianPlane(
            xMin.value as Double, xMax.value as Double,
            yMin.value as Double, yMax.value as Double,
        )

        val cartesianPainter = CartesianPainter(plane)
        val functionPainter = FunctionPainter(plane)
        val paramFunctionPainter = ParamFunctionPainter(plane)


        val f = { x: Double ->
            x * x + 2.0.pow(x)
        }

        val pfX = { t: Double ->
            cos(t)
        }

        val pfY = { t: Double ->
            t + sin(t)
        }


        functionPainter.function = f
        functionPainter.funColor = Color.BLACK
        paramFunctionPainter.x = pfX
        paramFunctionPainter.y = pfY
        paramFunctionPainter.funColor = Color.BLUE

        val painters = mutableListOf(cartesianPainter, functionPainter, paramFunctionPainter)

        mainPanel = GraphicsPanel(painters).apply {
            background = Color.WHITE
        }

        mainPanel.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                plane.width = mainPanel.width
                plane.height = mainPanel.height
                mainPanel.repaint()
            }
        })

        tMinInput.addActionListener() {
            paramFunctionPainter.tMin = tMinInput.text.toDouble()
            mainPanel.repaint()
        }

        tMaxInput.addActionListener() {
            paramFunctionPainter.tMax = tMaxInput.text.toDouble()
            mainPanel.repaint()
        }

//        mainPanel.addMouseListener(object : MouseAdapter() {
//            override fun mouseClicked(e: MouseEvent?) {
//                with(plane) {
//                    if (e != null) {
//                        pointsPainter.points.add(Pair(xScr2Crt(e.x), yScr2Crt(e.y)))
//                        addPoint(xScr2Crt(e.x) to yScr2Crt(e.y))
//                        functionPainter.function = plane.p
//                        derivativePainter.function = plane.p.derivative()
////                        painters.add(pointsPainter)
//                        mainPanel.repaint()
//                    }
//                }
//            }
//        })

        cb2.isSelected = true
        cb3.isSelected = true


        cb2.addItemListener { e ->
            if (!cb2.isSelected) {
                painters.remove(functionPainter)
                mainPanel.repaint()
            } else {
                painters.add(functionPainter)
                mainPanel.repaint()
            }
        }

        cb3.addItemListener { e ->
            if (!cb3.isSelected) {
                painters.remove(paramFunctionPainter)
                mainPanel.repaint()
            } else {
                painters.add(paramFunctionPainter)
                mainPanel.repaint()
            }
        }

//        cjp1.addMouseListener(object : MouseAdapter() {
//            override fun mouseClicked(e: MouseEvent?) {
//                if (e != null) {
//                    val color = JColorChooser.showDialog(null, "Выберите цвет точек", cjp1.background)
//                    cjp1.background = color
//                    pointsPainter.pointsColor = color
//                    mainPanel.repaint()
//                }
//            }
//        })

        cjp2.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e != null) {
                    val color = JColorChooser.showDialog(null, "Выберите цвет явной функции", cjp2.background)
                    cjp2.background = color
                    functionPainter.funColor = color
                    mainPanel.repaint()
                }
            }
        })

        cjp3.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e != null) {
                    val color = JColorChooser.showDialog(null, "Выберите цвет параметрической функции", cjp3.background)
                    cjp3.background = color
                    paramFunctionPainter.funColor = color
                    mainPanel.repaint()
                }
            }
        })


        xMin.addChangeListener {
            xMaxM.minimum = xMin.value as Double + 0.1
            plane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        xMax.addChangeListener {
            xMinM.maximum = xMax.value as Double - 0.1
            plane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        yMin.addChangeListener {
            yMaxM.minimum = yMin.value as Double + 0.1
            plane.ySegment = Pair(yMin.value as Double, yMax.value as Double)
            mainPanel.repaint()
        }
        yMax.addChangeListener {
            yMinM.maximum = yMax.value as Double - 0.1
            plane.ySegment = Pair(yMin.value as Double, yMax.value as Double)
            mainPanel.repaint()
        }

        XMin = JLabel("XMin:")
        XMax = JLabel("XMax:")
        YMin = JLabel("YMin:")
        YMax = JLabel("YMax:")
        set2 = JLabel("Отображать график явной функции")
        set3 = JLabel("Отображать график параметрической функции")



        controlPanel.layout = GroupLayout(controlPanel).apply {
            linkSize(XMin, xMin)
            linkSize(XMax, xMax)
            linkSize(YMin, yMin)
            linkSize(YMax, yMax)
            linkSize(cjp2, cjp3, cb2, cb3)
            linkSize(tMinLbl, tMaxLbl)
            linkSize(tMinInput, tMaxInput)

            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(10)
                    .addGroup(createParallelGroup().addComponent(XMin).addComponent(YMin))
                    .addGroup(
                        createParallelGroup().addComponent(
                            xMin,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        ).addComponent(
                            yMin,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addGap(10, 20, 20)
                    .addGroup(createParallelGroup().addComponent(XMax).addComponent(YMax))
                    .addGroup(
                        createParallelGroup().addComponent(
                            xMax,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        ).addComponent(
                            yMax,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addGap(50)
                    .addGroup(createParallelGroup().addComponent(cb2).addComponent(cb3))
                    .addGroup(createParallelGroup().addComponent(set2).addComponent(set3))
                    .addGap(10)
                    .addGroup(createParallelGroup().addComponent(cjp2).addComponent(cjp3))
                    .addGap(10)
                    .addGroup(createParallelGroup().addComponent(tMinLbl).addComponent(tMaxLbl))
                    .addGap(10)
                    .addGroup(createParallelGroup()
                        .addComponent(tMinInput, 50, 50, 50)
                        .addComponent(tMaxInput, 50, 50, 50))
            )
            setVerticalGroup(
                createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(
                    createSequentialGroup().addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(XMin)
                            .addComponent(
                                xMin,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(XMax)
                            .addComponent(
                                xMax,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                    )
                        .addGap(2)
                        .addGroup(
                            createParallelGroup()
                                .addComponent(YMin)
                                .addComponent(
                                    yMin,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(YMax)
                                .addComponent(
                                    yMax,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                        )
                ).addGroup(
                    createSequentialGroup()
                        .addGroup(
                            createParallelGroup(GroupLayout.Alignment.CENTER)
//                                .addComponent(cb1)
//                                .addComponent(set1)
//                                .addComponent(cjp1)
                        )
                        .addGap(2)
                        .addGroup(
                            createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(cb2)
                                .addComponent(set2)
                                .addComponent(cjp2)
                                .addComponent(tMinLbl)
                                .addComponent(tMinInput)
                        )
                        .addGap(2)
                        .addGroup(
                            createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(cb3)
                                .addComponent(set3)
                                .addComponent(cjp3)
                                .addComponent(tMaxLbl)
                                .addComponent(tMaxInput)
                        )
                )
            )
        }
        layout = GroupLayout(contentPane).apply {
            autoCreateGaps = true;
            autoCreateContainerGaps = true;
            setVerticalGroup(
                createSequentialGroup()
                    .addComponent(mainPanel)
                    .addComponent(controlPanel)
            )
            setHorizontalGroup(
                createParallelGroup()
                    .addComponent(mainPanel)
                    .addComponent(controlPanel)
            )
        }
        pack()
        plane.width = mainPanel.width
        plane.height = mainPanel.height
    }
}