import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class HW7_Student{

    private int _startNode;
    private int _endNode;
    private HashMap<Integer, ArrayList<Integer>> graph;
    private int[] _distance;
    private int[] _tree;
    public HW7_Student(int startNode, int endNode, HashMap<Integer, ArrayList<Integer>> g){
        _startNode = startNode;
        _endNode = endNode;
        graph = g;
        _distance=new int[graph.size()];
        _tree=new int[graph.size()];
        for(int i=0;i<graph.size();i++){
        	_distance[i]=(int)Double.POSITIVE_INFINITY;
        	_tree[i]=-1;
        }
    }
    public ArrayList<Integer> outputPath(){
        /*
         * Find the smallest weighted path between _startNode and _endNode
         * The first number of _graph's adjacency list is the weight of it's node
         */
    	ArrayList<Integer> output=new ArrayList<Integer>(); //ArrayList containing the output
    	HashSet<Integer> visited=new HashSet<Integer>(); //Set of all visited nodes
    	Comparator<Integer> cmp=new compareTo();
    	PriorityQueue<Integer> toVisit=new PriorityQueue<Integer>(1000,cmp);
    	Stack<Integer> tracker=new Stack<Integer>(); //Stack to get all the shortest path
    	toVisit.add(_startNode);
    	_distance[_startNode]=0;
    	_tree[_startNode]=-2;
    	while(!toVisit.isEmpty()){
    		int nextNode=toVisit.remove(); //The next node to visit
    		if(!visited.contains(nextNode)){
    			visited.add(nextNode); //Add the node as visited
    			ArrayList<Integer> edges=graph.get(nextNode); //ArrayList containing all the edges connected to the current node
    			for(int i=1;i<edges.size();i++){
    				int dist=_distance[nextNode]+graph.get(edges.get(i)).get(0);
    				if(_distance[edges.get(i)]==(int)Double.POSITIVE_INFINITY || _distance[edges.get(i)]>dist){
    					_distance[edges.get(i)]=dist;
    					_tree[edges.get(i)]=nextNode;
    				}
    				if(!visited.contains(edges.get(i))){
    					toVisit.add(edges.get(i));
    				}
    			}
    			if(nextNode==_endNode){
    				break;
    			}
    		}
    	}
    	int nextNode=_endNode;
    	if(_tree[nextNode]==-1){
    		return new ArrayList<Integer>();
    	}
    	while(nextNode!=_startNode){
    		tracker.push(nextNode);
    		nextNode=_tree[nextNode];
    	}
    	tracker.push(_startNode);
    	while(!tracker.isEmpty()){
    		output.add(tracker.pop());
    	}
        return output;
    }
    class compareTo implements Comparator<Integer>{
		@Override
		public int compare(Integer o1, Integer o2) {
			// TODO Auto-generated method stub
			if(_distance[o1]>_distance[o2]){
				return 1;
			}
			else if(_distance[o1]<_distance[o2]){
				return -1;
			}
			return 0;
		}
    }
}