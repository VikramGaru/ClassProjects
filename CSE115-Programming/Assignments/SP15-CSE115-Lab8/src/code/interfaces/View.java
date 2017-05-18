package code.interfaces;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View {
	private JFrame _a;
	private JButton _b,_c,_d,_e,_b1,_c1,_d1,_e1,_b2,_c2,_d2,_e2,_b3,_c3,_d3,_e3;
	private JLabel _f,_f1,_f2;
	private Model _m;
	public View(Model m){
		_m=m;
		_m.addView(this);
		_a=new JFrame("Game");
		JPanel f=new JPanel();
		JPanel f1=new JPanel();
		JPanel f2=new JPanel();
		_f=new JLabel();
		_f1=new JLabel();
		_f2=new JLabel();
		_b=new JButton();
		_b.addKeyListener(new Keypress(_m));
		_c=new JButton();
		_c.addKeyListener(new Keypress(_m));
		_d=new JButton();
		_d.addKeyListener(new Keypress(_m));
		_e=new JButton();
		_e.addKeyListener(new Keypress(_m));
		_b1=new JButton();
		_b1.addKeyListener(new Keypress(_m));
		_c1=new JButton();
		_c1.addKeyListener(new Keypress(_m));
		_d1=new JButton();
		_d1.addKeyListener(new Keypress(_m));
		_e1=new JButton();
		_e1.addKeyListener(new Keypress(_m));
		_b2=new JButton();
		_b2.addKeyListener(new Keypress(_m));
		_c2=new JButton();
		_c2.addKeyListener(new Keypress(_m));
		_d2=new JButton();
		_d2.addKeyListener(new Keypress(_m));
		_e2=new JButton();
		_e2.addKeyListener(new Keypress(_m));
		_b3=new JButton();
		_b3.addKeyListener(new Keypress(_m));
		_c3=new JButton();
		_c3.addKeyListener(new Keypress(_m));
		_d3=new JButton();
		_d3.addKeyListener(new Keypress(_m));
		_e3=new JButton();
		_e3.addKeyListener(new Keypress(_m));	
		f1.setLayout(new GridLayout(2,2));
		f1.add(_f);
		f1.add(_f1);
		f1.add(_f2);
		f.add(_b);
		f.add(_c);
		f.add(_d);
		f.add(_e);
		f.add(_b1);
		f.add(_c1);
		f.add(_d1);
		f.add(_e1);
		f.add(_b2);
		f.add(_c2);
		f.add(_d2);
		f.add(_e2);
		f.add(_b3);
		f.add(_c3);
		f.add(_d3);
		f.add(_e3);
		f.setLayout(new GridLayout(4,4));
		f2.setLayout(new BoxLayout(f2, BoxLayout.Y_AXIS));
		f2.add(f1);
		f2.add(f);
		_a.add(f2);
		_a.setVisible(true);
		_a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_a.pack();
		updateView();
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
		
		i=_m.get02();
		if(i!=0){
			y=Integer.toString(i);
			_d.setText(y);	
		}
		else{
			_d.setText(null);
		}
		i=_m.get03();
		if(i!=0){
			y=Integer.toString(i);
			_e.setText(y);	
		}
		else{
			_e.setText(null);
		}
		i=_m.get10();
		if(i!=0){
			y=Integer.toString(i);
			_b1.setText(y);
		}
		else{
			_b1.setText(null);
		}
		i=_m.get11();
		if(i!=0){
			y=Integer.toString(i);
			_c1.setText(y);	
		}
		else{
			_c1.setText(null);
		}
		
		i=_m.get12();
		if(i!=0){
			y=Integer.toString(i);
			_d1.setText(y);	
		}
		else{
			_d1.setText(null);
		}
		i=_m.get13();
		if(i!=0){
			y=Integer.toString(i);
			_e1.setText(y);	
		}
		else{
			_e1.setText(null);
		}
		i=_m.get20();
		if(i!=0){
			y=Integer.toString(i);
			_b2.setText(y);
		}
		else{
			_b2.setText(null);
		}
		i=_m.get21();
		if(i!=0){
			y=Integer.toString(i);
			_c2.setText(y);	
		}
		else{
			_c2.setText(null);
		}
		
		i=_m.get22();
		if(i!=0){
			y=Integer.toString(i);
			_d2.setText(y);	
		}
		else{
			_d2.setText(null);
		}
		i=_m.get23();
		if(i!=0){
			y=Integer.toString(i);
			_e2.setText(y);	
		}
		else{
			_e2.setText(null);
		}
		i=_m.get30();
		if(i!=0){
			y=Integer.toString(i);
			_b3.setText(y);
		}
		else{
			_b3.setText(null);
		}
		i=_m.get31();
		if(i!=0){
			y=Integer.toString(i);
			_c3.setText(y);	
		}
		else{
			_c3.setText(null);
		}
		
		i=_m.get32();
		if(i!=0){
			y=Integer.toString(i);
			_d3.setText(y);	
		}
		else{
			_d3.setText(null);
		}
		i=_m.get33();
		if(i!=0){
			y=Integer.toString(i);
			_e3.setText(y);	
		}
		else{
			_e3.setText(null);
		}
		i=_m.score();
		y=Integer.toString(i);
		_f1.setText("Your Current Score Is "+y+" Points");
		i=_m.highscore();
		y=Integer.toString(i);
		_f2.setText("High Score  "+y);
		y=_m.gameover();
		_f.setText(y);
		_a.pack();
	}
}