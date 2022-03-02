(* TODO: Write some tests for tabulate. *)
(* Note: we've added a type annotation here so that the compiler can help
   you write tests of the correct form. *)
let tabulate_tests: (((int -> int) * int) * int list) list = [
  (* Remember: your test cases should have this form:
     ((f, n), output)
     The following test case asserts that:
       tabulate (fun x -> x) (-1)
     should have the output []
  *)
  (*bottom is 120*)
  (*func , count up to from 0*)
  (((fun x-> x), 3), [0;1;2;3]);
  (((fun x-> x-1), 4), [-1;0;1;2;3]);
  (((fun x-> x+4), 2), [4;5;6]);
  (((fun x-> x-4), 0), [-4]);
  (((fun x -> x), -1), [])
]

(* TODO: Implement dist_table: (int * int) -> int -> float list *)
let dist_table_test: (((int*int)* int)*float list) list=[
  (((6,3),2), [0.; 0.; 0.2; 0.45; 0.6; 0.5; 0.]);
  (((6,3),0), [1.; 0.5; 0.2; 0.05; 0.; 0.; 0.]);
  (((6,3),1), [0.; 0.5; 0.6; 0.45; 0.2; 0.; 0.])
   (*[0;0;.2*.2*.6;.45*.05*.45; 0;0;0*)
   (*[0;0;  .024  ;  .010125  ; 0;0;0]*)
] 
let dist_table ((marblesTotal, marblesDrawn): (int * int)) (x: int) : float list =
  (*tabulate and no recursion*)
  (*  (n chose x) * (mT-n chose mD-x)
      --------------------------------- n counts up to marblesTotal starting at 0 
             (mT chose mD)
  
  *) (*need n for dist_black*)
  tabulate (fun n-> dist_black n x (marblesTotal, marblesDrawn)) marblesTotal
    
    (* raise NotImplemented*)
  
(* TODO: Write some test cases for is_empty. *)
let is_empty_tests: (float list list * bool) list = [
  ([[69.];[69.]], false);
  ([[]; [];[];[];[]], true);
  ([[]], true)
]

(* TODO: Implement is_empty: 'a list list -> bool *)
let is_empty (matrix: 'a list list) : bool =
  (*for_all*)
  List.for_all (fun e-> e=[]) matrix
    
let dist_matrix_test:(((int*int)*int list)*float list list)list=[
  (((1,1) ,[1;1]), [[0.;1.];[0.;1.]]);  
  (((3,2), [4;5]), [[0.;0.;0.;0.];[0.;0.;0.;0.]])
]

(* TODO: Implement dist_matrix: int * int -> int list -> float list list *)
let dist_matrix ((total, drawn): int * int) (resultList: int list) : float list list =
  (*map*)
  List.map (dist_table (total,drawn)) resultList

(* TODO: Implement combined_dist_table: float list list -> float list *)
let rec combined_dist_table (matrix: float list list) =
  (*map/map2 and fold right/left*)
  (*multiply matrix via everything in same row look at dist_table_test for sol. Final answer is list of products of respective rows*)
  if is_empty matrix then []
  else 
    let (allHds, remainMatrix)= (List.map (List.hd) matrix, List.map (List.tl) matrix ) in
    let prodHds= List.fold_left (fun x y-> x *. y) 1. allHds in
    prodHds::combined_dist_table remainMatrix 
    
  
  
  (* raise NotImplemented*)

(* Once you have implemented all the above functions, you can
   use this function to compute the maximum likelihood.
   You can try it on the given example by running:
     max_likelihood (6, 3) [2; 0; 1]
*)
let max_likelihood (total, drawn) resultList =
  (*finds index of max likelihood*)
  max_in_list
    (combined_dist_table
       (dist_matrix (total, drawn) resultList))


(* TODO: Implement all: (ingredients list -> bool) -> cake -> bool *)
let test_all:(((ingredients list-> bool)*cake)*bool)list=[
  (((List.mem (Chocolate)), Slice[Chocolate]), true);
  (((List.mem (Orange)), Slice[Chocolate]), false);
  
]
let rec all (p: (ingredients list -> bool)) (c: cake) : bool = 
   (*cake is a tree so either slice with ingredient list or 2 child cakes*)
  match c with
  |Slice(iL)-> p (iL)
  |Cake(x,y)-> all p x && all p y

(* TODO: Write some test cases for is_chocolate_cake. *)
let is_chocolate_cake_tests = [
  (Cake(Slice[Chocolate; Flour], Slice[Orange]), false);
  (Slice[Chocolate; Flour], true);
  (Slice[], false)
]

(* TODO: Implement is_chocolate_cake: cake -> bool *)
let is_chocolate_cake (c: cake) : bool = 
  
  all (List.mem(Chocolate)) c

(* TODO: Implement map: (ingredients list -> ingredients list) -> cake -> cake *)
let rec map (p: (ingredients list -> ingredients list)) (c: cake) = 
  match c with
  |Slice(iL)-> Slice (p iL)
  |Cake(x,y)-> Cake (map p x, map p y)  

(* TODO: Write some test cases for add_ingredient. *)
let add_ingredient_tests = [
  ((Orange,Slice[]), Slice[Orange]);
  ((Orange,Cake(Slice[], Cake(Slice[Chocolate], Slice[]))), Cake(Slice[Orange], Cake(Slice[Chocolate;Orange], Slice[Orange])))
]

(* TODO: Implement add_ingredient: ingredients -> cake -> cake *)
let add_ingredient (x: ingredients) (c: cake) : cake = 
  (*map*)
  map (insert x) c

(* TODO: Implement fold_cake: (ingredients list -> 'a -> 'a) -> 'a -> cake -> 'a  *)
let rec fold_cake (f: (ingredients list -> 'a -> 'a)) (base: 'a) (c: cake) : 'a = 
  match c with
  |Slice (iL)-> f (iL) (base)
  |Cake(x,y)-> fold_cake f (fold_cake f base x) y


(* TODO: Implement get_all_ingredients: cake -> ingredients list *)
let get_all_ingredients (c: cake) : ingredients list = 
  
  fold_cake union [] c
