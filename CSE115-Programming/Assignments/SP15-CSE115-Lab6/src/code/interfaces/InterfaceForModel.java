package code.interfaces;

public interface InterfaceForModel {
	
	/**
	 * Assigns randomly generated int values to the 
	 * variables of the model.
	 */
	public void randomizeModel();

	/**
	 * Accessor method for the first of the four int
	 * variables of the model.
	 */
	public int get00();

	/**
	 * Accessor method for the second of the four int
	 * variables of the model.
	 */
	public int get01();

	/**
	 * Accessor method for the third of the four int
	 * variables of the model.
	 */
	public int get10();

	/**
	 * Accessor method for the fourth of the four int
	 * variables of the model.
	 */
	public int get11();

	/**
	 * Method that associates the a view as an observer
	 * of the model.  Being an observer implies receiving
	 * notifications when the data in the model changes.
	 */
	public void addObserver(InterfaceForView view);
}