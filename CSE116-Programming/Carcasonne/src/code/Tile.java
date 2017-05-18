package code;

import java.awt.Graphics2D;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Tile {

	/**
	 * an instance variable of type ArrayList<Edge> that holds the edges of a Tile
	 */
	private ArrayList<Edge> _edges;
	/**
	 * an instance variable of type boolean to signal whether the Tile has a monastery in the middle
	 */
	private boolean _hasMiddle; 
	/**
	 * an instance variable of type BufferedImage that holds the BufferedImage of the Tile
	 */
	private BufferedImage _img;
	
	/**
	 * The constructor adds the edges to the ArrayList
	 * @param bottom bottom edge of the Tile
	 * @param left left edge of the Tile
	 * @param top top edge of the Tile 
	 * @param right right edge of the Tile
	 */
	public Tile(Edge bottom, Edge left, Edge top, Edge right){
		_edges = new ArrayList<Edge>();
		_edges.add(bottom);
		_edges.add(left);
		_edges.add(top);
		_edges.add(right);
		_hasMiddle = false;
	}
	/**
	 * an accessor method for the edges of the Tile
	 * @return an ArrayList of Edges
	 */
	public ArrayList<Edge> getEdges(){
		return _edges;
	}
	
	/**
	 * Let's not even...
	 * TL;DR
	 */
	public void rotate(){
		_edges.add(0,_edges.remove(3));
		double sin = Math.abs(Math.sin(3.14/2)), cos = Math.abs(Math.cos(3.14/2));
		int w = _img.getWidth(), h = _img.getHeight();
		int neww = (int)Math.floor(w*cos + h*sin), newh= (int)Math.floor(h*cos + w*sin);
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage img = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
		Graphics2D g = img.createGraphics();
		g.translate((neww-w)/2, (newh-h)/2);
		g.rotate(3.14/2, w/2, h/2);
		g.drawRenderedImage(_img, null);
		g.dispose();
		_img = img;
		
	}
	
	/**
	 * an accessor that returns true if the Tile has something in the middle, false otherwise
	 * @return a boolean 
	 */
	public boolean hasMiddle(){
		return _hasMiddle;
	}
	
	/**
	 * a mutator method that lets you set a middle for the Tile
	 * @param b boolean true if there's a middle, false if not
	 */
	public void setMiddle(boolean b){
		_hasMiddle = b;
	}
	/**
	 * an accessor method that returns the top Edge
	 * @return an edge
	 */
	public Edge getTop(){
		return _edges.get(2);
	}
	/**
	 * an accessor method that returns the bottom Edge
	 * @return an edge
	 */
	public Edge getBottom(){
		return _edges.get(0);
	}
	/**
	 * an accessor method that returns the left Edge
	 * @return an edge
	 */
	public Edge getLeft(){
		return _edges.get(1);
	}
	/**
	 * an accessor method that returns the right Edge
	 * @return an edge
	 */
	public Edge getRight(){
		return _edges.get(3);
	}
	
	/**
	 * this method takes the file path of the Tile image and creates a BufferedImage from it
	 * @param url the path of the Tile image file
	 */
	public void setImage(URL url){
		try{
			_img = ImageIO.read(url);
		}
		catch(IOException e){
			
		}
	}
	
	/**
	 * an accessor for the Tile image
	 * @return a BufferedImage
	 */
	public BufferedImage getImage(){
		return _img;
	}
	public void meeple(Model m) {
		// TODO Auto-generated method stub
		try{
			Meeple a = new Meeple(m);
			BufferedImage img = a.placeMeeple();
			_img=img;
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
