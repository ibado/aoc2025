#!/usr/bin/env kotlin

import kotlin.io.path.Path
import kotlin.io.path.useLines
import kotlin.math.max
import kotlin.math.min

fun parseInput(input: List<String>): Pair<List<LongRange>, List<Long>> {
    val ranges = input.takeWhile { it.isNotEmpty() }
        .map {
            val (lower, upper) = it.split("-").map { it.toLong() }
            lower..upper
        }
    val ids = input.takeLastWhile { it.isNotEmpty() }.map { it.toLong() }
    return ranges to ids
}

fun LongRange.intersect(other: LongRange): Boolean =
    this.first in other || this.last in other ||
            other.first in this || other.last in this

fun part1(input: List<String>): Int =
    parseInput(input).let { (ranges, ids) ->
        ids.count { id ->
            ranges.any { range -> id in range }
        }
    }

fun part2(input: List<String>): Long {
    val ranges = parseInput(input).first.sortedBy { it.last }
    val overlaps = mutableSetOf<LongRange>()
    for ((i, range) in ranges.withIndex()) {
        var r1 = range
        for (r2 in ranges.drop(i + 1)) {
            if (r1.intersect(r2)) {
                r1 = min(r1.first, r2.first)..max(r1.last, r2.last)
            }
        }
        if (overlaps.any { it.intersect(r1) }) continue
        overlaps.add(r1)
    }
    return overlaps.sumOf { it.last - it.first + 1 }
}

val lines = Path("../../input/5.txt").useLines { it.toList() }
part1(lines).let(::println)
part2(lines).let(::println)
