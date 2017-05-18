import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * A general utility class.
 * For HW1, this will:
 *      - read the file, and parse out the preference lists (readfile())
 *      - return said lists (men(), women())
 *      - sort a list of matchings
 *      - compare two sorted lists for equality.
 * For use in CSE 331
 */
public class HW1_Utils {

    private String input_filename;

    private HashMap<Integer, ArrayList<Integer>> women = new HashMap<>();
    private HashMap<Integer, ArrayList<Integer>> men = new HashMap<>();


    public HW1_Utils(String in){
        input_filename = in;
    }

    public HashMap<Integer, ArrayList<Integer>> men()   { return men; }
    public HashMap<Integer, ArrayList<Integer>> women() { return women; }

    /**
     * This function reads the file given in the constructor, parses the information, and then stores the preference
     * lists for future use.
     */
    public void readFile() {
        BufferedReader bufferedReader = null;
        try {

            bufferedReader = new BufferedReader(new FileReader(input_filename));

        } catch (FileNotFoundException e) {
            System.err.println("Unable to open the file " + input_filename);
            e.printStackTrace();
        }

        try {
            int numberOfMenAndWomen = Integer.valueOf(bufferedReader.readLine());

            for (int i = 1; i <= numberOfMenAndWomen; i++) {
                ArrayList<Integer> preferences = new ArrayList<>();
                String line = bufferedReader.readLine();
                for (String part : line.split("\\s+")) {
                    preferences.add(Integer.valueOf(part));
                }
                women.put(i, preferences);
            }
            for (int i = 1; i <= numberOfMenAndWomen; i++) {
                ArrayList<Integer> preferences = new ArrayList<>();
                String line = bufferedReader.readLine();
                for (String part : line.split("\\s+")) {
                    preferences.add(Integer.valueOf(part));
                }
                men.put(i, preferences);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This sorts a list.
     * @param list a list of matchings
     * @return the sorted list
     */
    public ArrayList<ArrayList<Marriage>> sortMatchingList(ArrayList<ArrayList<Marriage>> list) {

        // First, we sort each matching by woman
        for(int i = 0; i < list.size(); i++){
            Collections.sort(list.get(i), new Comparator<Marriage>() {
                public int compare(Marriage first, Marriage second){
                    return (first._woman).compareTo(second._woman);
                }
            });
        }

        // Then, we sort the entire list by the order of the men in each matching
        Collections.sort(list, new Comparator<ArrayList<Marriage>>(){
            public int compare(ArrayList<Marriage> first, ArrayList<Marriage> second){
                for(int i = 0; i < first.size(); i++){
                    if(first.get(i)._man > second.get(i)._man || first.get(i)._man < second.get(i)._man){
                        return (first.get(i)._man).compareTo(second.get(i)._man);
                    }
                }
                return 0;
            }
        });
        return list;
    }

    /**
     * This compares two lists of matchings for equality.
     * @param student_ans list given by the student
     * @param accepted list given by instructors
     * @return true if they are equal, false if not
     */
    public boolean compare (ArrayList<ArrayList<Marriage>> student_ans, ArrayList<ArrayList<Marriage>> accepted) {

        if (student_ans == null) return false;
        student_ans = sortMatchingList(student_ans);
        accepted = sortMatchingList(accepted);
        if(student_ans.size() != accepted.size()) return false;
        for(int matching = 0; matching < student_ans.size(); matching ++) {
            for(int marriage = 0; marriage < student_ans.get(matching).size(); marriage ++) {
                if(!student_ans.get(matching).get(marriage).equals(accepted.get(matching).get(marriage))) return false;
            }
        }
        return true;
    }
}
