let read_lines filename =
  In_channel.with_open_text filename In_channel.input_lines

let input_day day =
  assert (day < 25);
  read_lines (Printf.sprintf "../input/%d.txt" day)

let parse_nums line =
  String.split_on_char ' ' line
  |> List.filter_map (fun s ->
         if s <> "" then Option.bind (Some s) int_of_string_opt else None)

let rec window2 f acc l ?(partial = false) =
  match l with
  | [] -> acc
  | [ n1 ] ->
      if partial then match f acc [ n1 ] with None -> acc | Some r -> r
      else acc
  | n1 :: n2 :: l -> (
      match f acc [ n1; n2 ] with
      | None -> acc
      | Some r -> window2 f r (n2 :: l) ~partial)

let find_point mx c =
  let i =
    Option.get @@ Array.find_index (fun s -> Array.exists (Char.equal c) s) mx
  in
  let j = Option.get @@ Array.find_index (Char.equal c) mx.(i) in
  Point.create i j

let parse_matrix input =
  Array.of_list input |> Array.map String.to_seq |> Array.map Array.of_seq

let intpow base exp =
  int_of_float (Float.pow (float_of_int base) (float_of_int exp))

let ( % ) n m = ((n mod m) + m) mod m
let drop_last s = String.sub s 0 (String.length s - 1)
let get_last s = String.get s (String.length s - 1)
