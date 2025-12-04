#!/usr/bin/env kotlin
@file:Import("grid.main.kts")

import kotlin.io.path.Path
import kotlin.io.path.useLines

fun part1(input: List<String>) = findAccessibleRolls(input.asCharGrid()).size

fun part2(input: List<String>) = buildSet {
    var grid = input.asCharGrid()
    while (true) {
        val partial = findAccessibleRolls(grid)
        addAll(partial)
        if (partial.isEmpty()) break
        grid = generateGrid(grid.rows(), grid.cols()) { i, j ->
            if (Point(i, j) in partial) '.' else grid[i][j]
        }
    }
}.size

fun findAccessibleRolls(grid: Grid<Char>): Set<Point> =
    grid.fold(mutableSetOf()) { acc, i, j ->
        acc.also {
            if (grid[i][j] == '@') {
                val rolls = allDirs.count { dir ->
                    val pd = Point(i, j) + dir
                    pd.isInRange(grid.rows(), grid.cols()) && grid[pd.x][pd.y] == '@'
                }
                if (rolls < 4) it.add(Point(i, j))
            }
        }
    }

fun List<String>.asCharGrid() = map { it.toList() }

val lines = Path("../../input/4.txt").useLines { it.toList() }
part1(lines).let(::println)
part2(lines).let(::println)