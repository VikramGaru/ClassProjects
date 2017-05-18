import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * The driver for hw6
 */

public class Driver {

    public static void main(String[] args){
        if(args.length != 1){
            System.err.println("Please provide the input filepath as the first argument");
            return;
        }

        //Get the input filename from command line arguments and parse it
        String inputFile = args[0];
        ArrayList<int[]> rallies = readFile(inputFile);

        //Create an instance of student class
        HW6_StudentSolution student = new HW6_StudentSolution(rallies);

        //Get the schedule
        ArrayList<int[]> schedule = student.getSchedule();

        System.out.println();
        for(int[] p : schedule) {
            System.out.print("[ ");
            for(int i : p){
                System.out.print(i + " ");
            }
            System.out.print("]\n");
        }
    }

    /**
     * This function will read a file and return the corresponding list of rallies. Note that your final answer will
     * require rally ids. These are assumed to be the index of each rally.
     *
     * @param inputFile the file to be read
     * @return an ArrayList of tuples: (rally duration, rally deadline)
     */
    private static ArrayList<int[]> readFile(String inputFile) {
        ArrayList<int[]> rallies = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));

            int number_of_rallies = Integer.parseInt(bufferedReader.readLine());

            for (int i = 0; i < number_of_rallies; i++) {
                String[] duration_and_deadline = (bufferedReader.readLine()).split(" ");
                rallies.add(new int[]{Integer.parseInt(duration_and_deadline[0]), Integer.parseInt(duration_and_deadline[1])});
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rallies;
        }
    }
}
