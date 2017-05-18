package lab3;

public class Pen {
	public Pen(){
		example1.Terrarium t;
		t=new example1.Terrarium();
		example1.Pig a,b,c,d,e;
		a=new example1.Pig();
		b=new example1.Pig();
		c=new example1.Pig();
		d=new example1.Pig();
		e=new example1.Pig();
		t.addPig(a);
		t.addPig(b);
		t.addPig(c);
		t.addPig(d);
		t.addPig(e);
		a.start();
		b.start();
		c.start();
		d.start();
		e.start();
	}

}
