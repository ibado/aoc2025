val orthogonalDirs = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))
val diagonals = listOf(Point(-1, -1), Point(-1, 1), Point(1, -1), Point(1, 1))
val allDirs = orthogonalDirs + diagonals

data class Point(val x: Int, val y: Int) {
    infix operator fun plus(other: Point): Point = Point(this.x + other.x, this.y + other.y)
    infix operator fun minus(other: Point): Point = Point(this.x - other.x, this.y - other.y)
    fun isInRange(xMax: Int, yMax: Int): Boolean =
        this.x in (0..<xMax) && (this.y in 0..<yMax)

    fun rotate90Right(): Point = Point(this.y, -this.x)
    fun rotate90Left(): Point = Point(-this.y, this.x)
}

typealias Grid<T> = List<List<T>>

fun Grid<*>.rows() = size
fun Grid<*>.cols() = first().size

fun List<String>.asCharGrid() = map { it.toList() }

fun <T> generateGrid(rows: Int, cols: Int, generator: (i: Int, j: Int) -> T): Grid<T> =
    List(rows) { i ->
        List(cols) { j ->
            generator(i, j)
        }
    }

fun <T, U> Grid<T>.fold(init: U, f: (acc: U, i: Int, j: Int) -> U): U {
    var acc = init
    for (i in indices) {
        for (j in first().indices) {
            acc = f(acc, i, j)
        }
    }
    return acc
}
