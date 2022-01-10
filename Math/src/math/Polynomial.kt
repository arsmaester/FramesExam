package math

import java.lang.Exception
import kotlin.math.abs
import kotlin.math.max

open class Polynomial(coeff: Iterable<Double>) : (Double) -> Double {
    private var variableName: String = "x"
    private var _coeff: MutableList<Double> = mutableListOf()
    var coeff: List<Double>
        get() = _coeff.toMutableList()
        protected set(value) {
            _coeff.clear()
            _coeff.addAll(value)
        }

    private val degree: Int
        get() = _coeff.size - 1


    init {
        this._coeff.addAll(coeff)
    }

    constructor() : this(listOf(0.0))
    constructor(c: Array<Double>) : this(c.toList())
    constructor(vararg c: Double) : this(c.toList())


    override fun toString(): String =
        _coeff.indices.reversed().joinToString(separator = "") { i ->
            val mon = StringBuilder()
            if (_coeff[i] neq 0.0) { //bad check
                mon.append(
                    if (_coeff[i] < 0) " - " else
                        (if (i < _coeff.size - 1) " + " else "")
                )
                val acoeff = abs(_coeff[i])
                if (acoeff neq 1.0 || i == 0) //bad check
                    mon.append(acoeff)
                if (i > 0) {
                    mon.append(variableName)
                    if (i > 1) mon.append("^$i")
                }
            }
            if (mon.isEmpty()) mon.append(0)
            mon
        }

    private fun removeZeros() {
        var found = false
        _coeff.indices.reversed().forEach {
            if (_coeff[it] neq 0.0) found = true
            else if (!found) _coeff.removeAt(it)
        }
        if (_coeff.size == 0) _coeff.add(0.0)
    }

    override operator fun invoke(x: Double): Double {
        var p = 1.0
        return _coeff.reduce { res, d -> p *= x; res + d * p }
    }


    operator fun plus(other: Polynomial) = // сложение полиномов
        Polynomial(List(max(degree, other.degree) + 1) {
            (if (it < _coeff.size) _coeff[it] else 0.0) +
                    (if (it < other._coeff.size) other._coeff[it] else 0.0)
        })

    operator fun plusAssign(other: Polynomial) { // сложение полиномов с присвоением
        this._coeff = (this + other)._coeff
    }

    operator fun minus(other: Polynomial) = this + other * (-1.0) // вычитание полинома


    operator fun minusAssign(other: Polynomial) { // вычитание полинома с присвоением
        this._coeff = (this - other)._coeff
    }

    operator fun times(x: Double) = Polynomial(_coeff.map { it * x }) // умножение на число

    operator fun timesAssign(x: Double) { // умножение на число с присвоением
        _coeff.indices.forEach { _coeff[it] *= x }
        removeZeros()
    }

    operator fun times(other: Polynomial): Polynomial { // умножение на полином
        val resCoeff = DoubleArray(degree + other.degree + 1) { 0.0 }
        _coeff.forEachIndexed { tIndex, tCoeff ->
            other._coeff.forEachIndexed { oIndex, oCoeff ->
                resCoeff[tIndex + oIndex] += tCoeff * oCoeff
            }
        }
        return Polynomial(*resCoeff)
    }

    operator fun timesAssign(other: Polynomial) { // умножение на полином с присвоением
        _coeff = (this * other)._coeff
    }

    operator fun div(x: Double) = // деление на число
        if (x neq 0.0)
            this * (1.0 / x)
        else
            throw Exception("Zero division!")

    operator fun divAssign(x: Double) { // деление на число с присвоением
        if (x neq 0.0)
            _coeff.indices.forEach { _coeff[it] *= 1.0 / x }
        else
            throw Exception("Zero division!")
    }

    override fun equals(other: Any?): Boolean =
        (other != null) && (other is Polynomial) && (_coeff == other._coeff)

    fun derivative(): Polynomial {
        var tmp = DoubleArray(degree)
        if (degree == 0) {
            tmp = doubleArrayOf(0.0)
        } else {
            for (i in 0 until degree) {
                tmp[i] = (i + 1) * _coeff[i + 1]
            }
        }

        return Polynomial(*tmp)
    }

    override fun hashCode() = _coeff.hashCode()
}






