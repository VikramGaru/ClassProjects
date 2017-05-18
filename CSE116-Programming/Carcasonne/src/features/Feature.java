package features;


public class Feature {

	/**
	 * an instance variable of type boolean which indicates whether the Tile has a shield. 
	 * useful for later stages that involve scoring
	 */
	private boolean _hasShield;
	public Feature(){
		
	}
	/**
	 * this method returns the type of the Feature. Used to check legal Tile placement. 
	 * the subclasses override this method to return their respective types
	 * @return a String representing the type of the Feature
	 */
	public String type(){
		return "";
	}
	
	/**
	 * an accessor for the _hasShield instance variable 
	 * @return a boolean
	 */
	public boolean hasShield(){
		return _hasShield;
	}
	
	/**
	 * a mutator that allows to set the shield for a Tile
	 * @param b true if there's a shield, false otherwise
	 */
	public void setShield(boolean b){
		_hasShield = b;
	}
}


