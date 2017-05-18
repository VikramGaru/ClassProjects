package code;

import java.awt.Color;


public class Player {

	/**
	 * an instance variable of type String that holds the name of the Player
	 */
	private String _name;
	/**
	 * an instance variable of type int that keeps track of Meeples left
	 */
	private int _unusedFollowers;
	/**
	 * an instance variable of type Color that keeps track of the Player's color. 
	 * Will be useful for the color of the followers. 
	 */
	private Color _c;
	
	/**
	 * Establishes association relationships between the instance variables and the parameter variables
	 * @param name the name of the Player
	 * @param c the color assigned to the Player
	 */
	public Player(String name, Color c){
		_unusedFollowers = 8;
		_name = name;
		_c = c;
	}
	
	/**
	 * an accessor for the name of the Player
	 * @return a String that holds the name of the Player
	 */
	public String getName(){
		return _name;
	}
	
	/**
	 * an accessor for the color of the Player
	 * @return the color of the Player
	 */
	public Color getColor(){
		return _c;
	}
	
	/**
	 * an accessor for the number of followers left
	 * @return an int representing the remaining meeples
	 */
	public int followersLeft(){
		return _unusedFollowers;
	}
	
	/**
	 * This method places a Meeple on the feature
	 */
	public void placeMeeple(){
		_unusedFollowers--;
	}
}
