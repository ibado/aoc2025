#!/usr/bin/env kotlin
@file:Import("list.main.kts")

import kotlin.io.path.Path
import kotlin.io.path.useLines
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.measureTimedValue

private val connections = 1000

data class Point(val x: Double, val y: Double, val z: Double) {
    override fun toString(): String = "($x, $y, $z)"
}

fun distance(p1: Point, p2: Point): Double = sqrt(
    (p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2) + (p1.z - p2.z).pow(2)
)

fun part1(lines: List<String>): Int {
    val pairsByDistance = lines.map {
        val (x, y, z) = it.split(",").map(String::toDouble)
        Point(x, y, z)
    }.pairs()
        .sortedBy { (p1, p2) ->  distance(p1, p2) }

    return computeCircuits(pairsByDistance.take(connections))
        .asSequence()
        .map { it.size }.distinct()
        .sortedByDescending { it }
        .take(3)
        .reduce(Int::times)
}

fun part2(lines: List<String>): Int {
    val pairsByDistance = lines.map {
        val (x, y, z) = it.split(",").map(String::toDouble)
        Point(x, y, z)
    }.pairs()
        .sortedBy { (p1, p2) ->  distance(p1, p2) }

    var circuits = computeCircuits(pairsByDistance.take(connections))

    for (conn in pairsByDistance.drop(connections)) {
        circuits = computeCircuits(listOf(conn), circuits)
        if (circuits.size == 1 && circuits.single().size == lines.size) {
            return (conn.first.x * conn.second.x).toInt()
        }
    }

    error("unreachable")
}

private fun computeCircuits(
    pairsByDistance: List<Pair<Point, Point>>,
    circuits: MutableList<Set<Point>> = mutableListOf()
): MutableList<Set<Point>> {
    var connections = pairsByDistance.map { (p1, p2) -> setOf(p1, p2) }
    var mergeDone = true
    while (mergeDone) {
        mergeDone = false
        for (connection in  connections) {
            var joined = false
            for ((idx, circuit) in circuits.withIndex()) {
                if (connection.intersect(circuit).isNotEmpty()) {
                    circuits[idx] += connection
                    joined = true
                    mergeDone = true
                    break
                }
            }
            if (joined) continue
            circuits.add(connection)
        }
        if (mergeDone) {
            connections = circuits.toList()
            circuits.clear()
        }
    }
    return circuits
}

private fun printSolution(lines: List<String>, f: (List<String>) -> Int): Unit =
    measureTimedValue { f(lines) }.let { (value, time) -> println("result: $value, time: $time") }

val lines = Path("../../input/8.txt").useLines { it.toList() }
printSolution(lines, ::part1)
printSolution(lines, ::part2)
