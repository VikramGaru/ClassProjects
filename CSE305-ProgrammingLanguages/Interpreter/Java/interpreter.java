import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
//ioFunc stack to be completed in let..end
public class interpreter {
	Stack<Stack<Object>> frame; //Contains the various stacks of let..end
	Stack<HashMap<String,Object>> scope; //Stack of variables and values
	Stack<Object> stack; //Contains the commands and results of them
	HashMap<String, Object> map; //Contains variables and values
	//HashSet<Object> stringPool;
	Stack<Integer> check;
	Stack<Stack<Integer>> vars; //Stack of all check/variable positions
	HashSet<String> reserved; //Set of reserved words
	HashMap<String,ArrayList<Object>> functions; //Set of function name to commands in the function
	HashMap<String,ArrayList<Object>> ioFunctions; //Set of function name to commands in the in out function
	Stack<HashMap<String,ArrayList<Object>>> funcScope; //Stack of the scope of function
	Stack<HashMap<String,ArrayList<Object>>> ioFunS; //Scope if ioFunctions
	public interpreter(String input, String output){
		stack=new Stack<Object>();
		map=new HashMap<String, Object>();
		//stringPool=new HashSet<Object>();
		frame=new Stack<Stack<Object>>();
		scope=new Stack<HashMap<String,Object>>();
		vars=new Stack<Stack<Integer>>();
		frame.push(stack);
		scope.push(map);
		check=new Stack<Integer>();
		vars.push(check);
		functions=new HashMap<String,ArrayList<Object>>();
		funcScope=new Stack<HashMap<String,ArrayList<Object>>>();
		funcScope.push(functions);
		ioFunctions=new HashMap<String,ArrayList<Object>>();
		ioFunS=new Stack<HashMap<String,ArrayList<Object>>>();
		ioFunS.push(ioFunctions);
		reserved=new HashSet<String>();
		reserved.add("push");
		reserved.add("pop");
		reserved.add(":true:");
		reserved.add(":false:");
		reserved.add(":error:");
		reserved.add(":unit:");
		reserved.add("add");
		reserved.add("mul");
		reserved.add("div");
		reserved.add("rem");
		reserved.add("neg");
		reserved.add("swap");
		reserved.add("and");
		reserved.add("or");
		reserved.add("not");
		reserved.add("equal");
		reserved.add("lessThan");
		reserved.add("bind");
		reserved.add("if");
		reserved.add("let");
		reserved.add("end");
		reserved.add("fun");
		reserved.add("funEnd");
		reserved.add("return");
		reserved.add("inOutFun");
		reserved.add("quit");
		try{
			Scanner scanner = new Scanner(new File(input));
			while (true){
			  String line = scanner.nextLine();
			  String[] a=line.split(" ");
			  if(a[0].equals("push")){
				  String s=a[1];
				  for(int i=2;i<a.length;i++){
					  s=s+" "+a[i];
				  }
				  Object o=s;
				  push(o);
			  }
			  else if(a[0].equals("pop")){
				  pop();
			  }
			  else if(a[0].equals(":true:")||a[0].equals(":false:")){
				  Object o=a[0];
				  pushBool(o);
			  }
			  else if(a[0].equals(":error:")){
				  err();
			  }
			  else if(a[0].equals("add")){
				  add();
			  }
			  else if(a[0].equals("sub")){
				  sub();
			  }
			  else if(a[0].equals("mul")){
				  mul();
			  }
			  else if(a[0].equals("div")){
				  div();
			  }
			  else if(a[0].equals("rem")){
				  rem();
			  }
			  else if(a[0].equals("neg")){
				  neg();
			  }
			  else if(a[0].equals("swap")){
				  swap();
			  }
			  else if(a[0].equals("and")){
				  and();
			  }
			  else if(a[0].equals("or")){
				  or();
			  }
			  else if(a[0].equals("not")){
				  not();
			  }
			  else if(a[0].equals("equal")){
				  equal();
			  }
			  else if(a[0].equals("lessThan")){
				  lessThan();
			  }
			  else if(a[0].equals("bind")){
				  bind();
			  }
			  else if(a[0].equals("if")){
				  iF();
			  }
			  else if(a[0].equals("let")){
				  let();
			  }
			  else if(a[0].equals("end")){
				  end();
			  }
			  else if(a[0].equals("fun")){
				  int i=fun(a[1],a[2]);
				  int count=0;
				  if(i==1){
					  functions.get(a[1]).add(a[2]);
					  while(true){
						  String s=scanner.nextLine();
						  String[]b=s.split(" ");
						  if(b[0].equals("fun")||b[0].equals("inOutFun")){
							  count=count+1;
						  }
						  if(b[0].equals("funEnd")){
							  if(count==0){
								  break;
							  }
							  else{
								  functions.get(a[1]).add(s);
								  count=count-1;
							  }
						  }
						  else{
							  functions.get(a[1]).add(s);
						  }
					  }
					  HashMap<String,Object> ob=new HashMap<String,Object>();
					  Iterator<String> it=map.keySet().iterator();
					  while(it.hasNext()){
						  String s=it.next();
						  ob.put(s,map.get(s));
					  }
					  functions.get(a[1]).add(ob);
				  }
				  else{
					  while(true){
						  String s=scanner.nextLine();
						  String[]b=s.split(" ");
						  if(b[0]=="funEnd"){
							  break;
						  }
					  }
				  }
			  }
			  else if(a[0].equals("inOutFun")){
				  int i=inOutFun(a[1],a[2]);
				  if(i==1){
					  int count=0;
					  ioFunctions.get(a[1]).add(a[2]);
					  while(true){
						  String s=scanner.nextLine();
						  String[]b=s.split(" ");
						  if(b[0].equals("fun")||b[0].equals("inOutFun")){
							  count=count+1;
						  }
						  if(b[0].equals("funEnd")){
							  if(count==0){
								  break;
							  }
							  else{
								  ioFunctions.get(a[1]).add(s);
								  count=count-1;
							  }
						  }
						  else{
							  ioFunctions.get(a[1]).add(s);
						  }
					  }
					  HashMap<String,Object> ob=new HashMap<String,Object>();
					  Iterator<String> it=map.keySet().iterator();
					  while(it.hasNext()){
						  String s=it.next();
						  ob.put(s,map.get(s));
					  }
					  ioFunctions.get(a[1]).add(ob);
				  }
				  else{
					  while(true){
						  String s=scanner.nextLine();
						  String[]b=s.split(" ");
						  if(b[0]=="funEnd"){
							  break;
						  }
					  }
				  }
			  }
			  else if(a[0].equals("call")){
				  call();
			  }
			  else if(a[0].equals("quit")){
				  scanner.close();
				  quit(output);
			  }
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public void push(Object o){
		String s=o.toString();
		if(checkInt(o)&&!map.containsKey(s)){
			pushInteger(o);
		}
		else if(s.charAt(0)==8221||s.charAt(s.length()-1)==8221||s.charAt(0)=='\"'){
			pushString(o);
		}
		else{
			pushName(o);
		}
	}
	public void pushInteger(Object o){
		if(o.toString().contains(".")){
			err();
		}
		else{
			int i=getInt(o);
			stack.push((Object)i);
			check.push(0);
		}
	}
	public void pushString(Object o){
		String s=o.toString();
		String n="";
		for(int i=1;i<s.length()-1;i++){
			n=n+s.charAt(i);
		}
		Object k=n;
		//stringPool.add(k);
		stack.push(k);
		check.push(0);
	}
	public void pushName(Object o){
		boolean b=true;
		String s=o.toString();
		for(int i=0;i<s.length();i++){
			if(!Character.isAlphabetic(s.charAt(i))&&!Character.isDigit(s.charAt(i))){
				b=false;
			}
		}
		if(b){
			stack.push(o);
			check.push(1);
		}
		else{
			err();
		}
	}
	public void pop(){
		if(stack.isEmpty()){
			err();
		}
		else{
			Object o=stack.pop();
			/*if(stringPool.contains(o)){
				stringPool.remove(o);
			}*/
			check.pop();
		}
	}
	public void pushBool(Object o){
		if(o.toString().equals(":true:")){
			stack.push(":true:");
		}
		else{
			stack.push(":false:");
		}
		check.push(0);
	}
	public void err(){
		check.push(0);
		stack.push(":error:");
	}
	public boolean checkInt(Object o){
		String s=o.toString();
		try{
			if(map.containsKey(s)){
				Double.parseDouble(map.get(s).toString());
			}
			else{
				Double.parseDouble(s);
			}
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	public int getInt(Object o){
		String s=o.toString();
		if(map.containsKey(s)){
			return Integer.parseInt(map.get(s).toString());
		}
		return Integer.parseInt(s);
	}
	public void add(){
		int a=0;
		int flag=0;
		Object x=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				a=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==1){
			x=stack.pop();
		}
		else{
			flag=0;
		}
		int b=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				b=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==2){
			stack.pop();
			int k=a+b;
			stack.push(k);
			check.pop();
			check.pop();
			check.push(0);
		}
		else if(flag==1){
			stack.push(x);
			err();
		}
		else{
			err();
		}
	}
	public void sub(){
		int a=0;
		Object x=0;
		int flag=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				a=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==1){
			x=stack.pop();
		}
		else{
			flag=0;
		}
		int b=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				b=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==2){
			stack.pop();
			int k=b-a;
			stack.push(k);
			check.pop();
			check.pop();
			check.push(0);
		}
		else if(flag==1){
			stack.push(x);
			err();
		}
		else{
			err();
		}
	}
	public void mul(){
		int a=0;
		Object x=0;
		int flag=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				a=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==1){
			x=stack.pop();
		}
		else{
			flag=0;
		}
		int b=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				b=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==2){
			stack.pop();
			int k=b*a;
			stack.push(k);
			check.pop();
			check.pop();
			check.push(0);
		}
		else if(flag==1){
			stack.push(x);
			err();
		}
		else{
			err();
		}
	}
	public void div(){
		int a=0;
		Object x=0;
		int flag=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				a=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==1){
			x=stack.pop();
		}
		else{
			flag=0;
		}
		int b=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				b=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==2){
			if(a==0){
				stack.push(x);
				err();
			}
			else{
				stack.pop();
				int k=b/a;
				stack.push(k);
				check.pop();
				check.pop();
				check.push(0);
			}
		}
		else if(flag==1){
			stack.push(x);
			err();
		}
		else{
			err();
		}
	}
	public void rem(){
		int a=0;
		Object x=0;
		int flag=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				a=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==1){
			x=stack.pop();
		}
		else{
			flag=0;
		}
		int b=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				b=getInt(stack.peek());
				flag=flag+1;
			}
		}
		if(flag==2){
			if(a==0){
				stack.push(x);
				err();
			}
			else{
				stack.pop();
				int k=b%a;
				stack.push(k);
				check.pop();
				check.pop();
				check.push(0);
			}
		}
		else if(flag==1){
			stack.push(x);
			err();
		}
		else{
			err();
		}
	}
	public void neg(){
		int a=0;
		int flag=0;
		if(!stack.isEmpty()){
			if(checkInt(stack.peek())){
				a=getInt(stack.peek());
				flag=flag+1;
			}
			if(flag==1){
				stack.pop();
				int k=-a;
				stack.push(k);
				check.pop();
				check.push(0);
			}
			else{
				err();
			}
		}
		else{
			err();
		}
	}
	public void swap(){
		if(stack.size()>=2){
			Object a=stack.pop();
			Object b=stack.pop();
			stack.push(a);
			stack.push(b);
			int k=check.pop();
			int l=check.pop();
			check.push(k);
			check.push(l);
		}
		else{
			err();
		}
	}
	public void quit(String a){
		try{
			File outFile = new File (a);
			FileWriter fWriter = new FileWriter (outFile);
			PrintWriter pWriter = new PrintWriter (fWriter);
			while(!stack.isEmpty()){
				pWriter.println(stack.pop());
			}
			pWriter.close();
		}
		catch(Exception e){
		}
		System.exit(1);
	}
/*
 * Second Part	
 */
	public boolean isBool(Object o){
		if(map.containsKey(o.toString())){
			if(map.get(o.toString()).toString().equals(":true:")){
				return true;
			}
			else if(map.get(o.toString()).toString().equals(":false:")){
				return true;
			}
			else{
				return false;
			}
		}
		else if(o.equals(":true:")||o.equals(":false:")){
			return true;
		}
		return false;
	}
	public boolean getBool(Object o){
		if(map.containsKey(o.toString())){
			if(map.get(o.toString()).toString().equals(":true:")){
				return true;
			}
			return false;
		}
		else if(o.equals(":true:")){
			return true;
		}
		return false;
	}
	public void and(){
		if(stack.size()<2){
			err();
		}
		else{
			Object o1=stack.pop();
			Object o2=stack.pop();
			if(isBool(o1)&&isBool(o2)){
				check.pop();
				check.pop();
				check.push(0);
				if(getBool(o1)&&getBool(o2)){
					stack.push(":true:");
				}
				else{
					stack.push(":false:");
				}
			}
			else{
				stack.push(o2);
				stack.push(o1);
				err();
			}
		}
	}
	public void or(){
		if(stack.size()<2){
			err();
		}
		else{
			Object o1=stack.pop();
			Object o2=stack.pop();
			if(isBool(o1)&&isBool(o2)){
				check.pop();
				check.pop();
				check.push(0);
				if(getBool(o1)||getBool(o2)){
					stack.push(":true:");
				}
				else{
					stack.push(":false:");
				}
			}
			else{
				stack.push(o2);
				stack.push(o1);
				err();
			}
		}
	}
	public void not(){
		if(!stack.isEmpty()){
			if(isBool(stack.peek())){
				Object o=stack.pop();
				if(getBool(o)){
					stack.push(":false:");
				}
				else{
					stack.push(":true:");
				}
				check.pop();
				check.push(0);
			}
			else{
				err();
			}
		}
		else{
			err();
		}
	}
	public void equal(){
		if(stack.size()<2){
			err();
		}
		else{
			Object o1=stack.pop();
			Object o2=stack.pop();
			if(checkInt(o1)&&checkInt(o2)){
				int a=getInt(o1);
				int b=getInt(o2);
				if(a==b){
					stack.push(":true:");
				}
				else{
					stack.push(":false:");
				}
				check.pop();
				check.pop();
				check.push(0);
			}
			else{
				stack.push(o2);
				stack.push(o1);
				err();
			}
		}
	}
	public void lessThan(){
		if(stack.size()<2){
			err();
		}
		else{
			Object o1=stack.pop();
			Object o2=stack.pop();
			if(checkInt(o1)&&checkInt(o2)){
				int a=getInt(o1);
				int b=getInt(o2);
				if(b<a){
					stack.push(":true:");
				}
				else{
					stack.push(":false:");
				}
				check.pop();
				check.pop();
				check.push(0);
			}
			else{
				stack.push(o2);
				stack.push(o1);
				err();
			}
		}
	}
	public void bind(){
		if(stack.size()<2){
			err();
		}
		else{
			Object o1=stack.pop(); //Value
			Object o2=stack.pop(); //Variable
			int a=check.pop();
			int b=check.pop();
			if(checkInt(o1)&&b==1){
				stack.push(":unit:");
				map.put(o2.toString(),getInt(o1));
				check.push(0);
			}
			else if(isBool(o1)&&b==1){
				stack.push(":unit:");
				if(getBool(o1)){
					map.put(o2.toString(),":true:");
				}
				else{
					map.put(o2.toString(),":false:");
				}
				check.push(0);
			}
			else if(o1.toString().equals(":unit:")&&b==1){
				stack.push(":unit:");
				map.put(o2.toString(),":unit:");
				check.push(0);
			}
			//!stringPool.contains(o2)&&stringPool.contains(o1)&&
			else if(b==1&&!o2.toString().equals(":error:")&&!o1.toString().equals(":error:")){
				stack.push(":unit:");
				map.put(o2.toString(),o1);
				check.push(0);
			}
			else{
				stack.push(o2);
				stack.push(o1);
				check.push(b);
				check.push(a);
				err();
			}
		}
	}
	public void iF(){
		if(stack.size()<3){
			err();
		}
		else{
			Object o1=stack.pop();
			Object o2=stack.pop();
			Object o3=stack.pop();
			int a=check.pop();
			int b=check.pop();
			int c=check.pop();
			if(isBool(o3)){
				if(getBool(o3)){
					stack.push(o1);
					check.push(a);
				}
				else{
					stack.push(o2);
					check.push(b);
				}
			}
			else{
				stack.push(o3);
				stack.push(o2);
				stack.push(o1);
				check.push(c);
				check.push(b);
				check.push(a);
				err();
			}
		}
	}
	public void let(){
		stack=new Stack<Object>();
		Stack<Object> o=frame.peek();
		Stack<Object> temp=new Stack<Object>();
		while(!o.isEmpty()){
			temp.push(o.pop());
		}
		while(!temp.isEmpty()){
			Object item=temp.pop();
			stack.push(item);
			o.push(item);
		}
		frame.push(stack);
		HashMap<String,Object> temp1=scope.peek();
		map=new HashMap<String,Object>();
		Iterator<String> it=temp1.keySet().iterator();
		while(it.hasNext()){
			String s=it.next();
			map.put(s,temp1.get(s));
		}
		scope.push(map);
		check=new Stack<Integer>();
		Stack<Integer> temp2=vars.peek();
		Stack<Integer> t=new Stack<Integer>();
		while(!temp2.isEmpty()){
			t.push(temp2.pop());
		}
		while(!t.isEmpty()){
			int item=t.pop();
			check.push(item);
			temp2.push(item);
		}
		vars.push(check);
		HashMap<String,ArrayList<Object>> temp3=funcScope.peek();
		functions=new HashMap<String,ArrayList<Object>>();
		Iterator<String> it3=temp3.keySet().iterator();
		while(it3.hasNext()){
			String s=it3.next();
			functions.put(s,temp3.get(s));
		}
		funcScope.push(functions);
		HashMap<String,ArrayList<Object>> temp4=ioFunS.peek();
		ioFunctions=new HashMap<String,ArrayList<Object>>();
		Iterator<String> it4=temp4.keySet().iterator();
		while(it4.hasNext()){
			String s=it4.next();
			ioFunctions.put(s,temp4.get(s));
		}
		ioFunS.push(functions);
	}
	public void end(){
		Object o=stack.pop();
		frame.pop();
		stack=frame.peek();
		frame.peek().push(o);
		scope.pop();
		map=scope.peek();
		Stack<Integer> temp=vars.pop();
		vars.peek().push(temp.pop());
		check=vars.peek();
		funcScope.pop();
		functions=funcScope.peek();
		ioFunS.pop();
		ioFunctions=ioFunS.peek();
	}
	
	/*
	 * Third Part
	 */
	public boolean isValidName(String s){
		if(s.charAt(0)==8221||s.charAt(s.length()-1)==8221||s.charAt(0)=='\"'){
			return false;
		}
		else if(reserved.contains(s)){
			return false;
		}
		else if(functions.containsKey(s)){
			return false;
		}
		return true;
	}
	public boolean isValidFunctionName(String s){
		if(s.charAt(0)==8221||s.charAt(s.length()-1)==8221||s.charAt(0)=='\"'){
			return false;
		}
		else if(reserved.contains(s)){
			return false;
		}
		return true;
	}
	public int operations(String[]a,int j,ArrayList<Object>ar){
		if(a[0].equals("push")){
			  String s=a[1];
			  for(int i=2;i<a.length;i++){
				  s=s+" "+a[i];
			  }
			  Object o=s;
			  push(o);
		  }
		  else if(a[0].equals("pop")){
			  pop();
		  }
		  else if(a[0].equals(":true:")||a[0].equals(":false:")){
			  Object o=a[0];
			  pushBool(o);
		  }
		  else if(a[0].equals(":error:")){
			  err();
		  }
		  else if(a[0].equals("add")){
			  add();
		  }
		  else if(a[0].equals("sub")){
			  sub();
		  }
		  else if(a[0].equals("mul")){
			  mul();
		  }
		  else if(a[0].equals("div")){
			  div();
		  }
		  else if(a[0].equals("rem")){
			  rem();
		  }
		  else if(a[0].equals("neg")){
			  neg();
		  }
		  else if(a[0].equals("swap")){
			  swap();
		  }
		  else if(a[0].equals("and")){
			  and();
		  }
		  else if(a[0].equals("or")){
			  or();
		  }
		  else if(a[0].equals("not")){
			  not();
		  }
		  else if(a[0].equals("equal")){
			  equal();
		  }
		  else if(a[0].equals("lessThan")){
			  lessThan();
		  }
		  else if(a[0].equals("bind")){
			  bind();
		  }
		  else if(a[0].equals("let")){
			  let();
		  }
		  else if(a[0].equals("end")){
			  end();
		  }
		  else if(a[0].equals("if")){
			  iF();
		  }
		  else if(a[0].equals("fun")){
			  int i=fun(a[1],a[2]);
			  if(i==1){
				  functions.get(a[1]).add(a[2]);
				  int count=0;
				  while(true){
					  j=j+1;
					  String s=ar.get(j).toString();
					  //String s=scanner.nextLine();
					  String[]b=s.split(" ");
					  if(b[0].equals("fun")||b[0].equals("inOutFun")){
						  count=count+1;
					  }
					  if(b[0].equals("funEnd")){
						  if(count==0){
							  break;
						  }
						  else{
							  count=count-1;
							  functions.get(a[1]).add(s);
						  }
					  }
					  else{
						  functions.get(a[1]).add(s);
					  }
				  }
				  HashMap<String,Object> ob=new HashMap<String,Object>();
				  Iterator<String> it=map.keySet().iterator();
				  while(it.hasNext()){
					  String s=it.next();
					  ob.put(s,map.get(s));
				  }
				  functions.get(a[1]).add(ob);
			  }
			  else{
				  while(true){
					  j=j+1;
					  String s=ar.get(j).toString();
					  String[]b=s.split(" ");
					  if(b[0]=="funEnd"){
						  break;
					  }
				  }
			  }
		  }
		  else if(a[0].equals("call")){
			  call();
		  }
		  else if(a[0].equals("inOutFun")){
			  int i=inOutFun(a[1],a[2]);
			  if(i==1){
				  int count=0;
				  ioFunctions.get(a[1]).add(a[2]);
				  while(true){
					  String s=ar.get(j).toString();
					  String[]b=s.split(" ");
					  if(b[0].equals("fun")||b[0].equals("inOutFun")){
						  count=count+1;
					  }
					  if(b[0].equals("funEnd")){
						  if(count==0){
							  break;
						  }
						  else{
							  ioFunctions.get(a[1]).add(s);
							  count=count-1;
						  }
					  }
					  else{
						  ioFunctions.get(a[1]).add(s);
					  }
				  }
				  HashMap<String,Object> ob=new HashMap<String,Object>();
				  Iterator<String> it=map.keySet().iterator();
				  while(it.hasNext()){
					  String s=it.next();
					  ob.put(s,map.get(s));
				  }
				  ioFunctions.get(a[1]).add(ob);
			  }
			  else{
				  while(true){
					  String s=ar.get(j).toString();
					  String[]b=s.split(" ");
					  if(b[0]=="funEnd"){
						  break;
					  }
				  }
			  }
		  }
		return j;
	}
	public int fun(String a, String b){
		if(a.equals(b)){
			err();
			return -1;
		}
		else if(isValidFunctionName(a)&&isValidName(b)){
			stack.push(":unit:");
			functions.put(a,new ArrayList<Object>());
			check.push(0);
			return 1;
		}
		else{
			err();
			return -1;
		}
	}
	public void call(){
		if(stack.size()<2){
			err();
		}
		else{
			Object o1=stack.pop(); //Function name
			Object o2=stack.pop(); //Value
			if(!functions.containsKey(o1.toString())&&!map.containsKey(o1)){
				if(ioFunctions.containsKey(o1.toString())){
					ioCall(o1,o2);
				}
				else{
					stack.push(o2);
					stack.push(o1);
					err();
				}
			}
			else{
				//o2 was here first
				if(o2.equals(":error:")){
					stack.push(o2);
					stack.push(o1);
					err();
				}
				else{
					int flag=0;
					if(map.containsKey(o1)){
						if(!functions.containsKey(o1)&&!functions.containsKey(map.get(o1))){
							if(ioFunctions.containsKey(o1)){
								ioCall(o1,o2);
							}
							else{
								stack.push(o2);
								stack.push(o1);
								err();
							}
							flag=1;
						}
						else{
							o1=map.get(o1);
						}
					}
					if(flag==0){
						check.pop();
						check.pop();
						HashMap<String,Object> cMap=new HashMap<String,Object>();
						Stack<Object> cStack=stack;
						Stack<Integer> cCheck=check;
						stack=new Stack<Object>();
						check=new Stack<Integer>();
						ArrayList<Object> a=functions.get(o1);
						HashMap<String,Object> m=(HashMap<String, Object>) a.get(a.size()-1);
						//map=new HashMap<String,Object>();
						Iterator<String> it=map.keySet().iterator();
						while(it.hasNext()){
							String s=it.next();
							//if(!m.containsKey(s)){
							cMap.put(s,map.get(s));
							//}
						}
						it=m.keySet().iterator();
						while(it.hasNext()){
							String s=it.next();
							map.put(s,m.get(s));
						}
						Object arg=a.get(0);
						if(cMap.containsKey(o2)){
							map.put(arg.toString(),cMap.get(o2));
						}
						/*else if(functions.containsKey(o2)){
							map.put(arg.toString(),functions.get(o2));
						}*/
						else{
							map.put(arg.toString(),o2);
						}
						for(int i=1;i<a.size()-1;i++){
							String s=a.get(i).toString();
							String[]values=s.split(" ");
							i=operations(values,i,a);
						}
						if(a.get(a.size()-2).equals("return")){
							Object o=stack.pop();
							while(map.containsKey(o)){
								o=map.get(o);
							}
							map=cMap;
							stack=cStack;
							int c=check.peek();
							check=cCheck;
							stack.push(o);
							check.push(c);
						}
						else{
							Object o=stack.pop();
							map=cMap;
							stack=cStack;
							check=cCheck;
						}
					}
				}
			}		
		}
	}
	public void ioCall(Object o1, Object o2){
		//Same as call
		check.pop();
		check.pop();
		HashMap<String,Object> cMap=new HashMap<String,Object>();
		Stack<Object> cStack=stack;
		Stack<Integer> cCheck=check;
		stack=new Stack<Object>();
		check=new Stack<Integer>();
		ArrayList<Object> a=ioFunctions.get(o1);
		HashMap<String,Object> m=(HashMap<String, Object>) a.get(a.size()-1);
		//map=new HashMap<String,Object>();
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
			String s=it.next();
			//if(!m.containsKey(s)){
			cMap.put(s,map.get(s));
			//}
		}
		it=m.keySet().iterator();
		while(it.hasNext()){
			String s=it.next();
			map.put(s,m.get(s));
		}
		Object arg=a.get(0);
		if(cMap.containsKey(o2)){
			map.put(arg.toString(),cMap.get(o2));
		}
		else{
			map.put(arg.toString(),o2);
		}
		for(int i=1;i<a.size()-1;i++){
			String s=a.get(i).toString();
			String[]values=s.split(" ");
			i=operations(values,i,a);
		}
		if(a.get(a.size()-2).equals("return")){
			Object o=stack.pop();
			Object val=map.get(arg);
			while(map.containsKey(o)){
				o=map.get(o);
			}
			map=cMap;
			map.put(o2.toString(),val);
			stack=cStack;
			int c=check.peek();
			check=cCheck;
			stack.push(o);
			check.push(c);
		}
		else{
			Object o=stack.pop();
			Object val=map.get(arg);
			map=cMap;
			map.put(o2.toString(),val);
			stack=cStack;
			check=cCheck;
		}
	}
	public int inOutFun(String a,String b){
		if(a.equals(b)){
			err();
			return -1;
		}
		else if(isValidFunctionName(a)&&isValidFunctionName(b)){
			stack.push(":unit:");
			ioFunctions.put(a,new ArrayList<Object>());
			check.push(0);
			return 1;
		}
		else{
			err();
			return -1;
		}
	}
	/*public static void main(String args[]){
		interpreter a= new interpreter("//Users//vikramgaru//Documents//workspace//CSE-305//src//sample_input1.txt","//Users//vikramgaru//Documents//workspace//CSE-305//src//sample_output6.txt");
		//new interpreter(args[0],args[1]);
	}*/
	public static void interpreter(String a, String b){
		new interpreter(a,b);
	}
}
