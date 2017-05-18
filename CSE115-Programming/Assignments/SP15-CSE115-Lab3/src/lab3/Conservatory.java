package lab3;

public class Conservatory {
	public Conservatory(){
	example1.Terrarium t;
	t=new example1.Terrarium();
	example1.Butterfly a,b,c;
	a=new example1.Butterfly();
	b=new example1.Butterfly();
	c=new example1.Butterfly();
	t.addButterfly(a);
	t.addButterfly(b);
	t.addButterfly(c);
	a.start();
	b.start();
	c.start();
	}
}
