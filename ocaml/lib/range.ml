type range = { first : int; last : int }

let ( <.> ) first last = { first; last }
let contains num r = r.first <= num && num <= r.last

let union_if_overlap r1 r2 =
  if
    contains r2.first r1 || contains r2.last r1 || contains r1.first r2
    || contains r1.last r2
  then Some (min r1.first r2.first <.> max r1.last r2.last)
  else None
