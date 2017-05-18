package code;

public class Board {

	/**
	 * An instance variable of type Tile[][] that represents the Board 
	 */
	private Tile[][] _tiles;
	/**
	 * An instance variable of type int that represents the width of the Board
	 */
	private int _width;
	/**
	 * An instance variable of type int that represents the length of the Board
	 */
	private int _length;
	/**
	 * An instance variable of type Model that represents the model of the game 
	 */
	private Model _model;
	/**
	 * The constructor creates the double array of Tiles and places the start tile in the middle of the Board
	 * @param m is a variable of type model that establishes an association relationship with _model
	 */
	public Board(Model m){
		_model = m;
		_width = 3;
		_length = 3;
		_tiles = new Tile[_width][_length];
		_tiles[1][1] = _model.getInventory().getRandomTile();
	}
	
	/**
	 * This method is an accessor for the double array of Tiles
	 * @return a double array of Tiles
	 */
	public Tile[][] getTiles(){
		return _tiles;
	}
	
	/**
	 * This method returns the width of the Board
	 * @return an int representing the width
	 */
	public int getWidth(){
		return _width;
	}
	
	/**
	 * This method returns the length of the Board
	 * @return an int representing the length
	 */
	public int getLength(){
		return _length;
	}
	
	/**
	 * This method places a Tile on the Board. 
	 * It also checks whether the position where the Tile has to be placed is empty or not.
	 * Calls the resize method if necessary. 
	 * @param x an int representing the x-coordinate of the Tile placement
	 * @param y an int representing the y-coordinate of the Tile placement
	 * @param t the Tile to be placed
	 */
	public void placeTile(int x, int y, Tile t){
		if(_tiles[x][y] == null){
			_tiles[x][y] = t;
			if(x == 0){
				_width = _width + 1;
				resize(0);
			}
			else if(x == _width-1){
				_width = _width + 1;
				resize(1);
			}
			else if(y == 0){
				_length = _length + 1;
				resize(2);
			}
			else if(y == _length-1){
				_length = _length + 1;
				resize(3);
			}
		}
	}
	
	/**
	 * Returns the Tile at coordinates x and y. Will return null if the position is empty.
	 * @param x an int representing the x-coordinate
	 * @param y an int representing the y-coordinate
	 * @return the Tile at x and y
	 */
	public Tile getTile(int x, int y){
		return _tiles[x][y];
	}
	
	/**
	 * Checks to see if the Board is empty at x and y
	 * @param x an int representing the x-coordinate
	 * @param y an int representing the y-coordinate
	 * @return a boolean that's true if the Board is empty and false if it isn't
	 */
	public boolean isEmpty(int x, int y){
		return _tiles[x][y]==null;
	}
	
	/**
	 * Resizes the board 
	 * @param position an int representing where to move over the existing Tiles of the board
	 */
	public void resize(int position){
		Tile[][] temp = _tiles;
		_tiles = new Tile[_width][_length];
		if(position == 0){
			for(int i = 0; i<_width-1; i++){
				for(int j = 0; j<_length; j++){
					_tiles[i+1][j] = temp[i][j];
				}
			}
		}
		else if(position == 1){
			for(int i = 0; i<_width-1; i++){
				for(int j = 0; j<_length; j++){
					_tiles[i][j] = temp[i][j];
				}
			}
		}
		else if(position == 2){
			for(int i = 0; i<_width; i++){
				for(int j = 0; j<_length-1; j++){
					_tiles[i][j+1] = temp[i][j];
				}
			}
		}
		else if(position == 3){
			for(int i = 0; i<_width; i++){
				for(int j = 0; j<_length-1; j++){
					_tiles[i][j] = temp[i][j];
				}
			}
		}
	}
}
