package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import code.Model;

public class BoardButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _x;
    private int _y;
    private Model _model;
	public BoardButton(int x, int y, Model m){
		_x = x;
		_y = y;
		_model = m;
		if(!_model.getBoard().isEmpty(_x, _y)){
			this.setIcon(new ImageIcon(_model.getBoard().getTile(_x, _y).getImage()));
			this.setDisabledIcon(this.getIcon());
			this.setEnabled(false);
		}
		this.addActionListener(new BoardButtonHandler());
	}
	
	
	public class BoardButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			_model.move(_x, _y);
		}
		
	}
}
