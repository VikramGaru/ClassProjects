package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import code.FileSave;
import code.Model;

public class InfoWindow extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Model _model; 
	private JButton _rotate;
	private JButton _save;
	private JButton _meeple;
	private JButton _read;
	public InfoWindow(Model model) {
		_model = model;
		this.setLayout(new GridLayout(2,1,0,0));
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i<_model.getPlayers().size(); i++){
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
			JLabel playerLabel = new JLabel(_model.getPlayers().get(i).getName());
			playerLabel.setForeground(_model.getPlayers().get(i).getColor());
			if(_model.getPlayers().get(i).equals(_model.getCurrentPlayer())){
				playerLabel.setText(playerLabel.getText() + " (Current Player)");
			}
			JLabel followerLabel = new JLabel("Followers Left: " + _model.getPlayers().get(i).followersLeft());
			myPanel.add(playerLabel);
			myPanel.add(followerLabel);
			playerPanel.add(myPanel);
		}
		this.add(playerPanel);
		JPanel tilePanel = new JPanel();
		tilePanel.setLayout(new BoxLayout(tilePanel, BoxLayout.Y_AXIS));
		tilePanel.add(new JLabel("Your Tile"));
		_rotate = new JButton("Rotate");
		_rotate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_model.getCurrentTile().rotate();
				_model.updateUI();
			}
			
		});
		tilePanel.add(_rotate);
		_meeple = new JButton("Add Meeple");
		_meeple.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_model.getCurrentTile().meeple(_model);
				_model.updateUI();
			}
			
		});
		tilePanel.add(_meeple);
		
		_save = new JButton("Save");
		_save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FileSave().saveFile("save.txt");
			}
			
		});
		tilePanel.add(_save);
		
		_read = new JButton("Restore");
		_read.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FileSave().readFile("save.txt");
			}
			
		});
		tilePanel.add(_read);
		


		JLabel tile = new JLabel(new ImageIcon(_model.getCurrentTile().getImage()));
		tilePanel.add(tile);
		this.add(tilePanel);
	}
	
	public void update(){
		this.removeAll();
		this.setLayout(new GridLayout(2,1,0,0));
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i<_model.getPlayers().size(); i++){
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
			JLabel playerLabel = new JLabel(_model.getPlayers().get(i).getName());
			playerLabel.setForeground(_model.getPlayers().get(i).getColor());
			if(_model.getPlayers().get(i).equals(_model.getCurrentPlayer())){
				playerLabel.setText(playerLabel.getText() + " (Current Player)");
				playerLabel.setSize(playerLabel.getSize().width*2,playerLabel.getSize().height*2);
			}
			JLabel followerLabel = new JLabel("Followers Left: " + _model.getPlayers().get(i).followersLeft());
			myPanel.add(playerLabel);
			myPanel.add(followerLabel);
			playerPanel.add(myPanel);
		}
		if(_model.adjacentError()){
			JLabel myLabel = new JLabel("The placement needs to be adjacent");
			myLabel.setForeground(Color.RED);
			playerPanel.add(myLabel);
		}
		else if(_model.legalPlacementError()){
			JLabel myLabel = new JLabel("The placement is not legal");
			myLabel.setForeground(Color.RED);
			playerPanel.add(myLabel);
		}
		this.add(playerPanel);
		JPanel tilePanel = new JPanel();
		tilePanel.setLayout(new BoxLayout(tilePanel, BoxLayout.Y_AXIS));
		tilePanel.add(new JLabel("Your Tile"));
		tilePanel.add(_rotate);
		tilePanel.add(_save);
		tilePanel.add(_meeple);
		tilePanel.add(_read);
	
		JLabel tile = new JLabel(new ImageIcon(_model.getCurrentTile().getImage()));
		tilePanel.add(tile);
		this.add(tilePanel);
	}

}
