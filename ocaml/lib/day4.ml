let part1 input =
  let mx = Util.parse_matrix input in
  let rows = Array.length mx in
  let cols = Array.length mx.(0) in
  let count = ref 0 in
  for i = 0 to rows - 1 do
    for j = 0 to cols - 1 do
      if mx.(i).(j) = '@' then (
        let p = Point.create i j in
        let rolls = ref 0 in
        for d = 0 to 7 do
          let pd = Point.add p Point.all_dirs.(d) in
          if Point.in_range rows cols pd && mx.(pd.x).(pd.y) = '@' then
            incr rolls
        done;
        if !rolls < 4 then incr count)
    done
  done;
  !count

let part2 input =
  let mx = Util.parse_matrix input in
  let rows = Array.length mx in
  let cols = Array.length mx.(0) in
  let removed = ref true in
  let count = ref 0 in
  while !removed do
    removed := false;
    for i = 0 to rows - 1 do
      for j = 0 to cols - 1 do
        if mx.(i).(j) = '@' then (
          let p = Point.create i j in
          let rolls = ref 0 in
          for d = 0 to 7 do
            let pd = Point.add p Point.all_dirs.(d) in
            if Point.in_range rows cols pd && mx.(pd.x).(pd.y) = '@' then
              incr rolls
          done;
          if !rolls < 4 then (
            incr count;
            removed := true;
            mx.(i).(j) <- '.'))
      done
    done
  done;
  !count
