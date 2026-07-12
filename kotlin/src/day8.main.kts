#!/usr/bin/env kotlin
@file:Import("list.main.kts")

import kotlin.io.path.Path
import kotlin.io.path.useLines
import kotlin.math.pow
import kotlin.math.sqrt

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

    return computeCircuits(pairsByDistance.take(1000))
        .asSequence()
        .map { it.size }.distinct()
        .sortedByDescending { it }
        .take(3)
        .reduce(Int::times)
}

private fun computeCircuits(pairsByDistance: List<Pair<Point, Point>>): List<Set<Point>> {
    var connections = pairsByDistance.map { (p1, p2) -> setOf(p1, p2) }
    val circuits = mutableListOf<Set<Point>>()
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

val lines = Path("../../input/8.txt").useLines { it.toList() }
part1(lines).let(::println)