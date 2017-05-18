package code;

import features.Feature;

public class Edge {

	/**
	 * an instance variable of type Feature[] representing the features of an edge
	 */
	private Feature[] _myFeatures;
	/**
	 * The constructor creates a Feature array of size 3 and assigns the three parameters to their respective positions in the array
	 * @param one the first feature
	 * @param two the second feature
	 * @param three the third feature
	 */
	public Edge(Feature one, Feature two, Feature three){
		_myFeatures = new Feature[3];
		_myFeatures[0] = one;
		_myFeatures[1] = two;
		_myFeatures[2] = three;
	}
	
	/**
	 * An accessor method for the Feature[]
	 * @return the Features of the Edge in an array 
	 */
	public Feature[] getFeature(){
		return _myFeatures;
	}
}
