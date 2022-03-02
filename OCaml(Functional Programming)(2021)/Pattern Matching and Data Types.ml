(* Reminder: If a test case requires multiple arguments, use a tuple:
let myfn_with_2_args_tests = [
  ((arg1, arg1), (expected_output))
]
*)

(* Q1 *)
(* TODO: Write a good set of tests for compress *)
let compress_tests = [
  ([A;A], [(2,A)]);
  ([C;A;T], [(1,C);(1, A);(1, T)]);
  ([],[]);
  ([C],[(1,C)] )
  
]

(* TODO: Implement compress. *)
let compress (l : nucleobase list) : (int * nucleobase) list = 
  
      
  let rec help num l'=
    match l' with
    |hd::nhd::tl-> 
        (*head is removed each step of recursion and next head becomes head*)
        if hd=nhd then help (num+1) (nhd::tl) 
        else (num, hd)::help 1 (nhd::tl)
    |hd::tl->  (num, hd)::help 1 tl 
    |[]-> []
  in help 1 l 
        
       
  
(* TODO: Write a good set of tests for decompress *)
let decompress_tests = [
  ([(2,A)], [A;A]);
  ([(1,C);(1, A);(1, T)],[C;A;T]);
  ([],[]);
  ([(1,C)],[C] )
]

(* TODO: Implement decompress. *)
let rec decompress (l : (int * nucleobase) list) : nucleobase list = 
  (*good*)
  match l with
  |[]-> []
  |hd::tl-> 
      let (num, nuc)=hd in
      if num=1 then 
        nuc::decompress tl
      else nuc::decompress ((num-1, nuc)::tl) 
                            
                            
    


(* Q2 *)
(* TODO: Write a good set of tests for eval *)
let eval_tests = [
  (PLUS (FLOAT 2.2, FLOAT 3.)), 5.2;
  (DIV(MINUS(FLOAT 3., FLOAT 1.), FLOAT 2.)), 1.;
  (COS (FLOAT 1.57)), 0.000796326710733263345;
  (EXP(FLOAT 2.)), 7.38905609893065;
  (FLOAT 69.), 69.
  
  
]

(* TODO: Implement eval. *)
let rec eval e =
  match e with 
  |PLUS(x,y)-> (eval x) +. (eval y)
  |MINUS(x,y)-> (eval x)-.(eval y)
  |MULT(x,y) -> (eval x) *. (eval y)
  |DIV(x,y) -> (eval x) /. (eval y)
  |SIN(x)-> sin (eval x)
  |COS(x)-> cos (eval x)
  |EXP(x)-> exp (eval x)
  |FLOAT (x)->x
(* TODO: Write a good set of tests for to_instr *)
let to_instr_tests = [
  ((MULT (PLUS (FLOAT 2.2, FLOAT 3.3), FLOAT 5.0)),[Float 2.2; Float 3.3; Plus; Float 5.; Mult]);
  (PLUS(FLOAT 5.2, FLOAT 2.), [Float 5.2;Float 2.;Plus]);
  (DIV(FLOAT 5.2, FLOAT 2.), [Float 5.2;Float 2.;Div]);
  (EXP(FLOAT 2.), [Float 2.; Exp]);
  (COS(FLOAT 4.), [Float 4.; Cos]);
  (FLOAT 3.45, [Float 3.45])
]

(* TODO: Implement to_instr. *)
let rec to_instr e = 
  
  match e with
  |PLUS(x,y)-> (to_instr x) @ (to_instr y) @ [Plus]
  |MINUS(x,y)-> (to_instr x) @ (to_instr y) @ [Minus]
  |MULT(x,y) ->(to_instr x) @ (to_instr y) @ [Mult]
  |DIV(x,y) -> (to_instr x) @ (to_instr y) @ [Div]
  |SIN(x)-> (to_instr x) @ [Sin]
  |COS(x)-> (to_instr x)@ [Cos]
  |EXP(x)-> (to_instr x)@ [Exp]
  |FLOAT (x)-> [Float x]

(* TODO: Write a good set of tests for instr *)
let instr_tests = [
  ((Mult, [70.;2.1]), Some [147.]);
  ((Div, [2.;70.]), Some [35.]);
  ((Plus, [70.;2.1]), Some [72.1]);
  ((Minus, [2.1;70.]), Some [67.9]);
  ((Exp,[2.;3.]), Some[ 7.38905609893065;3.]);
  ((Cos, [1.57]),Some [0.000796326710733263345]);
  ((Float 3.6, [1.]), Some [3.6;1.])

  
]


(* TODO: Implement to_instr. *)               
let instr i s = 
  match (i,s) with 
  |Plus,x::y::tl->Some(y+.x::tl)
  |Minus,x::y::tl->Some(y-.x::tl)
  |Mult, x::y::tl->Some(y*.x::tl)
  |Div, x::y::tl->Some(y/.x::tl)
  |Sin,x::tl->Some(sin x::tl)
  |Cos,x::tl->Some(cos x::tl)
  |Exp,x::tl-> Some(exp x::tl)
  |Float f, l->Some(f::l) 
  |_-> None
                       
                       
      
         
                                


(* TODO: Write a good set of tests for prog *)
let prog_tests = [
  ([Float 7.2; Float 7.8; Plus; Float 3.; Div], Some 5.);
  (([Float 2.;Exp]), Some 7.38905609893065);
  ([], None)
]

(* TODO: Implement prog. *)
let prog instrs = 
  let rec help l' (stack:stack): float option= 
    match (l', stack) with
    |[], []->None
    |[], hd::_->Some (hd)
    |hd::tl, _->
        match  instr hd stack with
        |Some(x)->  help tl x
        |_->None 
  in help instrs []
