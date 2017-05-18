package lab2;

public class BarnYard {

	public BarnYard() {
		example1.Terrarium t;
		t=new example1.Terrarium();
		example1.Pig p;
		example1.Pig q;
		example1.Butterfly a;
		example1.Butterfly b;
		example1.Butterfly g;
		example1.Chicken c;
		example1.Chicken d;
		example1.Chicken e;
		example1.Chicken f;
		p= new example1.Pig();
		q=new example1.Pig();
		a=new example1.Butterfly();
		b=new example1.Butterfly();
		g=new example1.Butterfly();
		c=new example1.Chicken();
		d=new example1.Chicken();
		e=new example1.Chicken();
		f=new example1.Chicken();
		t.addButterfly(a);
		t.addButterfly(b);
		t.addButterfly(g);
		t.addPig(p);
		t.addPig(q);
		t.addChicken(c);
		t.addChicken(f);
		t.addChicken(e);
		t.addChicken(d);
		p.start();
		a.start();
		b.start();
		c.start();
		d.start();
		e.start();		
	}

}
