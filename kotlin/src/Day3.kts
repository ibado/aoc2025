#!/usr/bin/env kotlin

import kotlin.io.path.Path
import kotlin.io.path.useLines
import kotlin.math.max

fun part1(lines: List<String>): Int =
    lines.sumOf { line ->
        var maxNum = 0
        for (i in 0..<(line.length - 1)) {
            for (j in (i + 1)..<line.length) {
                val num = "${line[i]}${line[j]}".toInt()
                maxNum = max(num, maxNum)
            }
        }
        maxNum
    }

fun part2(lines: List<String>): Long =
    lines.sumOf { line ->
        var num = 0L
        var idx = 0
        repeat(12) { digit ->
            var maxDigit = 0L
            for (i in idx..(line.length - 12 + digit)) {
                val next = line[i].digitToInt()
                if (next > maxDigit) {
                    idx = i + 1
                    maxDigit = next.toLong()
                }
            }
            num = num * 10 + maxDigit
        }
        num
    }

val lines = Path("../../input/3.txt").useLines { it.toList() }
part1(lines).let(::println)
part2(lines).let(::println)