package code;

import java.io.*;

 
public class FileSave{

	private String save = "saved";
	private String [] saveInformation = {save};
	
	
 
  public FileSave(){
	  
	 saveFile("save.txt");
	  
  }
 /* private void updateFile(){
	  
  }*/
  public void readFile(String filePath){
	  File inputFile;
	  BufferedReader inputReader;
	  
	  try{
		  inputFile = new File(filePath);
		  
		  inputReader = new BufferedReader(new FileReader(inputFile));
		  
		  for(int i = 0; i<saveInformation.length;i++){
			  saveInformation[i] = inputReader.readLine();

		  }
		  System.out.println(new File("save.txt"));
		  
		  inputReader.close();
		  
	  }catch(Exception e){
		  e.printStackTrace();
	  }
  }
  
  public void saveFile(String filePath){
	  File outputFile;
	  BufferedWriter outputWriter;
	  
	  try{
		  outputFile = new File(filePath);
		  
		  outputWriter = new BufferedWriter(new FileWriter(outputFile));
		  
		  for(int i = 0; i<saveInformation.length;i++){
			  outputWriter.write(saveInformation[i]+"\n");
		  }
		  outputWriter.close();
		  
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  
 } 
 
 
}