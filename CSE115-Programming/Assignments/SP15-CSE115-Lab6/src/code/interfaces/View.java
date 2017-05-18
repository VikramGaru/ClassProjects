package code.interfaces;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View implements InterfaceForView{
	private InterfaceForModel _m;
	private JFrame _a;
	private JButton _b,_c,_d,_e;
	public View(InterfaceForModel a){
		_m=a;
		_m.addObserver(this);
		_a=new JFrame("SP15-LAB6");
		_a.setVisible(true);
		JPanel f=new JPanel();
		_b=new JButton();
		_b.setText("0");
		_b.addKeyListener(new Click(_m));
		_c=new JButton();
		_c.setText("0");
		_c.addKeyListener(new Click(_m));
		_d=new JButton();
		_d.setText("0");
		_d.addKeyListener(new Click(_m));
		_e=new JButton();
		_e.setText("0");
		_e.addKeyListener(new Click(_m));
		f.add(_b);
		f.add(_c);
		f.add(_d);
		f.add(_e);
		f.setLayout(new GridLayout(2,2));
		_a.add(f);
		_a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_a.pack();
	}

	@Override
	public void updateView() {
		int i;
		String y;
		i=_m.get00();
		y=Integer.toString(i);
		_b.setText(y);
		i=_m.get01();
		y=Integer.toString(i);
		_c.setText(y);
		i=_m.get10();
		y=Integer.toString(i);
		_d.setText(y);
		i=_m.get11();
		y=Integer.toString(i);
		_e.setText(y);
	}
}
