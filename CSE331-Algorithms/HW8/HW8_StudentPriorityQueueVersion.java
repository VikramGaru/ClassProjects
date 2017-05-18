import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class HW8_StudentPriorityQueueVersion {
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
	public HW8_StudentPriorityQueueVersion (ArrayList<ArrayList<Integer>> adj_matrix) { 
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
	        Comparator<Integer> cmp=new Compare();
	        PriorityQueue<Integer> toVisit=new PriorityQueue<Integer>(1,cmp);
	        toVisit.add(0);
	        _distance[0]=0;
	        output[0]=-1;
	        while(visited.size()!=adj_matrix.size()){
	            int nextNode=toVisit.remove();
	            if(!visited.contains(nextNode)){
	                visited.add(nextNode);
	                toVisit=new PriorityQueue<Integer>(adj_matrix.size(),cmp);
	                ArrayList<Integer> weights=adj_matrix.get(nextNode);
	                for(int i=0;i<weights.size();i++){
	                    if(!visited.contains(i)){
	                        if(_distance[i]>weights.get(i) && weights.get(i)!=-1 ){
	                            _distance[i]=weights.get(i);
	                            output[i]=nextNode;
	                        }
	                        toVisit.add(i);
	                    }
	                }
	            }
	        }
	        return output;
	    }
	    class Compare implements Comparator<Integer>{
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

