package code;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Meeple {
	/*private JFrame _j;
	private JPanel _pl;
	private JButton _b;*/
	private Model _model;
	
	private static BufferedImage joinedimg;
	//private static ImageIcon im1;
	
	


	public  Meeple(Model model) throws IOException{
		_model = model;
		/*int i = JOptionPane.showConfirmDialog(null,"Place Mepple?");
		if(i == 0){
			GridLayout gl = new GridLayout(2,2);
				_j = new JFrame("Meeple Placement");
				_pl = new JPanel(gl);
				
				
				placeMeeple();
				_pl.add(_b);
				_j.add(_pl);
				_j.pack();
				_j.setVisible(true);
				
				
		
	}*/
	}
	public BufferedImage placeMeeple() throws IOException {
		
		for(int a = 0; a<4; a++){
			ImageIcon t = new ImageIcon(_model.getCurrentTile().getImage());
		
			Image im = t.getImage();
			BufferedImage buff = (BufferedImage) im;
			
	
			BufferedImage img = ImageIO.read(_model.getMeeple());
			
			
			joinedimg = joinImage(buff,img);
			boolean success;

				success = ImageIO.write(joinedimg, "png", new File("joined.png"));
				System.out.println("saved?"+success);
				/*im1 = new ImageIcon(joinedimg);
			_b = new JButton();
			_b.setIcon(im1);
			_pl.add(_b);

		_b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				//Tile t =_tile.setImage("joined.png");
				//((JButton) e.getSource()).setIcon(new ImageIcon(_model.getBoard().getTile(_x, _y).getImage()));
				//_board.placeTile(_x, _y,"");
				_model.getCurrentPlayer().placeMeeple();
 				_j.dispose();

			}	
		});
		}*/}
				_model.getCurrentPlayer().placeMeeple();
				return joinedimg;
		

}	
	public BufferedImage joinImage(BufferedImage img , BufferedImage img2){
		int wid = img.getWidth();
		int height = img.getHeight();
		BufferedImage newImage = new BufferedImage(wid,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		g2.setPaint(Color.WHITE);
		g2.fillRect(0,0, wid, height);
		g2.setColor(oldColor);
		
		g2.drawImage(img, null, 0, 0);
		g2.drawImage(img2, null, 50, 20);
		
		g2.dispose();
		return newImage;
	}
	
	
}

	

