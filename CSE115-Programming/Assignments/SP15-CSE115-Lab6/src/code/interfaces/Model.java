package code.interfaces;

import java.util.Random;

public class Model implements InterfaceForModel{
	private InterfaceForView _a;
	private int _i,_j,_k,_l;
	public Model(){
		_i=0;
		_j=0;
		_k=0;
		_l=0;
	}
	@Override
	public void randomizeModel() {
		Random r=new Random();
		_i=r.nextInt();
		_a.updateView();
		_j=r.nextInt();
		_a.updateView();
		_k=r.nextInt();
		_a.updateView();
		_l=r.nextInt();
		_a.updateView();
	}

	@Override
	public int get00() {
		return _i;
	}

	@Override
	public int get01() {
		return _j;
	}

	@Override
	public int get10() {
		return _k;
	}

	@Override
	public int get11() {
		return _l;
	}

	@Override
	public void addObserver(InterfaceForView view) {
		_a=view;
	}
}
