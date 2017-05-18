import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * For use in CSE 331 HW1.
 * This is the class you will be editing and turning in. It will be timed against our implementation
 * NOTE that if you declare this file to be in a package, it will not compile in Autolab
 */
public class HW1_Student_Solution {

    private int _numberOfMenAndWomen;
    // The following represent the preference list for the men and women.
    // The KEY represents the integer representation of a given man or woman.
    // The VALUE is a list, from most preferred to least, of the member of the opposite gender.
    private HashMap<Integer, ArrayList<Integer>> _men;
    private HashMap<Integer, ArrayList<Integer>> _women;
    private ArrayList<ArrayList<Marriage>> _stableMatchings = new ArrayList<>();
    private ArrayList<Integer>_allMen;
    private ArrayList<Integer>_allWomen;
    /**
     * The constructor simply sets up the necessary data structures.
     * The grader for the homework will first call this class and pass the necessary variables.
     * There is no need to edit this constructor.
     *
     * @param n The number of men/women.
     * @param men A map linking each man (integer value) with their preference list.
     * @param women A map linking each woman (integer value) with their preference list.
     */
    public HW1_Student_Solution(int n, HashMap<Integer, ArrayList<Integer>> men, HashMap<Integer, ArrayList<Integer>> women){
        _numberOfMenAndWomen = n;
        _men = men;
        _women = women;
        _allMen=new ArrayList<Integer>(_numberOfMenAndWomen/2);
        _allWomen=new ArrayList<Integer>(_numberOfMenAndWomen/2);
        for(int i=1;i<=_numberOfMenAndWomen;i++){
        	_allMen.add(i);
        	_allWomen.add(i);
        }
    }
    /**
     * This method must be filled in by you. You may add other methods and subclasses as you see fit,
     * but they must remain within the HW1_Student_Solution class.
     * @return Your set of stable matches. Order does not matter.
     */
    //Produces all the permutations of men
    public ArrayList<ArrayList<Integer>> allPermutations(ArrayList<Integer>a){
    	System.out.println("All men="+a);
    	ArrayList<ArrayList<Integer>> b=new ArrayList<ArrayList<Integer>>(10000);
    	int n=a.size();
    	ArrayList<Integer> first=new ArrayList<Integer>(n);
    	for(int i=0;i<n;i++){
    		first.add(a.get(i));
    	}
    	b.add(first);
    	int swapId;
    	int [] permId = new int[n];
		for( int i=0; i<n; i++){
			permId[i]=0;
		}
		int j=1;
		while(j< n){
			if(permId[j]<j){
				if( j%2!=0){
					swapId =permId[j];
				}
				else{
					swapId=0;
				}
				int k=a.get(swapId);
				a.set(swapId, a.get(j));
				a.set(j, k);
				ArrayList<Integer> storage=new ArrayList<Integer>(n);
				for(int i=0;i<a.size();i++){
					storage.add(a.get(i));
				}
				b.add(storage);
				permId[j]++;
				j=1;
			}
			else
				permId[j++]=0;
		}
		//System.out.println("All Possibilities="+b);
		return b;
    }
    //Finds all stable pairs
    public ArrayList<ArrayList<Integer>> allStablePairs(){
    	//HW1_Student_Solution z=new HW1_Student_Solution();
    	ArrayList<ArrayList<Integer>> a=allPermutations(_allMen);//ArrayList of all combinations of men
    	ArrayList<Integer> allWomen=_allWomen;//ArrayList containing all women
    	ArrayList<ArrayList<Integer>> stablePair=new ArrayList<ArrayList<Integer>>(10);
    	for(int i=0;i<a.size();i++){
    		boolean stable=true;
    		ArrayList<Integer> combinations=a.get(i);//Iterating all possible combinations of men
    		for(int j=0;j<allWomen.size();j++){
    			int instability=0;//Flag
    			Integer currentMan=combinations.get(j);//Current man: Iterating in the combination list
    			Integer currentWP=allWomen.get(j);//Current woman with whom current man is engaged to
    			ArrayList<Integer> manChoice=_men.get(currentMan);//All choices of man
    			int indexOfCWoman=manChoice.indexOf(currentWP);//Index of current woman
    			for(int k=0;k<indexOfCWoman;k++){
    				Integer antWo=manChoice.get(k);//Man's preference of another woman
    				ArrayList<Integer> womanChoice=_women.get(antWo);//All choices of another woman
    				Integer antWoCM=combinations.get(antWo-1);//Another women's current partner
    				if(womanChoice.indexOf(currentMan)<womanChoice.indexOf(antWoCM)){
    					instability=1;
    					break;
    				}
    			}
    			if(instability==1){
    				stable=false;
    				break;
    			}
    		}
    		if(stable){
    			stablePair.add(combinations);
    		}
    	}
    	return stablePair;
    }
    public ArrayList<ArrayList<Marriage>> outputStableMatchings() {
    	ArrayList<ArrayList<Integer>> a=allStablePairs();
    	for(int i=0;i<a.size();i++){
    		ArrayList<Integer> b=a.get(i);
    		ArrayList<Marriage> m=new ArrayList<Marriage>(500);
    		for(int j=0;j<b.size();j++){
    			m.add(new Marriage(b.get(j),j+1));
    		}
    		_stableMatchings.add(m);
    	}
        return _stableMatchings;
    }
    /*public static void main(String args[]){
    	ArrayList<Integer> men=new ArrayList<Integer>();
    	ArrayList<Integer> women=new ArrayList<Integer>();
    	men.add(1);
    	
    	HW1_Student_Solution b=new HW1_Student_Solution();
    	System.out.println(b.allPermutations(a));
    	HW1_Student_Solution z=new HW1_Student_Solution();
    	ArrayList<ArrayList<Integer>> a=new ArrayList<ArrayList<Integer>>();//ArrayList of all combinations of men
    	ArrayList<Integer> allWomen=new ArrayList<Integer>();//ArrayList containing all women
    	ArrayList<ArrayList<Integer>> stablePair=new ArrayList<ArrayList<Integer>>();
    	for(int i=0;i<a.size();i++){
    		boolean stable=true;
    		ArrayList<Integer> combinations=a.get(i);//Iterating all possible combinations of men
    		for(int j=0;j<allWomen.size();j++){
    			int instability=0;//Flag
    			Integer currentMan=combinations.get(j);//Current man: Iterating in the combination list
    			Integer currentWP=allWomen.get(j);//Current woman with whom current man is engaged to
    			ArrayList<Integer> manChoice=z._men.get(currentMan);//All choices of man
    			int indexOfCWoman=manChoice.indexOf(currentWP);//Index of current woman
    			for(int k=0;k<indexOfCWoman;k++){
    				Integer antWo=allWomen.get(k);//Man's preference of another woman
    				ArrayList<Integer> womanChoice=z._women.get(combinations.get(antWo));//All choices of another woman
    				Integer antWoCM=combinations.get(antWo-1);//Another women's current partner
    				if(womanChoice.indexOf(currentMan)<womanChoice.indexOf(antWoCM)){
    					instability=1;
    					break;
    				}
    			}
    			if(instability==1){
    				stable=false;
    				break;
    			}
    		}
    		if(stable){
    			stablePair.add(combinations);
    		}
    	}
    }*/
}
