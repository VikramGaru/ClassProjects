package lab3;

public class Coop {
	public Coop(){
	example1.Terrarium t;
	t=new example1.Terrarium();
	example1.Chicken a,b,c,d;
	a=new example1.Chicken();
	b=new example1.Chicken();
	c=new example1.Chicken();
	d=new example1.Chicken();
	t.addChicken(a);
	t.addChicken(b);
	t.addChicken(c);
	t.addChicken(d);
	a.start();
	b.start();
	c.start();
	d.start();
	}
}
