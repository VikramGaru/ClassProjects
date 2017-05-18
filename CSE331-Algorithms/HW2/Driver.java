import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


/**
 * Driver for HW2
 */
public class Driver {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Please provide the input filepath as the first argument and the output filepath as the second.");
		} else {
			String inputfilename = args[0];
			String outputfilename = args[1];
			try {
				@SuppressWarnings("resource")
				BufferedReader bufferedReader = new BufferedReader(new FileReader(inputfilename));
				int nHospital = Integer.valueOf(bufferedReader.readLine());
				int nStudent = Integer.valueOf(bufferedReader.readLine());
				HashMap<Integer, ArrayList<Integer>> hospitalList = new HashMap<Integer, ArrayList<Integer>>();
				HashMap<Integer, ArrayList<Integer>> studentList = new HashMap<Integer, ArrayList<Integer>>();
				
				for (int i = 1; i <= nHospital; i++) {
					String line = bufferedReader.readLine();
					ArrayList<Integer> preferences = new ArrayList<Integer>();
					for (String part: line.split("\\s+")) {
						preferences.add(Integer.valueOf(part));
					}
					hospitalList.put(i, preferences);
				}

				for (int i = 1; i <= nStudent; i++) {
					String line = bufferedReader.readLine();
					ArrayList<Integer> preferences = new ArrayList<Integer>();
					for (String part: line.split("\\s+")) {
						preferences.add(Integer.valueOf(part));
					}
					studentList.put(i, preferences);
				}

				HW2_Student_Solution problem = new HW2_Student_Solution(nHospital, nStudent, hospitalList, studentList);
				ArrayList<Match> matches = problem.getMatches();

				FileWriter f = new FileWriter(outputfilename);
					String  outputString = "";
					Collections.sort(matches, new Comparator<Match>() {
						public int compare(Match first, Match second){
							return (first._hospital).compareTo(second._hospital);
						}
					});
					for (int i = 0; i< matches.size(); i++) {
						outputString += matches.get(i).toString() + "\n";
					}
				f.write(outputString);
				f.close();

			}
			catch (FileNotFoundException e) {
				System.err.println("Unable to open the file " + inputfilename);
			}
			catch (IOException e) {
				System.err.println("Unable to read the file " + inputfilename);
			}
			catch (NumberFormatException e) {
				System.err.println("File not in proper format " + inputfilename);
			}
		}

	}
}
