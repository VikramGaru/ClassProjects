package code.interfaces;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View {
	private JFrame _a;
	private JButton _b,_c,_d,_e;
	private Model _m;
	public View(Model m){
		_m=m;
		_m.addView(this);
		_a=new JFrame("Game");
		JPanel f=new JPanel();
		_b=new JButton();
		_b.setText("2");
		_b.addKeyListener(new Keypress(_m));
		_c=new JButton();
		_c.addKeyListener(new Keypress(_m));
		_d=new JButton();
		_d.addKeyListener(new Keypress(_m));
		_e=new JButton();
		_e.addKeyListener(new Keypress(_m));
		f.add(_b);
		f.add(_c);
		f.add(_d);
		f.add(_e);
		f.setLayout(new GridLayout(1,4));
		_a.add(f);
		_a.setVisible(true);
		_a.pack();
	}
	public void updateView() {
		int i;
		String y;
		i=_m.get00();
		if(i!=0){
			y=Integer.toString(i);
			_b.setText(y);
		}
		else{
			_b.setText(null);
		}
		i=_m.get01();
		if(i!=0){
			y=Integer.toString(i);
			_c.setText(y);	
		}
		else{
			_c.setText(null);
		}
		i=_m.get10();
		if(i!=0){
			y=Integer.toString(i);
			_d.setText(y);	
		}
		else{
			_d.setText(null);
		}
		i=_m.get11();
		if(i!=0){
			y=Integer.toString(i);
			_e.setText(y);	
		}
		else{
			_e.setText(null);
		}
	}
}
