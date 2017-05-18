import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class HW4_Student {

    private int startNode;
    private HashMap<Integer, ArrayList<Integer>> graph;
    public HW4_Student(int node, HashMap<Integer, ArrayList<Integer>> g){
        startNode = node;
        graph = g;
    }

    public int[] outputDistances(){
        //Your code goes here
    	int[] output=new int[graph.size()];
    	for(int i=0;i<graph.size();i++){
    		output[i]=-1;
    	}
    	Queue<Integer> nextItem=new LinkedList<Integer>(); //Has the next unvisited item
    	nextItem.add(startNode);
    	int distance=0; //Distance from the startNode
    	int flag=0;
    	int j=0;
    	while(!nextItem.isEmpty()){ //All nodes an edge is directly connected
    		if(output[nextItem.peek()]==-1){
    			ArrayList<Integer> edges=graph.get(nextItem.peek()); //All edges of the graph
    			output[nextItem.peek()]=distance;
    			for(int i=0;i<edges.size();i++){
        			if(output[edges.get(i)]==-1){
        				nextItem.add(edges.get(i));
        			}
        		}
    		}
    		nextItem.remove();
    		if(j==flag){
    			distance=distance+1;
    			j=0;
    			flag=nextItem.size();
    		}
    		j=j+1;
    	}
        return output;
    }
}
