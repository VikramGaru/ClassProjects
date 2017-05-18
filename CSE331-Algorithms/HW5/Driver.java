import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class Driver {

    private static HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
    public static void main(String[] args){
        if(args.length != 1){
            System.err.println("Please provide the input filepath as the first argument");
            return;
        }
        //Get the input filename from command line arguments and parse it
        String inputFile = args[0];
        readFile(inputFile);

        //Create an instance of student class
        HW5_Student student = new HW5_Student(graph);
        System.out.println("Nodes: " + graph.keySet().size());

        //Get the distances
        ArrayList<Integer> cycle = student.findCycle();

        System.out.println(cycle);
    }

    private static void readFile(String inputFile){
        BufferedReader bufferedReader = null;
        try{
            bufferedReader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e){
            System.err.println("Unable to open the file " + inputFile);
            e.printStackTrace();
        }

        try{
            String line = null;
            Integer node = 0;
            while((line = bufferedReader.readLine()) != null){
                graph.put(node, new ArrayList<Integer>());
                String[] parts = line.trim().split("\\s+");
                for(String neighbor: parts){
                  Integer n = Integer.parseInt(neighbor);
                  graph.get(node).add(n);
                }
                node++;
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
