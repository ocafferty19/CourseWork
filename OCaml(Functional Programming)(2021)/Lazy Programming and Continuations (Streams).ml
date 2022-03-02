(* TODO: Write some tests for neighbours. Consider creating a graph first,
 and then writing your tests based on it *)

(* Reminder: If a test case requires multiple arguments, use a tuple:
let myfn_with_2_args_tests = [
  ((arg1, arg1), (expected_output))
]
*)
let rec all_nodes v =
  match v with
  |(n,w)::tl -> n::(all_nodes tl)
  |[]-> []
        
let rec all_weight v =
  match v with
  |(n,w)::tl -> w+(all_weight tl)
  |[]-> 0
        
(* We've added a type annotation here so that the compiler can help
   you write tests of the correct form. *)
let midW={nodes=["Michigan";"Ohio";"Indiana";"Florida";"Wisconsin"];
          edges=[("Michigan", "Ohio", 2);("Michigan", "Indiana", 3);("Michigan", "Wisconsin", 2);("Ohio", "Indiana",2)] }
         
let neighbours_tests: ((string graph * string) * (string * weight) list) list = [
  (midW, "Michigan"), ["Ohio",2; "Indiana",3;"Wisconsin",2];(*not ordered by weight*)
  (midW, "Michigan"), [ "Indiana",3;"Wisconsin",2;"Ohio",2];(*order does not matter*)
  (midW, "Florida"), [];
  (midW, "Indiana"), [];(*does not go backwards only neighbors one way*)
  (midW, "Ohio"), ["Indiana",2];
  
  
]

(* TODO: Implement neighbours. *)
let neighbours (g: 'a graph) (vertex: 'a): ('a * weight) list  =
  List.fold_left 
    (fun acc f->
       match f with 
       |(x,y,z) when x = vertex-> (y, z)::acc 
       |_-> acc
    )
    [] g.edges

(* TODO: Implement find_path. *)
let find_path (g: 'a graph) (a: 'a) (b: 'a) : ('a list * weight) =
  
  let rec aux_node (node: 'a * weight) (visited:('a*int) list) = 
    let (n,w)= node in 
    if not(List.mem(n) (all_nodes visited)) then  aux_list (neighbours g n) (node::visited)
    else raise Fail
  and aux_list (nodes: ('a * weight) list) (visited)  = 
    match nodes with
    |(n,w)::tl when n=b ->  let (x,y)= (all_nodes ((n,w)::visited), (all_weight ((n,w)::visited))) in (List.rev(x),y)
    |node::tl->(try aux_node node visited with Fail-> aux_list tl visited)
    |_-> raise Fail
  in
  aux_node (a, 0) []

(* TODO: Implement find_path'. *)
let find_path' (g: 'a graph) (a: 'a) (b: 'a) : ('a list * weight) =
  
  let rec aux_node (node: 'a * weight) (visited:('a*int) list) fc sc=
    let (n,w)=node in
    if not(List.mem(n) (all_nodes visited)) then aux_list (neighbours g n) (node::visited) fc sc
    else fc sc
  and aux_list (nodes: ('a * weight) list) (visited) fc sc  = 
    match nodes with
    |(n,w)::tl when n=b ->  let (x,y)= (all_nodes ((n,w)::visited), (all_weight ((n,w)::visited))) in sc(List.rev(x),y)
    |n::tl-> aux_node n visited (fun _ -> aux_list tl visited fc sc) sc
    |_-> fc sc
  in
  aux_node (a, 0) [] (fun _->raise Fail) (fun n->n)
    


(* TODO: Implement find_all_paths *)
let find_all_paths (g: 'a graph) (a: 'a) (b: 'a) : ('a list * weight) list =
  
  let rec aux_node (node: 'a * weight) (visited:('a*int) list) = 
    match node with
    |(n,w) when n=b ->  let (x,y)= (all_nodes ((n,w)::visited),  (all_weight ((n,w)::visited))) in [(List.rev(x),y)](*done up here in this iteration because*)
    |(n,w) when (not(List.mem(n) (all_nodes visited))) ->  aux_list (neighbours g n) (node::visited) 
    |_->[]
  and aux_list (nodes: ('a * weight) list) (visited)  = 
    match nodes with 
    |n::tl-> (aux_node n visited) @ (aux_list tl visited)
    |_-> []
  in
  aux_node (a, 0) [] 


(* TODO: Implement find_shortest_path *)
let find_shortest_path (g: 'a graph) (a: 'a) (b: 'a) : ('a list * weight) option =
  let y:('a list * weight) list =find_all_paths g a b in
  let rec max (l:('a list * weight) list) (v:weight) :('a list * weight) list =
    match l with 
    |(n,w)::tl when w<v->  (n,w)::(max tl w)
    |p::tl ->  (max tl v)
    |[]->[] 
         
  in match List.fold_left (fun x z ->max x 20000) y [] with 
  |[]-> None
  |(n::tl)->Some(n)
    
         
  

(* ---------- Hamming Numbers ----------- *)

let rec merge (s1:'a str ) (s2:'a str ) =
  (*s1 and s2 are increasing, output is also increasing*)
  if s1.hd<s2.hd then
    {hd= s1.hd;
     tl= Susp(fun()-> merge (force s1.tl) (s2))}
  else if s1.hd=s2.hd then 
    {hd= s1.hd;
     tl= Susp(fun()-> merge (force s1.tl) (force s2.tl))}
  else
    {hd= s2.hd;
     tl= Susp(fun()-> merge (s1) (force s2.tl))}
let rec hamming_series =
  (*fix*)
  {hd=1;
   tl=Susp(fun()-> merge(times 2 hamming_series) (merge(times 3 hamming_series) (times 5 hamming_series)))
  }
  
  
