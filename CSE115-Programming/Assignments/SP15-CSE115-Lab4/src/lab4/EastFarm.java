package lab4;

public class EastFarm {
	private example1.Terrarium _t;
		public EastFarm(){
			_t=new example1.Terrarium();
		}
		public void addPig(){
			example1.Pig a=new example1.Pig();
			_t.addPig(a);
			a.start();
		}
		public void addButterfly(){
			example1.Butterfly a=new example1.Butterfly();
			_t.addButterfly(a);
			a.start();
		}
		public void addChicken(){
			example1.Chicken a=new example1.Chicken();
			_t.addChicken(a);
			a.start();
		}
}