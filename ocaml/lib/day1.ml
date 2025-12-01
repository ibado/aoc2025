let modulo n m = ((n mod m) + m) mod m
let num_at n s = String.to_seq s |> Seq.drop n |> String.of_seq |> int_of_string

let part1 input =
  let _, count =
    List.fold_left
      (fun (dial, count) it ->
        let dial =
          match (String.get it 0, num_at 1 it) with
          | 'L', n -> modulo (dial - n) 100
          | 'R', n -> modulo (dial + n) 100
          | _ -> assert false
        in
        if dial = 0 then (dial, count + 1) else (dial, count))
      (50, 0) input
  in
  count

let part2 input =
  let rotate (dial, count) s =
    let increment =
      match String.get s 0 with 'L' -> -1 | 'R' -> 1 | _ -> assert false
    in
    let clicks = num_at 1 s in
    List.init clicks Fun.id
    |> List.fold_left
         (fun (dial, count) _ ->
           let new_dial = modulo (dial + increment) 100 in
           (new_dial, count + if new_dial = 0 then 1 else 0))
         (dial, count)
  in
  let _, count = List.fold_left rotate (50, 0) input in
  count
