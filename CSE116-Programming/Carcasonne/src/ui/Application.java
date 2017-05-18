package ui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import code.Model;

public class Application implements Runnable, Observer {
	private JFrame  _mainFrame;
	private JPanel _boardWindow;
	private JPanel _infoWindow;
	private Model _model;
	public Application(String[] a){
		_model = new Model(a);
		_model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		((BoardWindow) _boardWindow).update();
		_boardWindow.setBounds(0, 0, _model.getBoard().getWidth()*80, _model.getBoard().getWidth()*80);
		((InfoWindow)_infoWindow).update();
		_mainFrame.pack();
	}

	@Override
	public void run() {
		_mainFrame = new JFrame("Carcassonne");
		_boardWindow = new BoardWindow(_model);
		_boardWindow.setBounds(0, 0, _model.getBoard().getWidth()*80, _model.getBoard().getWidth()*80);
		_infoWindow = new InfoWindow(_model);
		_mainFrame.add(_boardWindow, BorderLayout.WEST);
		_mainFrame.add(_infoWindow, BorderLayout.EAST);
		_mainFrame.setVisible(true);
		_mainFrame.pack();
		_mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	


}


