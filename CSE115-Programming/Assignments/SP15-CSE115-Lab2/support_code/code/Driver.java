package code;

import example1.Butterfly;
import example1.Chicken;
import example1.Pig;
import example1.Terrarium;

public class Driver {
	public static void main(String[] args) {
		Terrarium t = new Terrarium();

		for (int i = 0; i < 10; i++) {
			Chicken c1, c2, c3;
			c1 = new Chicken();
			c2 = new Chicken();
			c3 = new Chicken();

			c1.start();
			c2.start();

			Butterfly b1, b2, b3;
			b1 = new Butterfly();
			b2 = new Butterfly();
			b3 = new Butterfly();

			b1.start();
			b2.start();
			b3.start();

			Pig p1, p2, p3;
			p1 = new Pig();
			p2 = new Pig();
			p3 = new Pig();

			p1.start();
			p2.start();
			p3.start();
			p3.stop();

			t.addButterfly(b1);
			t.addButterfly(b2);
			t.addButterfly(b3);
			t.addChicken(c1);
			t.addChicken(c2);
			t.addChicken(c3);
			t.addPig(p1);
			t.addPig(p2);
			t.addPig(p3);
		}
	}
}
