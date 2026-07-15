let numbers = [4; 7; 2; 7; 9; 2; 5; 7; 4; 2]

let mean values =
  let total = List.fold_left ( + ) 0 values in
  float_of_int total /. float_of_int (List.length values)

let median values =
  let sorted = List.sort compare values in
  let length = List.length sorted in
  if length mod 2 = 0 then
    let left = List.nth sorted (length / 2 - 1) in
    let right = List.nth sorted (length / 2) in
    (float_of_int left +. float_of_int right) /. 2.0
  else
    float_of_int (List.nth sorted (length / 2))

let frequencies values =
  List.fold_left
    (fun acc value ->
      let count = try List.assoc value acc with Not_found -> 0 in
      (value, count + 1) :: List.remove_assoc value acc)
    []
    values

let mode values =
  let counts = frequencies values in
  let max_count =
    List.fold_left (fun current (_, count) -> max current count) 0 counts
  in
  List.fold_right
    (fun (value, count) acc ->
      if count = max_count then value :: acc else acc)
    counts
    [],
  max_count

let string_of_int_list values =
  String.concat ", " (List.map string_of_int values)

let () =
  let modes, count = mode numbers in
  Printf.printf "Dataset: %s\n" (string_of_int_list numbers);
  Printf.printf "Mean: %.2f\n" (mean numbers);
  Printf.printf "Median: %.2f\n" (median numbers);
  Printf.printf "Mode: %s (appeared %d times)\n"
    (string_of_int_list modes)
    count
