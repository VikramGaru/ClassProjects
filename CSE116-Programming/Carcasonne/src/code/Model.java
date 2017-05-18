package code;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;


public class Model extends Observable {
	/**
	 * an instance variable that represents the Inventory(all Tiles) of the game
	 */
	private Inventory _inventory;
	/**
	 * an instance variable of type ArrayList<Player> that holds the players of the game
	 */
	private ArrayList<Player> _players;
	/**
	 * an instance variable of type Board that represents the Board of the game
	 */
	private Board _board;
	/**
	 * an instance variable of type Tile that holds the random Tile of the current player 
	 */
	private Tile _currentTile;
	/**
	 * a boolean that signals to the UI whether the player tried to place a Tile that's not adjacent to the Board
	 */
	private boolean _adjacentError;
	/**
	 * a boolean that signals to the UI whether the player tried to make an illegal move
	 */
	private boolean _legalPlacementError;
	
	/**
	 * The constructor initialized the Inventory, the Board and creates the players of the game  
	 */
	private String[] _q;
	
	private FileSave _save;
	
	public Model(String[] s){
		_q=s;
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.RED);
		colors.add(Color.BLACK);
		colors.add(Color.BLUE);
		colors.add(Color.YELLOW);
		_players = new ArrayList<Player>();
		for(int i = 0; i < s.length; i++){
			_players.add(new Player((s[i]), colors.get(i)));
		}
		_inventory = new Inventory();
		_board = new Board(this);
		_currentTile = _inventory.getRandomTile();
		_adjacentError = false;
		_legalPlacementError = false;

	}
	public URL getMeeple(){
		URL s = null ;
		
		if(_players.get(0).getName().equals(_q[0])){
			System.out.println(_players.get(0).getName() + _q[0]);
			s = getClass().getResource("/MeeplePlayer1.png");
		}else if(_players.get(0).getName().equals(_q[1])){
			System.out.println(_players.get(0).getName() + _q[1]);
			s = getClass().getResource("/MeeplePlayer2.png");
		}else if(_players.get(0).getName().equals(_q[2])){
			System.out.println(_players.get(0).getName() + _q[2]);
			s = getClass().getResource("/MeeplePlayer3.png");
		}else if(_players.get(0).getName().equals(_q[3])){
			System.out.println(_players.get(0).getName() + _q[3]);
			s = getClass().getResource("/MeeplePlayer4.png");
		}else{ System.out.println("error");}
		
		return s;
	}
	
	/**
	 * an accessor method for the players of the game
	 * @return an ArrayList of Players
	 */
	public ArrayList<Player> getPlayers(){
		return _players;
	}

	/**
	 * an accessor method for the current player of the game
	 * @return a player
	 */
	public Player getCurrentPlayer(){
		return _players.get(0);
	}

	/**
	 * an accessor method for the adjacency placement error. Used by the UI to display error message
	 * @return a boolean true if there's an error, false otherwise
	 */
	public boolean adjacentError(){
		return _adjacentError;
	}

	/**
	 * an accessor method for the legal placement error. Used by the UI to display error message
	 * @return a boolean true if there's an error, false otherwise
	 */
	public boolean legalPlacementError(){
		return _legalPlacementError;
	}

	/**
	 * an accessor method for the Inventory of the game 
	 * @return the Inventory
	 */
	public Inventory getInventory(){
		return _inventory;
	}
	
	public FileSave save(){
		return _save;
	}

	/**
	 * an accessor method for the Board of the game 
	 * @return the Board
	 */
	public Board getBoard(){
		return _board;
	}

	/**
	 * an accessor method for the current Tile of the game
	 * @return a Tile
	 */
	public Tile getCurrentTile(){
		return _currentTile;
	}

	/**
	 * Compares two edges of a Tile to determine whether they can be placed together or now
	 * @param a the first edge
	 * @param b the second edge
	 * @return a boolean true if the placement is legal and false if it's not
	 */
	public boolean compareEdges(Edge a, Edge b){
		if(!a.getFeature()[0].type().equals(b.getFeature()[2].type())){
			return false;
		}
		if(!a.getFeature()[1].type().equals(b.getFeature()[1].type())){
			return false;
		}
		if(!a.getFeature()[2].type().equals(b.getFeature()[0].type())){
			return false;
		}
		return true;
	}

	/**
	 * Marks the end of turn for the current player who's now removed from the front of the ArrayList and added at the back. 
	 * Also gets a new random Tile from the Inventory
	 */
	public void endTurn(){
		_players.add(_players.remove(0));
		_currentTile = _inventory.getRandomTile();
	}

	/**
	 * this method checks whether placing the current Tile at the specified coordinates on the Board violates any rules. 
	 * If all conditions are met, it places the Tile on the Board, ends the turn and notifies the UI. 
	 * @param x an int representing the x-coordinate
	 * @param y an int representing the y-coordinate
	 */
	public void move(int x, int y) {
		if(checkAdjacent(x, y) == true && checkLegalPlacement(x, y) == true){
			_board.placeTile(x, y, _currentTile);
			_adjacentError = false;
			_legalPlacementError = false;
			endTurn();
		}
		else if(checkAdjacent(x, y) == false){
			_adjacentError = true;
		}
		else if(checkLegalPlacement(x,y) == false){
			_adjacentError = false;
			_legalPlacementError = true;
		}
		setChanged();
		notifyObservers();

	}

	/**
	 * This method compares the edges of the Tiles adjacent to the current tile with the edges of the current Tile to determine legality of the placement
	 * @param x an int representing the x-coordinate 
	 * @param y an int representing the y-coordinate
	 * @return a boolean true if the placement is legal, false otherwise
	 */
	private boolean checkLegalPlacement(int x, int y) {
		boolean left = false;
		boolean down = false;
		boolean right = false;
		boolean up = false;
		if(x+1<=_board.getWidth()-1){
			if(!_board.isEmpty(x+1, y)){
				right = compareEdges(_currentTile.getRight(), _board.getTile(x+1, y).getLeft());
			}
			else{
				right = true;
			}
		}
		else{
			right = true;
		}
		if(y+1<=_board.getLength()-1){
			if(!_board.isEmpty(x, y+1)){
				down = compareEdges(_currentTile.getBottom(), _board.getTile(x, y+1).getTop());
			}
			else{
				down = true;
			}
		}
		else{
			down = true;
		}
		if(x-1>=0){
			if(!_board.isEmpty(x-1, y)){
				left = compareEdges(_currentTile.getLeft(), _board.getTile(x-1, y).getRight());
			}
			else{
				left = true;
			}
		}
		else{
			left = true;
		}
		if(y-1>=0){
			if(!_board.isEmpty(x, y-1)){
				up = compareEdges(_currentTile.getTop(), _board.getTile(x, y-1).getBottom());
			}
			else{
				up = true;
			}
		}
		else{
			up = true;
		}
		return up && left && down && right;
	}

	/**
	 * this method refreshes the UI
	 */
	public void updateUI(){
		setChanged();
		notifyObservers();
	}

	/**
	 * this method checks whether there is at least one Tile adjacent to the Tile being placed  
	 * @param x an int representing the x-coordinate
	 * @param y an int representing the y-coordinate
	 * @return a boolean true if there's an adjacent Tile, false otherwise
	 */
	private boolean checkAdjacent(int x, int y) {
		boolean left = false;
		boolean down = false;
		boolean right = false;
		boolean up = false;
		if(x+1<=_board.getWidth()-1){
			if(!_board.isEmpty(x+1, y)){
				right = true;
			}
		}
		if(y+1<=_board.getLength()-1){
			if(!_board.isEmpty(x, y+1)){
				down = true;
			}
		}
		if(x-1>=0){
			if(!_board.isEmpty(x-1, y)){
				left = true;
			}
		}
		if(y-1>=0){
			if(!_board.isEmpty(x, y-1)){
				up = true;
			}
		}
		return left || down || right || up;
	}

}
