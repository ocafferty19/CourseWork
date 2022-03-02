let unused_vars_tests = [
  (* An example test case.
     Note that you are *only* required to write tests for Rec, Fn, and Apply!
  *)
  (Rec("x", Bool, If(Var "y", Var "z", Var"x")), []);(*1*)
  (Rec("x", Bool, If(Var "y", Var "z", Var"x1")), ["x"]);(*2*)
                                                         
  (Rec("x", Arrow([Arrow([Int],Int); Bool; Int], Int), I 1), ["x"]);(*Rec of (name * tp * exp) *)
 
                                                                    
  (Apply(Rec("x", Int,I 1), [I 1; I 2; I 3]), ["x"]);(*passes 5*)
                                                     
  (Fn([("x", Int); ("y", Int)], Rec("z", Bool, If(Var "y", Var "z", Var"x"))) ,[]);
  (Fn([("q", Int); ("y", Int)], Rec("z", Bool, If(Var "y", Var "z", Var"x"))) ,["q"]);
  (Fn([("x", Int); ("x", Int)],I 1) ,["x";"x"]);
  (Fn(["x", Int],I 1) ,["x"]);(*passes 4*)
  
  
  
  
]

(* TODO: Implement the missing cases of unused_vars. *)
let rec unused_vars =
  function
  | Var _ | I _ | B _ -> []
  | If (e, e1, e2) -> unused_vars e @ unused_vars e1 @ unused_vars e2
  | Primop (_, args) ->
      List.fold_left (fun acc exp -> acc @ unused_vars exp) [] args
  | Let (x, e1, e2) ->
      let unused = unused_vars e1 @ unused_vars e2 in
      if List.mem x (free_variables e2) then
        unused
      else
        x :: unused

  | Rec (x, _, e) ->
      let unused = unused_vars e in
      if List.mem x (free_variables e) then
        unused
      else
        x :: unused

  | Fn (xs, e) ->
      
      let unused = unused_vars e in
      let names= List.fold_right (fun (name,tp) acc->name::acc) xs [] in
      List.fold_left (fun res x-> if List.mem x (free_variables e) then res else x::res) unused names
        
                    (* Fn of ((name * tp) list * exp) *)
  | Apply (e, es) -> 
      let rec helper l=
        match l with
        |e::tl->(unused_vars e)@ helper tl
        |_->[]
      in
      (unused_vars e)@(helper es)
(* TODO: Write a good set of tests for subst. *)
(* Note: we've added a type annotation here so that the compiler can help
   you write tests of the correct form. *)
let subst_tests : (((exp * name) * exp) * exp) list = [
  (* An example test case. If you have trouble writing test cases of the
     proper form, you can try copying this one and modifying it.
     Note that you are *only* required to write tests for Rec, Fn, and Apply!
  *)
  (((I 1, "x"), (* [1/x] *)
    (* let y = 2 in y + x *)
    Let ("y", I 2, Primop (Plus, [Var "y"; Var "x"]))),
   (* let y = 2 in y + 1 *)
   Let ("y", I 2, Primop (Plus, [Var "y"; I 1])));
  (((I 1, "x"),(Rec("y", Int, Var "x"))), Rec("y", Int, I 1));(*1*)
  (((I 1, "x"),(Rec("x", Int, Var "x"))), Rec("x", Int, Var "x"));(*2*) 
  (((Var "override", "old"),(Rec("override", Int, Var "old"))), Rec("overriden", Int, Var "override")); (*3 weird*)(*replaces name of existing name that matches subbed name*)
  (((Rec("x", Int, Var "x"), "z"), (Rec("x", Int, Var "x"))), Rec("x", Int, Var "x"));(*weird*)
  ((((I 1),"x"), Fn(["y",Int], Var "x")), Fn(["y",Int], I 1)); (*4*)    
  ((((I 1),"x"), Fn(["x",Int], Var "x")), Fn(["x",Int], Var "x")); (*5*) 
  (((Var "override", "old"), Fn(["override", Int], Var "old")), Fn(["overriden", Int], Var "override"));(*6*)                                                           
  (((I 1, "x"),Apply(Var "x", [Var "x"])), Apply(I 1, [I 1]));(*7*)
  (((I 1, "x"),Apply(Var "x", [Var "x"; Var "x"])), Apply(I 1, [I 1; I 1]));(*7*)                                      
]

(* TODO: Implement the missing cases of subst. *)
let rec subst ((e', x) as s) exp =
  match exp with
  | Var y ->
      if x = y then e'
      else Var y
  | I n -> I n
  | B b -> B b
  | Primop (po, args) -> Primop (po, List.map (subst s) args)
  | If (e, e1, e2) ->
      If (subst s e, subst s e1, subst s e2)
  | Let (y, e1, e2) ->
      let e1' = subst s e1 in
      if y = x then
        Let (y, e1', e2)
      else
        let (y, e2) =
          if List.mem y (free_variables e') then
            rename y e2
          else
            (y, e2)
        in
        Let (y, e1', subst s e2)
  | Rec (y, t, e) when y=x-> Rec(y,t,e)
  | Rec (y, t, e) -> 
      let (y, e) =
        if List.mem y (free_variables e') then
          rename y e
        else
          (y, e)
      in Rec(y, t, subst s e)
        
        

  | Fn (xs, e) ->
      
      let names= List.fold_right(fun (name, tp) acc -> name::acc) xs []; in 
      if (List.mem x names) then
        Fn(xs, e)
      else
        let freeVars= List.fold_left (fun acc name->if List.mem name (free_variables e') then name::acc else acc) [] names in 
        if freeVars=[] then 
          Fn(xs, subst s e)
        else
          let tps= List.fold_right(fun (name, tp) acc -> tp::acc) xs []
          in
          let (nN,nE)= rename_all names e in 
          Fn((List.combine nN tps),subst s nE)
            
  | Apply (e, es) ->
      let x= List.fold_right (fun  el acc-> (subst s el)::acc) es []  in
      Apply(subst s e, x)

and rename x e =
  let x' = freshVar x in
  (x', subst (Var x', x) e)

and rename_all names exp =
  List.fold_right
    (fun name (names, exp) ->
       let (name', exp') = rename name exp in
       (name' :: names, exp'))
    names
    ([], exp)

(* Applying a list of substitutions to an expression, leftmost first *)
let subst_list subs exp =
  List.fold_left (fun exp sub -> subst sub exp) exp subs


(* TODO: Write a good set of tests for eval. *)
let eval_tests = [
  (* An example test case.
     Note that you are *only* required to write tests for Rec and Apply!
  *)
  (Let ("x", I 1, Primop (Plus, [Var "x"; I 5])), I 6);
  (Rec("x", Int, I 1), I 1);(*1*)
  (Rec("x", Bool, B true), B true);
  (Apply(Fn([("x", Int); ("y",Int)], I 1), [I 1; I 1]), I 1);(*3*)
  (Apply(Fn(["x", Int], I 1), [I 1]), I 1);(*2*)
  (Apply(Fn(["x", Int], (Fn(["x", Int], I 1))), [I 1]), Fn(["x", Int], I 1)); (*can have same name*)                                    
  (Apply(Fn([], I 1), []), I 1);(*4*)
  (Apply(Fn([("x", Int); ("y",Int)], Primop(Equals, [Var "x"; Var"y"])), [I 1; I 1]), B true);(*5*)
  (Apply(Rec("x", Int, Fn(["x", Int], Var "x")), [I 1]), I 1) ;(*6*)(*can have same name*)
                                                                    
  (*[variable must be not be free?, Stuck(Free_variable "y")]*)
  (*(Apply(I 1, [I 1]), I 1) results in (Stuck Apply_non_fn)*)

]

(* TODO: Implement the missing cases of eval. *)
let rec eval exp =
  match exp with
  (* Values evaluate to themselves *)
  | I _ -> exp
  | B _ -> exp
  | Fn _ -> exp

  (* This evaluator is _not_ environment-based. Variables should never
     appear during evaluation since they should be substituted away when
     eliminating binding constructs, e.g. function applications and lets.
     Therefore, if we encounter a variable, we raise an error.
*)
  | Var x -> raise (Stuck (Free_variable x))

  (* Primitive operations: +, -, *, <, = *)
  | Primop (po, args) ->
      let args = List.map eval args in
      begin
        match eval_op po args with
        | None -> raise (Stuck Bad_primop_args)
        | Some v -> v
      end

  | If (e, e1, e2) ->
      begin
        match eval e with
        | B true -> eval e1
        | B false -> eval e2
        | _ -> raise (Stuck If_non_true_false)
      end

  | Let (x, e1, e2) ->
      let e1 = eval e1 in
      eval (subst (e1, x) e2)

  | Rec (f, _, e) ->
      
      (
        match eval e with
        |Fn(xs, e1)-> eval(subst(exp,f) (eval e))
        | _-> eval (eval e) 
      )

  | Apply (e, es) -> 
      
      match eval e with
      |Fn(xs, e')->
          let names= List.fold_left (fun  acc (name,tp)->name::acc)  [] xs in
          let exps= List.fold_left (fun acc b -> (eval b)::acc)  [] es in 
          if List.length names = List.length exps then
            let subs= List.combine exps names in 
            eval (List.fold_right (fun sub exp -> subst sub exp) subs e')(*so i didnt need to rev names and exps*)
          else
            raise(Stuck Arity_mismatch)
      |_-> eval (eval e)

(* TODO: Write a good set of tests for infer. *)
let infer_tests = [
  (* An example test case.
     Note that you are *only* required to write tests for Rec, Fn, and Apply!
  *)
  (([("x", Int)], Var "x"), Int);(*return type*)
  (([("x", Bool)], Fn([], Var "x")), Arrow([], Bool));
  
  (([("x", Bool)], Fn(["var", Int], Var "x")), Arrow([Int], Bool));
  (([("x", Bool)], Fn([("var", Int);("::", Bool)], Var "x")), Arrow([Int;Bool], Bool));
  ((["x", Bool], ex1), Arrow([Int;Int],Int));(*?*)
  (([("x", Bool)], Apply(Fn([], Var "x"),[])),Bool);
  (([("x", Bool)], Apply(Fn(["var", Bool], Var "x"),[B true])),Bool);
  (([("x", Bool)], Apply(Fn([("var",Bool);("::", Bool)], Var "x"),[B true; B false])),Bool);
  (([("x", Int)], Apply(Fn([("var",Bool);("::", Bool)], Var "x"),[B true; B false])),Int);
  (([("x", Bool)], Apply(Fn([("var",Int);("::", Bool)], Var "x"),[I 1; B false])),Bool);(*???*)
  (([("x", Bool)], Apply(Fn([("var",Bool);("::", Int)], Var "x"),[B false; I 1])),Bool);(*?? only cares about tp of [name, tp]*)
  (([("x", Int)], Apply(Fn([("var",Bool);("::", Bool)], Var "x"),[B true; B false])),Int);(*^ yes?*)
  (([("x", Bool)], Rec("n", Bool, Var"x")), Bool); 
  (([("x", Bool)], Rec("n", Bool, B true)), Bool);
  (([("x", Bool)],Rec("n", Bool, If(Var "x", B true, B true))), Bool);
  ((["x", Int], Rec("n",Int, Primop(Negate,([Var "x"])))),Int);
  ((["x", Int], Rec("n",Int, Primop(Plus,([Var "x"; Var "n"])))),Int)
  (*([("x", Bool)], Rec("n", Int, Var"x")), Bool) typeMismatch*)
   (*type mismatch(([("x", Bool)], Apply(Fn(["var", Int], Var "x"),[B true])),Bool)*)
]

(* TODO: Implement the missing cases of infer. *)
let rec infer ctx e =
  match e with
  | Var x ->
      begin
        try lookup x ctx
        with Not_found -> raise (TypeError (Free_variable x))
      end
  | I _ -> Int
  | B _ -> Bool

  | Primop (po, exps) ->
      let (domain, range) = primopType po in
      check ctx exps domain range

  | If (e, e1, e2) ->
      begin
        match infer ctx e with
        | Bool ->
            let t1 = infer ctx e1 in
            let t2 = infer ctx e2 in
            if t1 = t2 then t1
            else type_mismatch t1 t2
        | t -> type_mismatch Bool t
      end

  | Let (x, e1, e2) ->
      let t1 = infer ctx e1 in
      infer (extend ctx (x, t1)) e2

  | Rec (f, t, e) ->
      if (infer (extend ctx (f, t)) e) = t then
        t 
      else type_mismatch t (infer (extend ctx (f, t)) e) 
        

  | Fn (xs, e) -> 
      let rec helper l=
        match l with
        |(name, tp)::tl when List.mem (name) (free_variables e) ->(name,tp)::(helper tl )
        |(name, tp)::tl->helper tl
        |_->[]
      in
      let free= List.rev(helper xs) in
      let tps=List.fold_right(fun  (name, tp) acc->tp::acc)  xs [] in
      Arrow(tps, infer(extend_list ctx free) e)

  | Apply (e, es) -> 
      raise NotImplemented
    

and check ctx exps tps result =
  match exps, tps with
  | [], [] -> result
  | e :: es, t :: ts ->
      let t' = infer ctx e in
      if t = t' then check ctx es ts result
      else type_mismatch t t'
  | _ -> raise (TypeError Arity_mismatch)

(* TODO: Implement type unification. *)
let rec unify (t1 : utp) (t2 : utp) : unit =
  match t1, t2 with
  (* unifying identical concrete types does nothing *)
  | UInt, UInt
  | UBool, UBool -> ()
  (* For type constructors, recursively unify the parts *)
  | UArrow (t1, t1'), UArrow (t2, t2') ->
      unify t1 t2;unify t1' t2'
  | UTVar a, _ -> unifyVar a t2
  | _, UTVar b -> unifyVar b t1
  (* All other cases are mismatched types. *)
  | _, _ -> unif_error @@ UnifMismatch (t1, t2)

(* Unify a variable with a type *)
and unifyVar a t =
  match !a with
  |Some(v)-> unify v t
               
  |None->
      match t with
      |UTVar b ->
          (match !b with
           |Some (bv)-> unify (UTVar a) bv
           |None when !b == !a-> ()
           |None->a:=Some(UTVar b)
          )
      |_->if occurs a t then
            raise (unif_error UnifOccursCheckFails) else a:=Some(t)
        
     
