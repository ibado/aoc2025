
def solve(lines: list[str], n: int) -> int:
    sum = 0
    for line in lines:
        x0 = 0
        num = 0
        for nth in range(0, n):
            digit = 0
            for i in range(x0, len(line) - n + nth):
                x = int(line[i])
                if x > digit:
                    digit = x
                    x0 = i + 1
            num = num * 10 + digit
        sum += num

    return sum

def part1(lines: list[str]) -> int:
    return solve(lines, 2)

def part2(lines: list[str]) -> int:
    return solve(lines, 12)
