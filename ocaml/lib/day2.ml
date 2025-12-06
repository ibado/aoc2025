open Util

let is_even n = n % 2 = 0

let digits n =
  let rec aux n d c = if n / d = 0 then c else aux n (d * 10) (c + 1) in
  aux n 10 1

let is_invalid n =
  if not (is_even (digits n)) then false
  else
    let halflen = digits n / 2 in
    let multiplier = intpow 10 halflen in
    let first_half = n / multiplier in
    n = (first_half * multiplier) + first_half

let chunks_by_len =
  [|
    [ 1 ];
    [ 1 ];
    [ 1 ];
    [ 1; 2 ];
    [ 1 ];
    [ 1; 2; 3 ];
    [ 1 ];
    [ 1; 2; 4 ];
    [ 1; 3 ];
    [ 1; 2; 5 ];
  |]

let is_invalid2 n =
  let len = digits n in
  if len = 1 then false
  else
    let chunks = chunks_by_len.(len - 1) in
    List.exists
      (fun chunk_len ->
        let chunks =
          List.init (len / chunk_len) (fun i ->
              n % intpow 10 ((i + 1) * chunk_len) / intpow 10 (i * chunk_len))
        in
        let first = List.hd chunks in
        List.for_all (fun ch -> ch = first) chunks)
      chunks

let find_invalid sum n1 n2 f =
  let rec aux sum n1 n2 f =
    if n1 > n2 then sum
    else if f n1 then aux (sum + n1) (n1 + 1) n2 f
    else aux sum (n1 + 1) n2 f
  in
  aux sum n1 n2 f

let solve input f =
  List.hd input |> String.split_on_char ','
  |> List.map (fun s -> String.split_on_char '-' s)
  |> List.map (fun l -> List.map int_of_string l)
  |> List.map (fun l -> find_invalid 0 (List.nth l 0) (List.nth l 1) f)
  |> List.fold_left ( + ) 0

let part1 input = solve input is_invalid
let part2 input = solve input is_invalid2
