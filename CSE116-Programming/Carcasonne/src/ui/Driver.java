package ui;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import code.FileSave;

public class Driver {
	
	public static void main(String[] args) {
		try {for(int i = 0; i<4; i++){
			String a = "Enter name of Player " +(i+1);
			args[i] = JOptionPane.showInputDialog(a);
		}
		new FileSave();
		SwingUtilities.invokeLater(new Application(args));
		
	
	}
		catch (Exception e ){
			e.printStackTrace();
		}
}
}