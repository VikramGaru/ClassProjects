import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class Driver {

    private static Integer startNode;
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
        HW4_Student student = new HW4_Student(startNode, graph);
        System.out.println("Starting node " + startNode);
        System.out.println("Nodes: " + graph.keySet().size());

        //Get the distances
        int[] distances = student.outputDistances();

        System.out.println(Arrays.toString(distances));
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
            startNode = Integer.valueOf(bufferedReader.readLine());
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
