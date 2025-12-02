let modulo n m = ((n mod m) + m) mod m
let is_even n = modulo n 2 = 0

let digits n =
  let rec aux n d c = if n / d = 0 then c else aux n (d * 10) (c + 1) in
  aux n 10 1

let intpow base exp =
  int_of_float (Float.pow (float_of_int base) (float_of_int exp))

let is_invalid n =
  if not (is_even (digits n)) then false
  else
    let halflen = digits n / 2 in
    let multiplier = intpow 10 halflen in
    let first_half = n / multiplier in
    n = (first_half * multiplier) + first_half

let rec find_invalid sum n1 n2 =
  if n1 > n2 then sum
  else if is_invalid n1 then find_invalid (sum + n1) (n1 + 1) n2
  else find_invalid sum (n1 + 1) n2

let part1 input =
  List.hd input |> String.split_on_char ','
  |> List.map (fun s -> String.split_on_char '-' s)
  |> List.map (fun l -> List.map int_of_string l)
  |> List.map (fun l -> find_invalid 0 (List.nth l 0) (List.nth l 1))
  |> List.fold_left ( + ) 0

let part2 _input = 0
