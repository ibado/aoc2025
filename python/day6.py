def parse_nums(line: str) -> list[int]:
    nums = filter(str.strip, line.split(' '))
    return list(map(int, nums))

def op_ident(op: str) -> int:
    assert op in ['*', '+']
    return 1 if op == '*' else 0

def incr_by_op(val: int, op: str, amount: int) -> int:
    assert op in ['*', '+']
    if op == '*':
        val *= amount
    elif op == '+':
        val += amount
    return val

def part1(lines: list[str]) -> int:
    operators = list(filter(str.strip, lines.pop().split(' ')))
    result = parse_nums(lines.pop(0))
    for line in lines:
        nums = parse_nums(line)
        for i, n in enumerate(nums):
            op = operators[i]
            result[i] = incr_by_op(result[i], op, n)
    return sum(result)

def part2(lines: list[str]) -> int:
    operators = list(filter(str.strip, lines.pop().split(' ')))
    rows = len(lines)
    cols = len(lines[0]) - 1
    op_idx = 0
    result = 0
    temp = op_ident(operators[op_idx])
    # transposed iteration
    for j in range(cols):
        num = ''
        for i in range(rows):
            num += lines[i][j]
        if num.strip():
            op = operators[op_idx]
            n = int(num.strip())
            temp = incr_by_op(temp, op, n)
        else:
            op_idx += 1
            result += temp
            temp = op_ident(operators[op_idx])
    result += temp # there's no black line at the end
    return result
