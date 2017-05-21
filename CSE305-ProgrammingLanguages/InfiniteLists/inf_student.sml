datatype 'a inflist = NIL
                    | CONS of 'a * (unit -> 'a inflist);

exception Empty;
exception Subscript;

fun HD (CONS(a,b)) = a
  | HD NIL = raise Empty; 

fun TL (CONS(a,b)) = b()
  | TL NIL = raise Empty;

fun NUL NIL = true
  | NUL _ = false;

fun NTH 0 L = HD L
  | NTH n L = NTH (n-1) (TL L);

fun TAKE (xs, 0) = []
  | TAKE (NIL, n) = raise Subscript
  | TAKE (CONS(x, xf), n) = x::TAKE(xf(), n-1);

fun FROMN n = CONS(n, fn () => FROMN (n+1));

fun FIB n m = CONS(n, fn () => FIB m (n+m));

fun STUB _ = CONS(0, fn () => STUB 0)

fun FILTER f l =
  if NUL l
  then NIL
  else if f (HD l)
       then CONS(HD l, fn() => (FILTER f (TL l)))
       else FILTER f (TL l);

fun SIFT NIL = NIL
  | SIFT l =
     let val a = HD l
     in CONS(a, fn () => SIFT(FILTER (fn x => x mod a <> 0) (TL l)))
     end;

	 
(**********************
 *
 * FUNCTION AND INFLIST STUBS -- YOU MUST IMPLEMENT THESE
 * 
 * printList and printPairList must write to the file named by f.
 * Anything printed to the terminal will not be graded.
 *
 **********************)
 
fun even (x : int) = 
if x mod 2 = 0 then true
else false

fun odd  (x : int) =
if x mod 2 = 0 then false
else true

val fibs     = FIB 0 1
val evenFibs = FILTER even fibs
val oddFibs  = FILTER odd fibs

fun printGenList (f : ('a -> 'b)) (l : ('a list)) : unit = ()

fun printList (f : string, l : int list)=
let
val outStream = TextIO.openOut f
fun helper([])= 
TextIO.closeOut outStream
   |  helper(x::xs) = (TextIO.output(outStream, (Int.toString x)^" ");helper(xs))
in
helper(l)
end

fun printPairList (f : string, l : (int * int) list)=
let
val outStream = TextIO.openOut f
fun helper([])= 
TextIO.closeOut outStream
   |  helper((a,b)::xs) = (TextIO.output(outStream, "("^(Int.toString a)^", "^(Int.toString b)^") ");helper(xs))
in
helper(l)
end

fun ZIP (infL1 : 'a inflist, infL2 : 'b inflist)=
let
val a= HD infL1
val b= HD infL2
val c=(a,b)
in
CONS(c, fn () => ZIP(TL infL1, TL infL2))
end