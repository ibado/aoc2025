#!/usr/bin/env kotlin

import kotlin.io.path.Path
import kotlin.io.path.useLines

fun ranges(input: List<String>): List<LongRange> =
    input.first()
        .split(',', '-')
        .map(String::toLong)
        .chunked(2) { (first, second) -> first..second }

fun part1(input: List<String>): Long {
    val regex = """(\d+)\1""".toRegex()
    return ranges(input).flatMap { range -> range.filter { regex.matches(it.toString()) } }.sum()
}

private fun part2(input: List<String>): Long {
    val regex = """(\d+)\1+""".toRegex()
    return ranges(input).flatMap { range -> range.filter { regex.matches(it.toString()) } }.sum()
}

val lines = Path("../../input/2.txt").useLines { it.toList() }
part1(lines).let(::println)
part2(lines).let(::println)