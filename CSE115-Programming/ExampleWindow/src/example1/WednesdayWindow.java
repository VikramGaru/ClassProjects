package example1;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WednesdayWindow {
	public WednesdayWindow(){
		JFrame jframe=new JFrame("Example Title");
		jframe.setVisible(true);
		GridLayout gridlayout=new GridLayout(1,2);
		JPanel panel=new JPanel();
		panel.setLayout(gridlayout);
		JTextArea text=new JTextArea();
		JScrollPane scrollTextArea=new JScrollPane(text);
		JLabel label=new JLabel();
		JScrollPane scrollTextLabel=new JScrollPane(label);
		label.setText("Hi I am a label");
		jframe.add(panel);
		panel.add(scrollTextArea);
		panel.add(scrollTextLabel);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.pack();
	}

}
