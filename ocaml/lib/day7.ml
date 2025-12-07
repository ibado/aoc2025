let down = Point.create 1 0
let left = Point.create 0 (-1)
let right = Point.create 0 1

let parse_input input =
  let mx = Util.parse_matrix input in
  let rows = Array.length mx in
  let cols = Array.length mx.(0) in
  let start =
    Array.find_index (Char.equal 'S') mx.(0) |> Option.get |> Point.create 0
  in
  (mx, rows, cols, start)

let part1 input =
  let mx, rows, cols, start = parse_input input in
  let seen = Array.make_matrix rows cols false in
  let rec aux p count =
    let np = Point.add p down in
    if (not (Point.in_range rows cols np)) || seen.(np.x).(np.y) then count
    else
      match mx.(np.x).(np.y) with
      | '.' -> aux np count
      | '^' ->
          let inc =
            match seen.(np.x).(np.y) with
            | true -> 0
            | false ->
                seen.(np.x).(np.y) <- true;
                1
          in
          let pl = Point.add np left in
          let pr = Point.add np right in
          inc + aux pl count + aux pr count
      | _ -> assert false
  in
  aux start 0

let part2 input =
  let mx, rows, cols, start = parse_input input in
  let cache = Array.make_matrix rows cols 0 in
  let rec aux p =
    let np = Point.add p down in
    if not (Point.in_range rows cols np) then 1
    else
      let res =
        if cache.(p.x).(p.y) <> 0 then cache.(np.x).(np.y)
        else
          match mx.(np.x).(np.y) with
          | '.' -> aux np
          | '^' ->
              let pl = Point.add np left in
              let pr = Point.add np right in
              aux pl + aux pr
          | _ -> assert false
      in
      cache.(np.x).(np.y) <- res;
      res
  in
  aux start
