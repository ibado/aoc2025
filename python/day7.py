
def part1(lines: list[str]) -> int:
    beams = [False] * len(lines[0])
    count = 0
    for row in lines:
        for i, char in enumerate(row):
            if char == 'S':
                beams[i] = True
            elif char == '^':
                if beams[i]:
                    beams[i] = False
                    beams[i+1] = True
                    beams[i-1] = True
                    count += 1
    return count

def part2(lines: list[str]) -> int:
    beams = [0] * len(lines[0])
    for row in lines:
        for i, char in enumerate(row):
            if char == 'S':
                beams[i] = True
            elif char == '^':
                if beams[i]:
                    beams[i+1] += beams[i]
                    beams[i-1] += beams[i]
                    beams[i] = 0
    return sum(beams)

