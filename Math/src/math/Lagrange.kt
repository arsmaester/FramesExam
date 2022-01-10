package math

class Lagrange(private val points: Map<Double, Double>) : Polynomial() {
    init {
        val p = Polynomial()
        points.keys.forEach {
            p += fundamental(it) * points[it]!! // dangerous
        }
        coeff = p.coeff
    }

    private fun fundamental(x: Double) = Polynomial(1.0).apply {
        points.keys.forEach {
            if (it != x) this *= Polynomial(-it, 1.0) / (x - it)
        }
    }
}