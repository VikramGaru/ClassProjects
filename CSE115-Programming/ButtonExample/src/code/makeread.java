package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class makeread {
	public static void make(){
			try {
				String s="1000";
				BufferedWriter bw=new BufferedWriter(new FileWriter("//home//csdue//vikramga//workspace//file.txt"));
				bw.write(s);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public static void readfile(){
			int a=0;
			try {
				BufferedReader br=new BufferedReader(new FileReader("//home//csdue//vikramga//workspace//file.txt"));
				try {
					a=Integer.parseInt(br.readLine());
					br.close();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(a);
		}
}

