#!/usr/bin/env kotlin
@file:Import("grid.main.kts")

import kotlin.io.path.Path
import kotlin.io.path.useLines

val down = Point(1, 0)
val left = Point(0, -1)
val right = Point(0, 1)

fun part1(lines: List<String>): Int {
    val start = Point(0, lines.first().indexOfFirst { it == 'S' })
    val grid = lines.asCharGrid()
    val seen = Array(grid.rows()) { BooleanArray(grid.cols()) }
    fun countStuff(point: Point, count: Int): Int {
        val next = point + down
        if (!next.isInRange(grid.rows(), grid.cols()) || seen[next.x][next.y]) return count
        return when (grid[next.x][next.y]) {
            '^' -> {
                val inc = if (seen[next.x][next.y]) 0 else {
                    seen[next.x][next.y] = true
                    1
                }
                inc + countStuff(next + left, count) + countStuff(next + right, count)
            }

            '.' -> countStuff(next, count)
            else -> error("unreachable")
        }
    }

    return countStuff(start, 0)
}

fun part2(lines: List<String>): Long {
    val start = Point(0, lines.first().indexOfFirst { it == 'S' })
    val grid = lines.asCharGrid()
    val cache = mutableMapOf<Point, Long>()
    fun countStuff(point: Point): Long {
        val next = point + down
        return (if (!next.isInRange(grid.rows(), grid.cols())) 1
        else if (point in cache) cache[point]!!
        else
            when (grid[next.x][next.y]) {
                '^' -> {
                    countStuff(next + left) + countStuff(next + right)
                }

                '.' -> countStuff(next)
                else -> error("unreachable")
            }).also { cache[next] = cache.getOrPut(next) { 0 } + it }
    }

    return countStuff(start)
}

val lines = Path("../../input/7.txt").useLines { it.toList() }
part1(lines).let(::println)
part2(lines).let(::println)
