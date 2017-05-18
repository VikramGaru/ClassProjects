package code;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	public Window(){
		JButton button=new JButton("Click me");
		EventHandler eventhandler=new EventHandler();
		KeyHandler keyhandler=new KeyHandler();
		button.addActionListener(eventhandler);
		button.addKeyListener(keyhandler);
		JPanel panel=new JPanel();
		panel.add(button);
		JFrame j=new JFrame();
		j.addKeyListener(keyhandler);
		j.add(panel);
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.pack();
	}
	public static void main(String[] args){
		new Window();
	}

}
