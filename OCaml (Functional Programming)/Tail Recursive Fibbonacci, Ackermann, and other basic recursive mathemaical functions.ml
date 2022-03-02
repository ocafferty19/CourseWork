(* Question 1 *)
(* TODO: Write your own tests for the fact function.
         See the provided tests for double, above, for how to write test cases.
         Remember that you should NOT test cases for n < 0.
*)
(* TODO: Correct these tests for the fact function. *)
let fact_tests = [
  (*1,2,6,24,120,720*)
  (0, 1.);
  (1, 1.);
  (2, 2.);
  (5, 120.);
  (6,720.)
]

(* TODO: Correct this implementation so that it compiles and returns
         the correct answers.
*)
let rec fact (n: int): float =
  match n with
  | 0 -> 1.
  | _ -> float_of_int(n) *. fact (n - 1)


(* TODO: Write your own tests for the binomial function.
         See the provided tests for fact, above, for how to write test cases.
         Remember that we assume that  n >= k >= 0; you should not write test cases where this assumption is violated.
*)
let binomial_tests = [
  (* Your test cases go here. Correct the incorrect test cases for the function. *)
   (*1,2,6,24,120,720,5040, 40320*)
  ((0,0), 1.);
  ((1, 1), 1.);
  ((1, 0), 1.);(*1/1*1*)
  ((2, 0), 1.);(*2/1*2=1*)
  ((10, 1), 10.);(*10!/9!=10*)
  ((10, 2), 45.)(*10!/8!=90
               90/2!=45*)
]

(* TODO: Correct this implementation so that it compiles and returns
         the correct answers.
*)
let binomial (n: int) (k: int) =
   (*1,2,6,24,120,720*)
  if n < 0
  then domain ()
  else (if k = n
        then 1.0(*1/1*1=1*)
        else fact n /. (fact k *. fact (n-k)))


(* TODO: Write a good set of tests for ackerman. *)
let ackerman_tests = [
  (* Your test cases go here *)
  ((0,0),1);
  ((0,1),2);
  ((1,0),2);
  ((2,0),3);
  ((2,3),9)
]

(* TODO: Correct this implementation so that it compiles and returns
         the correct answers.
*)
let ackerman (n, k)  =
  if n < 0 || n < 0
  then domain ()
  else (let rec ack n k =
          match (n, k) with
          | (0, _) ->  k + 1(*A(0,k) moving up guarantees n>=1 in case below*)
          | (_, 0) -> ack (n-1) 1 
          | (_, _) -> ack (n - 1) (ack n (k - 1))
        in ack n k)


(* Question 2: is_prime *)

(* TODO: Write a good set of tests for is_prime. *)
let is_prime_tests = [
(* Your tests go here *)
  
  (2, true);
  (3, true);
  (4, false);
  (5, true);
  (6, false);
  (7, true);
  (8, false);
  (9, false);
  (10, false)

]

(* TODO: Correct this implementation so that it compiles and returns
         the correct answers.
*)
let is_prime n =
  if n<=1 then domain()
  else if
    n<=2 then true
  else
    let rec it n i =
      if (n mod i)==0 then false
      else if (i*i)>n then true
      else it n (i+1) 
    in
    it n 2 
      
    

(* Question 3: Newton-Raphson method for computing the square root
*)

let square_root_tests = [
  (1.,1.);
  (4.,2.);
  (25.,5.);
  (169.,13.);
  (100.,10.)
    
]

let square_root a =
  let rec findroot x acc =
    let x' = (a/.x +. x) /. 2. in
    let abs_diff=abs_float(x -. x') in
    if abs_diff<acc then x'
    else findroot x' acc
  in
  if a > 0.
  then findroot 1. epsilon_float
  else domain ()


(* Question 4: Fibonacci*)

(* TODO: Write a good set of tests for fib_tl. *)
let fib_tl_tests = [
  (0,1);
  (1,1);
  (2,2);
  (3,3);
  (4,5);
  (5,8)
  
]

(* TODO: Implement a tail-recursive helper fib_aux. *)
let rec fib_aux n a b = 
  (*a and b are previous 2 value and n is index*) 
  (*a is prev value and b is prev prev value*)
  if n<=1 then a (*<= for when fib_tl 0*)
  else fib_aux (n-1) (a+b) (a)
  
        

(* TODO: Implement fib_tl using fib_aux. *)
let fib_tl n =
  
  if n<0 then domain ()
  else fib_aux n 1 1
