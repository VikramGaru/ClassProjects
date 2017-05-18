/**
 * For use in CSE 331
 * Template for hw3
 */
public class HW3_Student_Solution {

    private int[] in_vector;
    private int[] out_vector;

    /**
     * The constructor simply sets up the necessary data structures.
     * The grader for the homework will first call this class and pass the necessary variables.
     * There is no need to edit this constructor.
     *
     * @param in_vector the vector used to multiply against the matrix
     */
    public HW3_Student_Solution(int[] in_vector) {
        this.in_vector = in_vector;
        this.out_vector=new int[in_vector.length];
    }

    /**
     * This method must be filled in by you. You may add other methods and subclasses as you see fit,
     * but they must remain within the HW3_Student_Solution class.
     * @return Your resulting vector.
     */
    public int[] outputVector() {
    	int limit=in_vector.length;
    	int sum=0;
    	for(int i=0;i<limit;i++){
    		sum=sum+in_vector[i];
    	}
    	for(int i=0;i<limit;i++){
    		if(i!=0){
    			sum=sum-in_vector[i-1];
    			out_vector[i]=sum;
    			continue;
    		}
    		out_vector[i]=sum;
    	}
        return out_vector;
    }
}
