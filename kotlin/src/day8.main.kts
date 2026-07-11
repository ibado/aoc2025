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
        .take(1000)

    val circuits = mutableListOf<Set<Point>>()
    for ((p1, p2) in  pairsByDistance) {
        var joined = false
        for ((idx, circuit) in circuits.withIndex()) {
            if (p1 in circuit || p2 in circuit) {
                circuits[idx] += p1
                circuits[idx] += p2
                joined = true
                break
            }
        }
        if (joined) continue
        circuits.add(mutableSetOf(p1, p2))
    }

    var mergeDone = true
    val merged = mutableSetOf<Set<Point>>()
    val toRemove = mutableSetOf<Set<Point>>()
    while (mergeDone) {
        mergeDone = false
        for ((i, circuit) in circuits.withIndex()) {
            for (other in circuits.drop(i + 1)) {
                if (circuit.intersect(other).isNotEmpty()) {
                    merged.add(circuit + other)
                    toRemove.add(circuit)
                    toRemove.add(other)
                    mergeDone = true
                }
            }
        }
        circuits.removeAll(toRemove)
        circuits.addAll(merged)
        merged.clear()
        toRemove.clear()
    }

    return circuits.asSequence()
        .map { it.size }.distinct()
        .sortedByDescending { it }
        .take(3)
        .reduce(Int::times)
}

val lines = Path("../../input/8.txt").useLines { it.toList() }
part1(lines).let(::println)