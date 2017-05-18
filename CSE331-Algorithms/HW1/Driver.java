import java.util.ArrayList;
import java.util.HashMap;

/**
 * The driver for homework one.
 * CSE 331
 */
public class Driver {

    // The following variables might need to be changed.
    private static String input_filename;
    private static long student_time = 0;

	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("Please provide the input filepath as the first argument");
            return;
		}

        input_filename = args[0];

    System.out.println("=======================================================================================================");
        System.out.println("Reading file for Input" + input_filename);
        
        // Read the file into HW1_Utils object
        HW1_Utils utils = new HW1_Utils(input_filename);
        utils.readFile();

        long startTime = System.nanoTime();

        // Load the preferences
        HashMap<Integer, ArrayList<Integer>> men = utils.men();
        HashMap<Integer, ArrayList<Integer>> women = utils.women();

        // Run student code
        HW1_Student_Solution student_class = new HW1_Student_Solution(men.size(), men, women);
        ArrayList<ArrayList<Marriage>> student_return = student_class.outputStableMatchings();
    
        long endTime = System.nanoTime();
        long student_time = (endTime - startTime);
        student_return = utils.sortMatchingList(student_return);
        System.out.println("Your solution:");
    System.out.println("=======================================================================================================");
        System.out.println(student_return.size());
        for(ArrayList<Marriage> match: student_return){
            System.out.println(match.toString());
            System.out.println("=======================================================================================================");
            

        }
        System.out.println("Total time taken= " + Long.toString(student_time) + " ns");

        

        
        

	}

}
