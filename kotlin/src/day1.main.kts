#!/usr/bin/env kotlin

import kotlin.io.path.Path
import kotlin.io.path.useLines

private fun turnDial(dir: Char, dialValue: Int, clicks: Int) = when (dir) {
    'L' -> (dialValue - clicks) % 100
    'R' -> (dialValue + clicks) % 100
    else -> error("unreachable")
}

fun part1(input: List<String>) =
    input.fold(0 to 50) { (count, dial), it ->
        val dir = it.first()
        val clicks = it.drop(1).toInt()
        val newDial = turnDial(dir, dial, clicks)
        (count + if (newDial == 0) 1 else 0) to newDial
    }.first

fun part2(input: List<String>) =
    input.fold(0 to 50) { (count, dial), it ->
        val dir = it.first()
        val clicks = it.drop(1).toInt()
        count + (1..clicks).count {
            when (dir) {
                'L' -> (dial - it) % 100
                'R' -> (dial + it) % 100
                else -> error("unreachable")
            } == 0
        } to turnDial(dir, dial, clicks)
    }.first

val lines = Path("../../input/1.txt").useLines { it.toList() }
part1(lines).let(::println)
part2(lines).let(::println)

