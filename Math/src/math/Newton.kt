package math

class Newton(private val points: MutableMap<Double, Double>) : Polynomial(), (Double) -> Double {
    init {
        val p = Polynomial()
        for (i in points.keys.indices) {
            p += omega(i) * divDiffN(i)
        }
        coeff = p.coeff
    }

    private var cachedOmega = Polynomial(1.0)

    override operator fun invoke(x: Double): Double {
        return super.invoke(x)
    }

    fun addPoint(point: Pair<Double, Double>) {
        points += point
        val deg = points.size - 1
        this += cachedOmega * divDiffN(deg)
        cachedOmega = cachedOmega * Polynomial(-point.first, 1.0)
    }

    private fun omega(n: Int): Polynomial {
        val p = Polynomial(1.0)
        points.keys.take(n).forEach {
            p *= Polynomial(-it, 1.0)
        }
        cachedOmega = p
        return p
    }


    private fun divDiffN(n: Int): Double {
        var sum = 0.0

        points.keys.take(n + 1).forEach { xj ->
            var prod = 1.0
            points.keys.take(n + 1).forEach { xi ->
                if (xi != xj) prod *= (xj - xi)
            }
            sum += points[xj]!! / prod
        }
        return sum
    }
}