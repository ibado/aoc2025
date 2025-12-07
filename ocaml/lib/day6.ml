open Util

let parse_nums input =
  List.take_while
    (fun s ->
      Option.is_some @@ int_of_string_opt (String.sub (String.trim s) 0 1))
    input

let parse_operators input =
  List.nth input (List.length input - 1)
  |> String.split_on_char ' '
  |> List.filter (fun s -> s <> "")
  |> Array.of_list

let operators_with_idx input =
  List.nth input (List.length input - 1)
  |> String.to_seq
  |> Seq.mapi (fun i it -> if it <> ' ' then Some (i, it) else None)
  |> Seq.filter_map Fun.id |> List.of_seq

let calc_part2 (op, l) =
  let init = if op = '*' then 1 else 0 in
  let rec aux op l acc =
    if List.for_all (fun s -> s = "") l then acc
    else
      let nl = List.map (fun s -> if s <> "" then drop_last s else "") l in
      let nacc =
        List.map (fun s -> get_last s) l
        |> List.fold_left (fun s c -> s ^ String.make 1 c) ""
        |> String.trim |> int_of_string
      in
      match op with
      | '*' -> aux op nl acc * nacc
      | '+' -> aux op nl acc + nacc
      | _ -> assert false
  in
  aux op l init

let part1 input =
  let nums =
    parse_nums input |> List.map Util.parse_nums |> Array.of_list
    |> Array.map Array.of_list
  in
  let operators = parse_operators input in
  Array.fold_left
    (fun array_acc it ->
      Array.mapi
        (fun i n ->
          match operators.(i) with
          | "*" -> array_acc.(i) * n
          | "+" -> array_acc.(i) + n
          | _ -> assert false)
        it)
    (Array.map
       (fun op -> match op with "*" -> 1 | "+" -> 0 | _ -> assert false)
       operators)
    nums
  |> Array.fold_left ( + ) 0

let part2 input =
  let ops = operators_with_idx input in
  let num_rows =
    parse_nums input
    |> List.map (fun s ->
           Util.window2
             (fun acc w ->
               match w with
               | (i, _) :: (j, _) :: _ ->
                   Some (acc @ [ String.sub s i (j - 1 - i) ])
               | [ (i, _) ] ->
                   Some (acc @ [ String.sub s i (String.length s - i) ])
               | _ -> None)
             [] ops ~partial:true)
  in
  ops
  |> List.mapi (fun i (_, op) ->
         (op, List.map (fun nums -> List.nth nums i) num_rows))
  |> sum_of calc_part2
