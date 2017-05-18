package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import code.Model;

@SuppressWarnings("serial")
public class BoardWindow extends JPanel {
    private Model _model;
    private BoardButton[][] _buttons;
	public BoardWindow(Model model) {
		_model = model;
		int width = _model.getBoard().getWidth();
		int length = _model.getBoard().getLength();
		_buttons = new BoardButton[width][length];
		this.setLayout(new GridLayout(length, width));
		for(int i = 0; i<width; i++){
			for(int j = 0; j<length; j++){
				_buttons[i][j] = new BoardButton(i,j, _model);
				_buttons[i][j].setMaximumSize(new Dimension(80,80));;
				_buttons[i][j].setBorder(new LineBorder(Color.gray,1));
			}
		}
		length = _model.getBoard().getWidth();
		width = _model.getBoard().getLength();
		for(int i = 0; i<width; i++){
			for(int j = 0; j<length; j++){
				this.add(_buttons[j][i]);
			}
		}

	}
	
	public void update(){
		this.removeAll();
		int width = _model.getBoard().getWidth();
		int length = _model.getBoard().getLength();
		_buttons = new BoardButton[width][length];
		this.setLayout(new GridLayout(length, width));
		for(int i = 0; i<width; i++){
			for(int j = 0; j<length; j++){
				_buttons[i][j] = new BoardButton(i,j, _model);
				_buttons[i][j].setMaximumSize(new Dimension(80,80));;
				_buttons[i][j].setBorder(new LineBorder(Color.gray,1));
			}
		}
		length = _model.getBoard().getWidth();
		width = _model.getBoard().getLength();
		for(int i = 0; i<width; i++){
			for(int j = 0; j<length; j++){
				this.add(_buttons[j][i]);
			}
		}
		
	}

}
