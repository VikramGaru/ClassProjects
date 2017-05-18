import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class HW5_Student {
    private HashMap<Integer, ArrayList<Integer>> graph;
    public HW5_Student(HashMap<Integer, ArrayList<Integer>> g){
        graph = g;
    }
    public ArrayList<Integer> findCycle(){
        //Your code goes here
    	int helper=0; //Helps to track the nodes which are not connected
    	ArrayList<Integer> output=new ArrayList<Integer>(); //Output all nodes forming the cycle
    	Stack<Integer> trackNode=new Stack<Integer>(); //Used to keep track of cycles
    	Stack<Integer> nextItem=new Stack<Integer>(); //Stack of the node the graph is currently visiting
    	nextItem.push(0); //Assuming the graph has at least one node
    	Set<Integer> visited=new HashSet<Integer>(); //Set of nodes that are visited
    	while(!nextItem.isEmpty()){
    		ArrayList<Integer> twoNodes=new ArrayList<Integer>(); //Gives the two nodes which form a cycle
    		int count=0; //Counts if an edge is connected to more than 2 visited edges cause it will mean that there is a cycle
    		int toVisit=nextItem.pop(); //Gives the next item to visit
    		visited.add(toVisit);
    		trackNode.push(toVisit);
    		ArrayList<Integer> allEdges=graph.get(toVisit); //List of all edges from a node
    		if(allEdges.size()==1){
    			while(trackNode.peek()!=helper){
    				trackNode.pop();
    			}
    			continue;
    		}
    		else if(allEdges.size()>2){
    			helper=toVisit;
    		}
    		for(int i=0;i<allEdges.size();i++){
    			if(!visited.contains(allEdges.get(i))){
    				nextItem.push(allEdges.get(i));
    			}
    			else{
    				count=count+1;
    				twoNodes.add(allEdges.get(i));
    			}
    		}
    		if(count==2){
    			output.add(trackNode.pop());
    			int lastNode=twoNodes.get(0);
    			if(lastNode==trackNode.peek()){
    				lastNode=twoNodes.get(1);
    			}
    			do{
    				output.add(trackNode.peek());
    			}while(trackNode.pop()!=lastNode);
    			break;
    		}
    		if(nextItem.size()==0 && visited.size()!=graph.size()){
    			Set<Integer> a=graph.keySet();
    			Iterator<Integer> it=a.iterator();
    			while(true){
    				int next=it.next();
    				if(!visited.contains(next)){
    					nextItem.push(next);
    					break;
    				}
    			}
    		}
    	}
	return output;
    }
}