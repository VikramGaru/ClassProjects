package code.interfaces;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

public class Model1 implements InterfaceForModel {
	private InterfaceForView _a;
	HashMap<Point,Integer> _b;
	public Model1(){
	}
	@Override
	public void randomizeModel() {
		_b=new HashMap<Point,Integer>();
		Random r=new Random();
		_b.put(new Point(0,0),r.nextInt());
		_b.put(new Point(0,1),r.nextInt());
		_b.put(new Point(1,0), r.nextInt());
		_b.put(new Point(1,1), r.nextInt());
		_a.updateView();
	}

	@Override
	public int get00() {
		return _b.get(new Point(0,0));
	}

	@Override
	public int get01() {
		return _b.get(new Point(0,1));
	}

	@Override
	public int get10() {
		return _b.get(new Point(1,0));
	}

	@Override
	public int get11() {
		return _b.get(new Point(1,1));
	}

	@Override
	public void addObserver(InterfaceForView view) {
		_a=view;
	}
}
