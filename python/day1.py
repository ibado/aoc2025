def turn_dial(dir: str, prev: int, clicks: int) -> int:
    if dir == 'R':
        return (prev + clicks) % 100
    elif dir == 'L':
        return (prev - clicks) % 100
    else:
        raise ValueError("Invalid dir!")


def part1(lines: list[str]) -> int:
    dial = 50
    count = 0
    for line in lines:
        dir = line[0]
        num = int(line[1:])
        dial = turn_dial(dir, dial, num)
        if dial == 0:
            count += 1

    return count

def part2(lines) -> int:
    dial = 50
    count = 0
    for line in lines:
        dir = line[0]
        num = int(line[1:])
        for _ in range(num):
            dial = turn_dial(dir, dial, 1)
            if dial == 0:
                count += 1
    return count
