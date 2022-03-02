(* ------------------------------------------------------------------------*)
(* Q 1 : Money in the bank (25 points)                                     *)
(* ------------------------------------------------------------------------*)
(*unit generally is ref like rapp*)
let new_account (p: passwd) : bank_account =
  
  let inc= fun attempt->attempt:= (!attempt)+1 in
  let r= fun attempt-> attempt:=0 in(*reset attempt*)
  let cur_b = ref 0 in 
  let a = ref 0 in 
  let p_ref= ref p in 
  
  {update_passwd = (fun old_pass->fun new_pass-> 
       if old_pass = !p_ref then 
         (r(a);p_ref := new_pass)
       else
         (inc(a);raise wrong_pass)
     );
   retrieve= (fun pass->fun withdrawn-> 
       if !a >= 3 then raise too_many_attempts else
         (
           match !p_ref = pass with 
           |false->(inc(a);raise wrong_pass)
           |true when !cur_b>=withdrawn->(r(a);cur_b:=(!cur_b)-withdrawn) 
           |true-> (r(a);raise no_money)
         )
     );
   deposit=(fun pass-> fun add-> 
       if !a >= 3 then raise too_many_attempts else        
         (
           if (!p_ref = pass) then (r(a); cur_b:= !cur_b+add) 
           else (inc(a); raise wrong_pass)
         )
     );
   print_balance= (fun pass-> 
       if !a >= 3 then raise too_many_attempts else
         (
           if !p_ref = pass then (r(a);!cur_b) 
           else (inc(a); raise wrong_pass)
         )
     )
           
                                              
  }
;;


(* ------------------------------------------------------------------------*)
(* Q 2 : Memoization (75 points)                                           *)
(* ------------------------------------------------------------------------*)

(* Q 2.1 : Counting how many function calls are made *)

let fib_I (n: int) : fib_result = 
  (*work on*)
  let inc (i)= i:= !i+2 in
  let recs= ref 1 in
  let help =
    let rec fib n =  if n <= 1 then n
      else (inc(recs);fib (n-2) + fib (n-1))
    in
    fib n
    
  in  {num_rec= !recs;
       result=help;
      }
;;


(* Q 2.2 : Memoization with a global store *)

let fib_memo (n: int) : int =
  let rec fib n =
    match Hashtbl.find_opt store n with 
    |Some v-> v 
    |None-> (let v= if n<=1 then n 
               else (fib (n-2) + fib (n-1)) 
             in Hashtbl.add store n v; v) 
  in
  fib n
;;


(* Q 2.3 : General memoization function *)

let memo (f: (('a -> 'b) -> 'a -> 'b)) (stats: stats) : ('a -> 'b) = 
  (*use local store*)
  let store  = Hashtbl.create 1000 in
  let inc= fun i-> i:= !i +1 in
  let rec help=
    fun k->
      match Hashtbl.find_opt store k with
      |Some v->(inc(stats.lkp); v)
      |None->(let d = f help k in 
              (Hashtbl.add store k d;inc(stats.entries); d))
  in help 
;;


(* Q 2.4 : Using memo to efficiently compute the Fibonacci number *)
(* We also accept let fibM = raise NotImplemented ;; *)
let fibM  = 
  let stat ={entries= ref 0; lkp= ref 0} in 
  let memo_res = memo (fun fib n->(if n <= 1 then n
                                   else (fib (n-2) + fib (n-1)))) stat in
  fun n-> (memo_res n, stat)
;;
