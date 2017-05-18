package code.interfaces;

public class Start {
	public static void main(String args[]){
		Model a=new Model();
		new View(a);
	}
}
/*public int count_e(String a){
	int c=0;
	for(int i=0;i<a.length()-1;i++){
		char b=a.charAt(i);
		if(b=='E'||b=='e'){
			c=c+1;
		}
		return c;
	}
}*/