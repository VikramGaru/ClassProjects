package code.interfaces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Model {
	private int _i;
	private HashMap<Point,Integer> _m;
	private View _a;
	private ArrayList<java.awt.Point>_b;
	public Model(){
		_m=new HashMap<Point,Integer>();
		_m.put(new Point(0,0), 2);
		_b=new ArrayList<java.awt.Point>();
		_i=0;
	}
	public void addView(View a){
		_a=a;
	}
	public void start(){
		_i=0;
		add();
		play();
		if(_i!=0){
			list();
			_m.put(new Point(_b.get(0)),2);
		}
		_a.updateView();
	}
	public void play(){
		int a=0;
		int i=3;
		while(i>=0){
			if(_m.containsKey(new Point(0,i))){
				if(a!=0){
				int j=3;
				while(j>=0){
					if(_m.containsKey(new Point(0,j))){
					}
					else{
						_m.put(new Point(0,j), _m.get(new Point(0,i)));
						_m.remove(new Point(0,i));
						j=0;
						_i=1;
					}
					j=j-1;
				}
			}
			}
			else{
				a=1;
			}
			i=i-1;
		}
	}
	public void add(){
		int i=3;
		while(i>=0){
			if(_m.containsKey(new Point(0,i))){
				int j=i-1;
				while(j>=0){
					if(_m.containsKey(new Point(0,j))){
						if(_m.get(new Point(0,i))==_m.get(new Point(0,j))){
							int a=_m.get(new Point(0,i));
							int b=_m.get(new Point(0,j));
							int c=a+b;
							_m.put(new Point(0,i),c);
							_m.remove(new Point(0,j));
							_i=1;
						}
						j=0;
					}
					j=j-1;
				}
			}
			i=i-1;
		}
	}
	public void start1(){
		_i=0;
		add1();
		play1();
		if(_i!=0){
			list();
			_m.put(new Point(_b.get(0)),2);
		}
		_a.updateView();
	}
	public void play1(){
		int a=0;
		int i=0;
		while(i<=3){
			if(_m.containsKey(new Point(0,i))){
				if(a!=0){
				int j=0;
				while(j<=3){
					if(_m.containsKey(new Point(0,j))){
					}
					else{
						_m.put(new Point(0,j), _m.get(new Point(0,i)));
						_m.remove(new Point(0,i));
						j=3;
						_i=1;
					}
					j=j+1;
				}
			}
			}
			else{
				a=1;
			}
			i=i+1;
		}
	}
	public void add1(){
		int i=0;
		while(i<=3){
			if(_m.containsKey(new Point(0,i))){
				int j=i+1;
				while(j<=3){
					if(_m.containsKey(new Point(0,j))){
						if(_m.get(new Point(0,i))==_m.get(new Point(0,j))){
							int a=_m.get(new Point(0,i));
							int b=_m.get(new Point(0,j));
							int c=a+b;
							_m.put(new Point(0,i),c);
							_m.remove(new Point(0,j));
							_i=1;
						}
						j=3;
					}
					j=j+1;
				}
			}
			i=i+1;
		}
	}
	public void list(){
		_b=new ArrayList<java.awt.Point>();
		int i=0;
		while(i<=3){
			if(_m.containsKey(new Point(0,i))){
			}
			else{
				_b.add(new Point(0,i));
			}
			i=i+1;
		}
		Collections.shuffle(_b);
	}
public int get00() {
	if(_m.containsKey(new Point(0,0))){
			int i=_m.get(new Point(0,0));
			return i;
		}
		else{
			return 0;
		}
	}
public int get01() {
		if(_m.containsKey(new Point(0,1))){
			int i=_m.get(new Point(0,1));
			return i;
		}
		else{
			return 0;
		}
	}
public int get10() {
		if(_m.containsKey(new Point(0,2))){
			int i=_m.get(new Point(0,2));
			return i;
		}
		else{
			return 0;
		}
	}
public int get11() {
		if(_m.containsKey(new Point(0,3))){
			int i=_m.get(new Point(0,3));
			return i;
		}
		else{
			return 0;
		}
	}
}
