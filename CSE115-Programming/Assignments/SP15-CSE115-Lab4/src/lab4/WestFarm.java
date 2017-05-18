package lab4;

public class WestFarm {
	private example1.Terrarium _t1;
	private example1.Terrarium _t2;
	public WestFarm(){
		_t1=new example1.Terrarium();
		_t2=new example1.Terrarium();
	}
	public void addPig(){
		example1.Pig a=new example1.Pig();
		example1.Terrarium b;
		_t1.addPig(a);
		a.start();
		b=_t2;
		_t2=_t1;
		_t1=b;
	}
	public void addButterfly(){
		example1.Butterfly a=new example1.Butterfly();
		example1.Terrarium b;
		_t1.addButterfly(a);
		a.start();
		b=_t2;
		_t2=_t1;
		_t1=b;
	}
	public void addChicken(){
		example1.Chicken a=new example1.Chicken();
		example1.Terrarium b;
		_t1.addChicken(a);
		a.start();
		b=_t2;
		_t2=_t1;
		_t1=b;
	}
}