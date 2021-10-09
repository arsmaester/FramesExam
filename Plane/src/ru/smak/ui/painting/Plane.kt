package ru.smak.ui.painting

import kotlin.math.max

/**
 * Класс плоскости для осуществления преобразования координат
 * между различными системами
 */
class Plane(
    xMin: Double,
    xMax: Double,
    yMin: Double,
    yMax: Double
) {

    /**
     * Левая граница отрезка оси абсцисс,
     * отображаемого на плоскости
     */
    var xMin: Double = 0.0
        private set

    /**
     * Правая граница отрезка оси абсцисс,
     * отображаемого на плоскости
     */
    var xMax: Double = 0.0
        private set

    /**
     * Нижняя граница отрезка на оси ординат,
     * отображаемого на плоскости
     */
    var yMin: Double = 0.0
        private set

    /**
     * Верхняя граница отрезка оси ординат,
     * отображаемого на плоскости
     */
    var yMax: Double = 0.0
        private set

    /**
     * Свойство для изменения границ отрезка на оси абсцисс
     */
    var xSegment: Pair<Double, Double>
        get() = Pair(xMin, xMax)
        set(value) {
            val k = if (value.first == value.second) 0.1 else 0.0
            if (value.first <= value.second) {
                this.xMin = value.first - k
                this.xMax = value.second + k
            } else {
                this.xMax = value.first
                this.xMin = value.second
            }
        }

    /**
     * Свойство для изменения границ отрезка на оси ординат
     */
    var ySegment: Pair<Double, Double>
        get() = Pair(yMin, yMax)
        set(value) {
            val k = if (value.first == value.second) 0.1 else 0.0
            if (value.first <= value.second) {
                this.yMin = value.first - k
                this.yMax = value.second + k
            } else {
                this.yMax = value.first
                this.yMin = value.second
            }
        }

    /**
     * Ширина плоскости в пискселях (хранит количество пикселей)
     */
    private var xSize: Int = 1

    /**
     * Высота плоскости в пикселях (хранит количество пикселей)
     */
    private var ySize: Int = 1

    /**
     * Ширина плоскости (возвращает номер последнего видимого пикселя)
     */
    var width: Int
        get() = xSize - 1
        set(value) {
            xSize = max(1, value)
        }

    /**
     * Высота плоскости (возвращает номер последнего видимого пикселя)
     */
    var height: Int
        get() = ySize - 1
        set(value) {
            ySize = max(1, value)
        }

    init {
        xSegment = Pair(xMin, xMax)
        ySegment = Pair(yMin, yMax)
    }

    /**
     * Плотность пикселей по оси абсцисс в единичном отрезке
     * отбражаемой системы координат
     */
    val xDen: Double
        get() = width / (xMax - xMin)

    /**
     * Плотность пикселей по оси ординат в единичном отрезке
     * отбражаемой системы координат
     */
    val yDen: Double
        get() = height / (yMax - yMin)

    /**
     * Преобразование абсциссы из декартовой системы координат в экранную
     *
     * @param x абсцисса точки в декартовой системе координат
     * @return абсцисса точки в экранной системе координат
     */
    fun xCrt2Src(x: Double): Int {
        var r = (xDen * (x - xMin)).toInt()
        if (r < -width) r = -width
        if (r > 2 * width) r = 2 * width
        return r
    }

    /**
     * Преобразование ординаты из декартовой системы координат в экранную
     *
     * @param x ордината точки в декартовой системе координат
     * @return ордината точки в экранной системе координат
     */
    fun yCrt2Src(y: Double): Int {
        var r = (yDen * (yMax - y)).toInt()
        if (r < -height) r = -height
        if (r > 2 * height) r = 2 * height
        return r
    }

    /**
     * Преобразование абсциссы из экранной системы координат в декартовую
     *
     * @param x абсцисса точки в экранной системе координат
     * @return абсцисса точки в декартовой системе координат
     */
    fun xScr2Crt(x: Int): Double {
        var _x = x
        if (_x < -width) _x = -width
        if (_x > 2 * width) _x = 2 * width
        return _x / xDen + xMin
    }

    /**
     * Преобразование ординаты из экранной системы координат в декартовую
     *
     * @param x ордината точки в экранной системе координат
     * @return ордината точки в декартовой системе координат
     */
    fun yScr2Crt(y: Int): Double {
        var _y = y
        if (_y < -height) _y = -height
        if (_y > 2 * height) _y = 2 * height
        return yMax - _y / yDen
    }
}