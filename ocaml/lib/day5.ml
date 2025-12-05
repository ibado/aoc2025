open Range

let parse_ranges input =
  List.take_while (fun s -> s <> "") input
  |> List.map (fun s ->
         let l = String.split_on_char '-' s in
         List.hd l |> int_of_string <.> (List.nth l 1 |> int_of_string))

let part1 input =
  let ranges = parse_ranges input in
  let ids =
    List.drop_while (fun s -> s <> "") input
    |> List.drop 1 |> List.map int_of_string
  in
  List.fold_left
    (fun count id ->
      if List.exists (fun r -> Range.contains id r) ranges then count + 1
      else count)
    0 ids

let part2 input =
  parse_ranges input
  |> List.sort (fun r1 r2 -> r2.first - r1.first)
  |> List.fold_left
       (fun acc r ->
         match acc with
         | [] -> [ r ]
         | hd :: tl -> (
             match Range.union_if_overlap hd r with
             | Some nr -> nr :: tl
             | None -> r :: acc))
       []
  |> List.fold_left (fun acc r -> acc + r.last - r.first + 1) 0
