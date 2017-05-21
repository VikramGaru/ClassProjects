fun pushdict ((a,b) : string*string, (l1,l2)::l : (string*string) list) = if a = l1 then
(a,b)::l
else
[(l1,l2)] @ pushdict ((a,b),l)
|pushdict ((a,b),[]) = (a,b)::[]

fun find (s : string, (a,b)::l : (string*string) list) = if a=s then
                                                true 
                                                else
                                                  find (s,l)
| find (s,[]) = false;

fun popdict (s : string, (a,b)::l : (string*string) list) = if a=s then
                                                 b
                                                else
                                                  popdict (s,l)
| popdict (s,[]) = ":error:";

val dict = [("z","z")]

fun push item (s : string list)         =  (item::s)
  fun pop ( first_item::s : string list) = (first_item, s)
fun isEmpty (s :string list)=
if s=[] then
 true
 else
  false
val v = []
val v = push "\n" v
val v = push "z" v
val v = push "\n" v
val v = push "z" v
val v = push "\n" v
val v = push "z" v

fun is_Digit (n : string)=
let
 fun isDigit (n : string)=
let
  val intnum = valOf(Int.fromString(n))
  val realnum = valOf( Real.fromString(n))
  val realtest = Real.fromInt(intnum)
  
 in
   ( if Real.==(realnum,realtest) then
    true
 else
  false)
end
in
  if n = ":unit:" orelse n = ":true:" orelse n = ":false:" orelse n = ":error:" orelse Char.isAlpha(String.sub(n,0))  then
   false
  else
   isDigit(n)
 end  

  
  fun is_Push (c) = String.isSubstring "push" c;
  fun is_Pop (c) = String.isSubstring "pop" c;
 fun is_Let (c) = String.isSubstring "let" c;
 fun is_End (c) = String.isSubstring "end" c;
  fun is_Add (c) = String.isSubstring "add" c;
  fun is_Sub (c) = String.isSubstring "sub" c;
  fun is_Swap (c) = String.isSubstring "swap" c;
  fun is_Mul (c) = String.isSubstring "mul" c;
  fun is_Div (c) = String.isSubstring "div" c;
  fun is_Rem (c) = String.isSubstring "rem" c;
  fun is_And (c) = String.isSubstring "and" c;
   fun is_Bind (c) = String.isSubstring "bind" c;
   fun is_Less (c) = String.isSubstring "lessThan" c;
   fun is_Equal (c) = String.isSubstring "equal" c;
   fun is_Not (c) = String.isSubstring "not" c;
   fun is_Or (c) = String.isSubstring "or" c;
  fun is_Quit (c) = String.isSubstring "quit" c;
  fun is_True (c) = String.isSubstring ":true:" c;
  fun is_If (c) = String.isSubstring "if" c;
  fun is_False (c) = String.isSubstring ":false:" c;
  fun is_Error (c) = String.isSubstring ":error:" c;
   fun is_Neg (c) = String.isSubstring "neg" c;

fun Push1(no:string,v : string list,dict :(string*string)list)=
     let
       val v = push "\n" v
       val v = push no v

    in
       ("",v,dict)
     end

fun Error ( v : string list,dict : (string*string)list) =
let
 val v = push "\n" v
  val v =push ":error:" v
  
 in
  ("",v,dict)
end

fun Bind (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)

val a1 = find (a,dict)
val b1 = find (b,dict)
val ag = popdict(a,dict)
fun Bind1 (v : string list,n : int,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val ag = popdict(a,dict)
val c = ":unit:"
val v = push c v
val dict =
if n=1 then 
 pushdict ((b,ag),dict)
else
 pushdict ((b,a),dict)
in
("",v,dict)
end
 in
   if (String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL) orelse (String.compare(a,":error:") = EQUAL) then
   Error (v,dict)
   else if (b1=false) then
    Error (v,dict) 
   else if (a1=true) andalso (ag="empty") then
   Error (v,dict)
   else if (a1=true) andalso (ag<>"empty") then
    Bind1(v,1,dict)
   else
    Bind1 (v,0,dict)

end

fun Pushstr(no : string,v :string list, dict : (string*string)list)=
 let 
  val size = String.size(no)
  val str = String.substring(no,0,size+(~1))
in
 Push1(no,v,dict)
end

fun Pushvar(no : string,v :string list, dict : (string*string)list)=
 let
  val dict = pushdict((no,"empty"),dict)
in
 Push1(no,v,dict)
end 

fun Pushneg(no : string,v :string list,dict : (string*string)list)=
 let
  val size = String.size(no)
  val str = String.substring(no,1,size+(~1))
   val num = ~(valOf(Int.fromString(str)))
  val final = Int.toString(num)
in
 Push1(final,v,dict)
end
fun Push (c : string, v : string list,dict : (string*string)list) =
let

  val b = String.size(c) + (~6)
  val d = b + (~1) 
val no = String.substring(c,5,b)
 val size = String.size(no)
val first = String.sub(c,5)
val nocheck = find (no,dict)
val second = if size<2 then
              #"0" 
               else
              String.sub(no,1)

              
  in 
     if ord(first)= 45 then
      Pushneg (no,v,dict)
     else if (Char.isAlpha(first)<>true) andalso (Char.isAlpha(second) = true) then
    Pushstr (no,v,dict)
   else if Char.isAlpha(first) andalso (nocheck = false)  then
     Pushvar (no,v,dict)
  else if Char.isAlpha(first) andalso (nocheck <> false)  then
     Push1 (no,v,dict)
  else if is_Digit(no) then
     Push1 (no,v,dict) 
   else
    Error (v,dict)
end

fun Pop (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)


fun Pop1 (v : string list,dict : (string*string)list) =
let
val (item,v) = pop v
val (item,v) = pop v
 in
  ("",v,dict)
end

 in
  if (String.compare(a,"z") = EQUAL)    then
   Error (v,dict)
   
   else
    Pop1(v,dict)
   
end

fun If (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)
val b = List.nth(v,2)
val c = List.nth(v,4)
val cget = popdict(c,dict)

fun If1 (v : string list,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val (blank,v) = pop v
val (c,v) = pop v
val cget = popdict(c,dict)
val d = if (String.compare(c,":true:")=EQUAL) orelse (String.compare(cget,":true:")=EQUAL) then
          a
         else
          b
val v = push d v
in
("",v,dict)
end
 in
  if ((String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL) orelse (String.compare(c,"z")=EQUAL)) then
   Error (v,dict)
 else if ((String.compare(c,":true:")=EQUAL) orelse (String.compare(c,":false:")=EQUAL) orelse (String.compare(cget,":true:")=EQUAL) orelse (String.compare(cget,":false:")=EQUAL)) then
     If1(v,dict)
     else
      Error(v,dict)

end

fun Or (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)
val aget = popdict(a,dict)
val bget = popdict(b,dict)

fun Or1 (v : string list,s : string,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val av = find (a,dict)
val bv = find (a,dict)
val aget = popdict(a,dict)
val bget = popdict(b,dict)
val v = push s v
in
("",v,dict)

end
 in
  if ((String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL))  then
   Error (v,dict)
   else if ((String.compare(a,":true:") = EQUAL ) orelse (String.compare(aget,":true:") = EQUAL )) andalso ((String.compare(b,":true:") = EQUAL) orelse (String.compare(bget,":true:") = EQUAL))   then
  Or1 (v,":true:",dict)
  
   else if ((String.compare(a,":true:") = EQUAL) orelse (String.compare(aget,":true:") = EQUAL) ) andalso ((String.compare(b,":false:") = EQUAL) orelse (String.compare(bget,":false:") = EQUAL))  then
   Or1 (v,":true:",dict)
   
   
   else if ((String.compare(a,":false:") = EQUAL) orelse (String.compare(aget,":false:") = EQUAL)) andalso ((String.compare(b,":true:") = EQUAL) orelse (String.compare(bget,":true:") = EQUAL))  then
   Or1 (v,":true:",dict)
   else if ((String.compare(a,":false:") = EQUAL) orelse (String.compare(aget,":false:") = EQUAL)) andalso ((String.compare(b,":false:") = EQUAL) orelse (String.compare(bget,":false:") = EQUAL))  then
   Or1 (v,":false:",dict)
   else
    Error (v,dict)

end

fun And (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)
val aget = popdict(a,dict)
val bget = popdict(b,dict)

fun And1 (v : string list,s : string,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v

val v = push s v
in
("",v,dict)
         
end       
 in      
  if ((String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL))  then
   Error (v,dict)
   else if (((String.compare(a,":true:") = EQUAL ) orelse (String.compare(aget,":true:") = EQUAL ) ) andalso ((String.compare(b,":true:") = EQUAL) orelse (String.compare(bget,":true:") = EQUAL)))   then
  And1 (v,":true:",dict) 
   else if (((String.compare(a,":true:") = EQUAL) orelse (String.compare(aget,":true:") = EQUAL))  andalso ((String.compare(b,":false:") = EQUAL) orelse (String.compare(bget,":false:") = EQUAL)))  then
   And1 (v,":false:",dict) 
   else if (((String.compare(b,":true:") = EQUAL) orelse (String.compare(bget,":true:") = EQUAL))  andalso ((String.compare(a,":false:") = EQUAL) orelse (String.compare(aget,":false:") = EQUAL)))  then
   And1 (v,":false:",dict) 
   
   else if (((String.compare(a,":false:") = EQUAL) orelse (String.compare(aget,":false:") = EQUAL)) andalso ((String.compare(b,":true:") = EQUAL) orelse (String.compare(bget,":true:") = EQUAL)))  then
   And1 (v,":false:",dict)
   
   else if (((String.compare(b,":false:") = EQUAL) orelse (String.compare(bget,":false:") = EQUAL)) andalso ((String.compare(a,":true:") = EQUAL) orelse (String.compare(aget,":true:") = EQUAL)))  then
   And1 (v,":false:",dict)
   
   else if (((String.compare(a,":false:") = EQUAL) orelse (String.compare(aget,":false:") = EQUAL) )andalso ((String.compare(b,":false:") = EQUAL) orelse (String.compare(bget,":false:") = EQUAL)))  then
   And1 (v,":false:",dict)
   else
    Error (v,dict)

end

fun Add (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)


fun Add1 (v : string list,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val av = find (a,dict)
val bv = find (a,dict)
val aget=popdict(a,dict)
val bget=popdict(b,dict)
val a1 = if is_Digit(a) then
           valOf(Int.fromString(a))
          else if (av=true) andalso (aget<>"empty") then
          valOf(Int.fromString(popdict(a,dict)))
          else
           99999 
val b1 = if is_Digit(b) then
           valOf(Int.fromString(b)) 
           else if (bv=true) andalso (bget<>"empty") then
          valOf(Int.fromString(popdict(b,dict)))
          else
           99999 
val c1 = if (a1= 99999) orelse (b1= 99999) then
         ":error:"^"\n"^a^"\n"^b
        else  
        Int.toString(a1+b1)
val v = push c1 v
in
("",v,dict)
end
 in
  if (String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL) then
   Error (v,dict) 
   else if (is_Digit (a) orelse Char.isAlpha(String.sub(a,0))) andalso (is_Digit (b) orelse Char.isAlpha(String.sub(b,0))) then
   Add1 (v,dict)
  else   
    Error (v,dict)
          
end 
 
fun Not (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)
val aget = popdict(a,dict)

fun Not1 (v : string list,s : string,dict : (string*string)list) =
let
val (a,v) = pop v
val v = push s v
in
("",v,dict)

end
 in
  if (String.compare(a,"z") = EQUAL)  then
   Error (v,dict)
   else if ((String.compare(a,":true:") = EQUAL) orelse (String.compare(aget,":true:") = EQUAL ))    then
   Not1 (v,":false:",dict)
   else if ((String.compare(a,":false:") = EQUAL) orelse (String.compare(aget,":false:") = EQUAL ) )  then
   Not1 (v,":true:",dict)
   else
    Error (v,dict)

end
 
fun Equal (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)


fun Equal1 (v : string list,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val av = find (a,dict)
val bv = find (a,dict)
val aget=popdict(a,dict)
val bget=popdict(b,dict)
val a1 = if is_Digit(a) then
           valOf(Int.fromString(a))
          else if (av=true) andalso (aget<>"empty") then
          valOf(Int.fromString(popdict(a,dict)))
          else
           99999
val b1 = if is_Digit(b) then
           valOf(Int.fromString(b))
           else if (bv=true) andalso (bget<>"empty") then
          valOf(Int.fromString(popdict(b,dict)))
          else
           99999
val c1 = if (a1= 99999) orelse (b1= 99999) then
         ":error:"^"\n"^a^"\n"^b
        else if (a1 = b1) then
         ":true:"
            else
            ":false:"
        
val v = push c1 v
in
("",v,dict)
end
 in
  if (String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL) then
   Error (v,dict)
   else if (is_Digit (a) orelse Char.isAlpha(String.sub(a,0))) andalso (is_Digit (b) orelse Char.isAlpha(String.sub(b,0))) then
   Equal1 (v,dict)
  else
    Error (v,dict)

end

fun Less (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)


fun Less1 (v : string list,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val av = find (a,dict)
val bv = find (a,dict)
val aget=popdict(a,dict)
val bget=popdict(b,dict)
val a1 = if is_Digit(a) then
           valOf(Int.fromString(a))
          else if (av=true) andalso (aget<>"empty") then
          valOf(Int.fromString(popdict(a,dict)))
          else
           99999
val b1 = if is_Digit(b) then
           valOf(Int.fromString(b))
           else if (bv=true) andalso (bget<>"empty") then
          valOf(Int.fromString(popdict(b,dict)))
          else
           99999
val c1 = if (a1= 99999) orelse (b1= 99999) then
         ":error:"^"\n"^a^"\n"^b
        else if (a1 > b1) then
         ":true:"
            else
            ":false:"
        
val v = push c1 v
in
("",v,dict)
end
 in
  if (String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL) then
   Error (v,dict)
   else if (is_Digit (a) orelse Char.isAlpha(String.sub(a,0))) andalso (is_Digit (b) orelse Char.isAlpha(String.sub(b,0))) then
   Less1 (v,dict)
  else
    Error (v,dict)

end



fun Sub (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)


fun Sub1 (v : string list,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val av = find (a,dict)
val bv = find (a,dict)
val aget=popdict(a,dict)
val bget=popdict(b,dict)
val a1 = if is_Digit(a) then
           valOf(Int.fromString(a))
          else if (av=true) andalso (aget<>"empty") then
          valOf(Int.fromString(popdict(a,dict)))
          else
           99999
val b1 = if is_Digit(b) then
           valOf(Int.fromString(b))
           else if (bv=true) andalso (bget<>"empty") then
          valOf(Int.fromString(popdict(b,dict)))
          else
           99999
val c1 = if (a1= 99999) orelse (b1= 99999) then
         ":error:"^"\n"^a^"\n"^b
        else
        Int.toString(b1-a1)
val v = push c1 v
in
("",v,dict)
end
 in
  if (String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL) then
   Error (v,dict)
   else if (is_Digit (a) orelse Char.isAlpha(String.sub(a,0))) andalso (is_Digit (b) orelse Char.isAlpha(String.sub(b,0))) then
   Sub1 (v,dict)
  else
    Error (v,dict)

end

fun Mul (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)


fun Mul1 (v : string list,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val av = find (a,dict)
val bv = find (a,dict)
val aget=popdict(a,dict)
val bget=popdict(b,dict)
val a1 = if is_Digit(a) then
           valOf(Int.fromString(a))
          else if (av=true) andalso (aget<>"empty") then
          valOf(Int.fromString(popdict(a,dict)))
          else
           99999
val b1 = if is_Digit(b) then
           valOf(Int.fromString(b))
           else if (bv=true) andalso (bget<>"empty") then
          valOf(Int.fromString(popdict(b,dict)))
          else
           99999
val c1 = if (a1= 99999) orelse (b1= 99999) then
         ":error:"^"\n"^a^"\n"^b 
        else
        Int.toString(b1*a1)
val v = push c1 v
in
("",v,dict)
end
 in
  if (String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL) then
   Error (v,dict)
   else if (is_Digit (a) orelse Char.isAlpha(String.sub(a,0))) andalso (is_Digit (b) orelse Char.isAlpha(String.sub(b,0))) then
   Mul1 (v,dict)
  else
    Error (v,dict)

end

fun Div (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)


fun Div1 (v : string list,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val av = find (a,dict)
val bv = find (a,dict)
val aget=popdict(a,dict)
val bget=popdict(b,dict)
val a1 = if is_Digit(a) then
           valOf(Int.fromString(a))
          else if (av=true) andalso (aget<>"empty") then
          valOf(Int.fromString(popdict(a,dict)))
          else
           99999
val b1 = if is_Digit(b) then
           valOf(Int.fromString(b))
           else if (bv=true) andalso (bget<>"empty") then
          valOf(Int.fromString(popdict(b,dict)))
          else
           99999
val c1 = if (a1= 99999) orelse (b1= 99999) then
         ":error:"^"\n"^a^"\n"^b
        else
        Int.toString(b1 div a1)
val v = push c1 v
in
("",v,dict)
end
 in
  if (String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL) then
   Error (v,dict)
   else if (is_Digit (a) orelse Char.isAlpha(String.sub(a,0))) andalso (is_Digit (b) orelse Char.isAlpha(String.sub(b,0))) then
   Div1 (v,dict)
  else
    Error (v,dict)

end

fun Rem (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)


fun Rem1 (v : string list,dict : (string*string)list) =
let
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val av = find (a,dict)
val bv = find (a,dict)
val aget=popdict(a,dict)
val bget=popdict(b,dict)
val a1 = if is_Digit(a) then
           valOf(Int.fromString(a))
          else if (av=true) andalso (aget<>"empty") then
          valOf(Int.fromString(popdict(a,dict)))
          else
           99999
val b1 = if is_Digit(b) then
           valOf(Int.fromString(b))
           else if (bv=true) andalso (bget<>"empty") then
          valOf(Int.fromString(popdict(b,dict)))
          else
           99999
val c1 = if (a1= 99999) orelse (b1= 99999) then
         ":error:"^"\n"^a^"\n"^b
        else
        Int.toString(b1 mod a1)
val v = push c1 v
in
("",v,dict)
end
 in
  if (String.compare(a,"z") = EQUAL) orelse (String.compare(b,"z") = EQUAL) then
   Error (v,dict)
   else if (is_Digit (a) orelse Char.isAlpha(String.sub(a,0))) andalso (is_Digit (b) orelse Char.isAlpha(String.sub(b,0))) then
   Rem1 (v,dict)
  else
    Error (v,dict)

end

fun Swap (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

val b = List.nth(v,2)


fun Swap1 (v : string list,dict : (string*string)list) =
let 
val (a,v) = pop v
val (blank,v) = pop v
val (b,v) = pop v
val v = push a v
val v = push "\n" v
val v = push b v
in
("",v,dict)
end
 in
  if ((String.compare(a,"z") = EQUAL) andalso (String.compare(b,"z") = EQUAL))  then
   Error (v,dict)
   else if (String.compare(a,"z") <> EQUAL) andalso (String.compare(b,"z") = EQUAL)  then
   Error (v,dict)
   else
    Swap1(v,dict)
   
end


fun Neg (v : string list,dict : (string*string)list) =
let
val a = List.nth(v,0)

fun Neg1 (v : string list,dict : (string*string)list) =
let
val (a,v) = pop v
val av = find (a,dict)

val aget=popdict(a,dict)

val a1 = if is_Digit(a) then
           valOf(Int.fromString(a))
          else if (av=true) andalso (aget<>"empty") then
          valOf(Int.fromString(popdict(a,dict)))
          else
           99999

val c1 = if (a1= 99999) then
         ":error:"^"\n"^a
        else
        Int.toString(~a1)
val v = push c1 v
in
("",v,dict)
end
 in
  if (String.compare(a,"z") = EQUAL) then
   Error (v,dict)
   else if is_Digit (a) orelse Char.isAlpha(String.sub(a,0))  then
   Neg1 (v,dict)
  else
    Error (v,dict)

end
fun stringneg [] = []
|stringneg(h::t)=
           if String.sub(h,0) = #"~" then
 let
 val c = String.substring(h,1,String.size(h)+(~1))
 val d = "-"^c
in
 [d] @ stringneg(t)
end
else
            [h] @ stringneg(t);

fun stringquote [] = []
|stringquote(h::t)=  if String.isSubstring"\"" h then
 [String.substring(h,1,String.size(h)+(~2))] @ stringquote(t)
else  
            [h] @ stringquote(t); 

fun Quit (v : string list,dict : (string*string)list) =
 let
 val v = stringneg v
 val v = stringquote v 
 val final = concat v 
  val b = String.size(final) + (~6)
val c = String.substring(final,0,b)
   
 in
    (c,v,dict)
end




fun Let ( v : string list,dict : (string*string)list) =
let
 val v = push "\n" v 
 val v =push "py" v
 in
  ("",v,dict)
end

fun End (v : string list, dict : (string*string)list)=
let
val (a,v) = pop v
val (blank,v) = pop v


fun End1 (v : string list , dict : (string*string)list,a : string)=
 let
    val (b,v) = pop v
    val (blank,v) = pop v
 in
   if b <> "py" then
    End1(v,dict,a)
    else
     Push1(a,v,dict)
  end

in
    End1(v,dict,a)
end  

fun T (c : string, v : string list,dict : (string*string)list) =
let
 val b = String.size(c) + (~1)
val c = String.substring(c,0,b)
 val v = push "\n" v
  val v =push c v
 in
  ("",v,dict)
end

fun F (c : string, v : string list,dict : (string*string)list) =
let
 val b = String.size(c) + (~1)
val c = String.substring(c,0,b)
val v = push "\n" v
  val v =push c v
 in
  ("",v,dict)
end


fun main (line : string, v : string list,dict : (string*string)list) =

   if is_Push (line) then
     Push (line,v,dict)
    else if is_Pop line then
     Pop (v,dict)
     else if is_Add line then
     Add (v,dict)
     else if is_Sub line then
     Sub (v,dict)
     else if is_Div line then
     Div (v,dict)
     else if is_Mul line then
     Mul (v,dict)
     else if is_Rem line then
     Rem (v,dict)
    else if is_Neg line then
     Neg (v,dict) 
    else if is_Or line then
     Or (v,dict)
    else if is_Bind line then
     Bind (v,dict)
     else if is_And line then
     And (v,dict)
     else if is_Equal line then
      Equal (v,dict)
      else if is_Less line then
     Less (v,dict)
     else if is_Not line then
     Not (v,dict)
      else if is_If line then
     If (v,dict)
     else if is_Quit line then
      Quit(v,dict)
     else if is_Error line then
     Error (v,dict)
     else if is_Let line then
     Let (v,dict)
     else if line ="" then
      ("",v,dict)
      else if is_Swap line then
      Swap(v,dict)
      else if is_End line then
       End(v,dict)
      
     else if is_True line then
      T (line,v,dict)
      else
       F (line,v,dict)
      

fun hw3(inputFile : string, outputFile : string) =
let
        val inStream = TextIO.openIn inputFile
        val outStream = TextIO.openOut outputFile
        val readLine = TextIO.inputLine inStream
       

        fun helper(readLine : string option , v :string list,dict : (string*string)list) =
        case readLine of
         NONE=>(TextIO.closeIn inStream; TextIO.closeOut outStream)
         |SOME(a)=>
         (let
          
         
         val (item,v,dict) = main(valOf(readLine),v,dict)
         
         in
                                (TextIO.output(outStream,item);helper(TextIO.inputLine inStream,v,dict))                     
          end)


in
        helper(readLine,v,dict)
end

val _ = hw3("input.txt","output.txt")
