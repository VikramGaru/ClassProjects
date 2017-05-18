package code.interfaces;

public class starter {
	public static void main(String args[]){
		InterfaceForModel a1=new Model();
		InterfaceForModel a2=new Model1();
		new View(a1);
		new View(a2);
	}
}
