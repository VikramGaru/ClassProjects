import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

/**
 * The driver for homework three.
 * CSE 331
 */
public class Driver {

    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Please provide the input filepath as the first argument");
            return;
        }

        String input_filename = args[0];

        System.out.println("=======================================================================================================");
        System.out.println("Reading file for Input" + input_filename);

        // read the file to get the vector
        int[] vector = readFile(input_filename);


        long startTime = System.nanoTime();

        // Run student code
        HW3_Student_Solution student_class = new HW3_Student_Solution(vector);
        int[] student_return = student_class.outputVector();

        long endTime = System.nanoTime();
        long student_time = (endTime - startTime);



        System.out.println("Your solution:");
        System.out.println("=======================================================================================================");
        System.out.println(Arrays.toString(student_return));


        System.out.println("Total time taken= " + Long.toString(student_time) + " ns");






    }

    /**
     * This will create a vector object from a file. The file is expected to have one line
     * with one value, representing n, and then there will be n lines, each with one element,
     * signifying the vector.
     *
     * eg. A vector of size eight will have nine lines, the first being 8, the remaining being
     * the values of the vector.
     */
    public static int[] readFile(String inFile){
        int[] vector = null;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inFile));

            int n = Integer.parseInt(bufferedReader.readLine());

            vector = new int[n];
            for (int i = 0; i < n; i ++) {
                vector[i] = Integer.parseInt(bufferedReader.readLine());
            }
            bufferedReader.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return vector;
    }

}
