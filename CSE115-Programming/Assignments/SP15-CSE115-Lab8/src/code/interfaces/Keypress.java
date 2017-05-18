package code.interfaces;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keypress implements KeyListener {
	private Model _m;
	public Keypress(Model m){
		_m=m;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() ==KeyEvent.VK_RIGHT){
			_m.start();
		}
		else if(e.getKeyCode() ==KeyEvent.VK_LEFT){
			_m.start1();
		}
		else if(e.getKeyCode() ==KeyEvent.VK_UP){
			_m.start2();
		}
		else if(e.getKeyCode() ==KeyEvent.VK_DOWN){
			_m.start3();
		}
		else if(e.getKeyCode()==KeyEvent.VK_ENTER){
			_m.restart();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
