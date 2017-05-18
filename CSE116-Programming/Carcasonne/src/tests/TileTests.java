package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.Edge;
import code.Model;
import code.Tile;
import features.City;
import features.Field;
import features.Road;

public class TileTests {

	@Test
	public void test() {
		Field f = new Field();
		Field f1 = new Field();
		Road r = new Road();
		City c = new City();
		Edge bottom = new Edge(f, f, f);
		Edge left = new Edge(f1, r, f);
		Edge top = new Edge(c, c, c);
		Edge right = new Edge(f, r, f1);
		Tile t = new Tile(bottom, left, top ,right);
		t.setImage(getClass().getResource("/11.png"));
		Field f2 = new Field();
		Field f3 = new Field();
		Road r1 = new Road();
		Edge bottom1 = new Edge(f2, r1, f3);
		Edge left1 = new Edge(f3, r1, f2);
		Edge top1 = new Edge(f3, f3, f3);
		Edge right1 = new Edge(f3, f3, f3);
		Tile t1 = new Tile(bottom1, left1, top1 ,right1);
		t1.setImage(getClass().getResource("/22.png"));
		boolean expected = true;
		String[] a = {"a","b"};
		Model m = new Model(a);
		boolean actual = m.compareEdges(t.getBottom(), t1.getTop());
		
		assertTrue("I expected the value to be  " + expected + " but what I got was " + actual, actual == expected);
	}
}
