package code.interfaces;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Click implements KeyListener{
	private InterfaceForModel _a;
	public Click(InterfaceForModel a)
	{
		_a=a;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		_a.randomizeModel();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}