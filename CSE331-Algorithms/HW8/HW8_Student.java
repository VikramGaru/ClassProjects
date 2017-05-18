import java.util.ArrayList;
import java.util.HashSet;

/**
 * The student template.
 * For use in HW8
 */
public class HW8_Student {

    /**
     * This is the adjacency matrix representation for the graph.
     * Each ArrayList represents a row, with the values contained representing the value in that row and column.
     */
    private ArrayList<ArrayList<Integer>> adj_matrix;
    private int[] _distance;

    /**
     * This does not need to be changed. It is merely the constructor.
     * @param adj_matrix The adjacency matrix representation of the graph.
     */
    public HW8_Student (ArrayList<ArrayList<Integer>> adj_matrix) { 
    	this.adj_matrix = adj_matrix;
    	_distance=new int[adj_matrix.size()];
    }

    /**
     * You  will fill this out.
     * We expect you to find out the minimum spanning tree. You may choose your root arbitrarily.
     * The int[] contained will represent this tree. Each value will be the parent of the node with the index value.
     * For example, if output[7] = 12, then node 7 has 12 as its parent. For the root node, it should have a value of
     * -1.
     * @return a representation of the MST.
     */
    public int[] output_edges() {
    	int [] output=new int[adj_matrix.size()];
    	HashSet<Integer> visited=new HashSet<Integer>();
    	for(int i=0;i<adj_matrix.size();i++){
    		_distance[i]=1000;
    		output[i]=-1;
    	}
    	int nextNode=0;
    	_distance[0]=0;
    	output[0]=-1;
    	while(visited.size()!=adj_matrix.size()){
    		if(!visited.contains(nextNode)){
    			visited.add(nextNode);
    			ArrayList<Integer> weights=adj_matrix.get(nextNode);
    			int min=1001;
    			for(int i=0;i<weights.size();i++){
    				if(weights.get(i)!=-1 && !visited.contains(i) && _distance[i]>weights.get(i)){
    						_distance[i]=weights.get(i);
    						output[i]=nextNode;
    				}
    			}
    			for(int i=0;i<_distance.length;i++){
    				if(_distance[i]<min && !visited.contains(i)){
						min=_distance[i];
		    			nextNode=i;
					}
    			}
    		}
    	}
        return output;
    }
}