open Util

let part1 input =
  let max_num s =
    let rec aux s i j m =
      let n = int_of_string (String.sub s i 1 ^ String.sub s j 1) in
      let nm = max n m in
      if j < String.length s - 1 then aux s i (j + 1) nm
      else if i < String.length s - 2 then aux s (i + 1) (i + 2) nm
      else nm
    in
    aux s 0 1 0
  in
  sum_of max_num input

let max_between arr i j =
  let rec loop x (max, idx) =
    if x > j then (max, idx)
    else
      let m = arr.(x) in
      loop (x + 1) (if m > max then (m, x) else (max, idx))
  in
  loop i (0, -1)

let max_num line =
  let nums =
    String.to_seq line |> Seq.map (fun c -> Char.code c - 48) |> Array.of_seq
  in
  let rec aux digit idx num =
    if digit >= 12 then num
    else
      let m, i = max_between nums idx (Array.length nums - 12 + digit) in
      aux (digit + 1) (i + 1) ((num * 10) + m)
  in
  aux 0 0 0

let part2 input = sum_of max_num input
