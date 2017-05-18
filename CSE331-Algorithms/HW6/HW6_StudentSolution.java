import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * The student solution
 * For use in CSE 331
 */
public class HW6_StudentSolution {

    private ArrayList<int[]> rallies;
    public HW6_StudentSolution(ArrayList<int[]> rallies) {
        this.rallies = rallies;
    }

    /**
     * You should fill this in on your own. Rallies is list of tuples, in the form (rally duration, rally deadline).
     * Your output should also be a list of tuples, of the form (rally id, start time of rally). If no possible
     * schedule exists, you should return an empty list.
     * @return
     */
    public ArrayList<int[]> getSchedule() {
    	HashMap<Integer,Stack<Integer>> s=new HashMap<Integer,Stack<Integer>>();
    	ArrayList<int[]> schedule = new ArrayList<>();
    	for(int i=0;i<rallies.size();i++){
    		int[]content=rallies.get(i);
    		if(s.containsKey(content[1])){
    			s.get(content[1]).push(content[0]);
    		}
    		else{
    			Stack<Integer> a=new Stack<Integer>();
    			a.push(i);
    			s.put(content[1], a);
    		}
    	}
    	List<Integer> mapKeys=new ArrayList<Integer>(s.keySet());
    	Collections.sort(mapKeys);
    	Iterator<Integer> it=mapKeys.iterator();
    	int start=0;
    	while(it.hasNext()){
    		int next=it.next();
    		Stack<Integer> con=s.get(next);
    		while(!con.isEmpty()){
    			int rally=con.pop();
    			int []sc=new int[2];
    			sc[0]=rally;
    			sc[1]=start;
    			start=start+rallies.get(rally)[0];
    			if(start>rallies.get(rally)[1]){
    				return new ArrayList<int[]>();
    			}
    			schedule.add(sc);
    		}
    	}
        return schedule;
    }
}
