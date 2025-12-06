#!/usr/bin/env kotlin

import kotlin.io.path.Path
import kotlin.io.path.useLines

fun parseOperations(lines: List<String>): List<Pair<Int, Char>> =
    lines.last().mapIndexedNotNull { i, c ->
        when (c) {
            '+', '*' -> i to c
            else -> null
        }
    }

fun opIdentity(op: Char): Long =
    when (op) {
        '*' -> 1L
        '+' -> 0L
        else -> error("unreachable")
    }

fun operationPart2(op: Char, list: List<String>): Long {
    var l = list
    var result = opIdentity(op)
    while (!l.all { it.isEmpty() }) {
        val n = buildString { for (s in l) append(s.last()) }.trim().toInt()
        l = l.map { it.dropLast(1) }
        when (op) {
            '*' -> result *= n
            '+' -> result += n
            else -> error("unreachable")
        }
    }
    return result
}

fun part1(lines: List<String>): Long {
    val operations = parseOperations(lines).map { (_, op) -> op }
    return lines.dropLast(1).map {
        it.trim().split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
    }.fold(operations.map(::opIdentity)) { partials, nums ->
        nums.mapIndexed { i, n ->
            when (operations[i]) {
                '*' -> partials[i] * n
                '+' -> partials[i] + n
                else -> error("unreachable")
            }
        }
    }.sum()
}

fun part2(lines: List<String>): Long {
    val operations = parseOperations(lines)
    val numRows = lines.dropLast(1).map { s ->
        operations.windowed(2, partialWindows = true) {
            if (it.size == 1) {
                val (i, _) = it.first()
                s.substring(i)
            } else {
                val (i, _) = it[0]
                val (j, _) = it[1]
                s.substring(i, j - 1)
            }
        }
    }

    return operations
        .mapIndexed { i, (_, op) -> op to numRows.map { it[i] } }
        .sumOf { (op, list) -> operationPart2(op, list) }
}

val lines = Path("../../input/6.txt").useLines { it.toList() }
part1(lines).let(::println)
part2(lines).let(::println)
