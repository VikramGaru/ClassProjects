import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * For use in CSE 331 HW2.
 * This is the class you will be editing and turning in. It will be timed against our implementation
 * NOTE that if you declare this file to be in a package, it will not compile in Autolab
 */
public class HW2_Student_Solution {
	private int _nHospital;
	private int _nStudent;
	
	// The following represent the preference list of hospitals and students.
  // The KEY represents the integer representation of a given hospital or student.
  // The VALUE is a list, from most preferred to least. 
  // For hospital, first element of the list is number of available slots
	private HashMap<Integer, ArrayList<Integer>> _hospitalList;
	private HashMap<Integer, ArrayList<Integer>> _studentList;
	//private Set<Integer> _allStudent;
	private Set<Integer> _allHospital;
	private ArrayList<Match> _allMatch;
	private ArrayList<Integer> _proposedTill;
	private HashMap<Integer,Integer> _s2h;
	private HashMap<Integer,HashSet<Integer>> _h2s;

	/**
	 * The constructor simply sets up the necessary data structures.
   * The grader for the homework will first call this class and pass the necessary variables.
   * There is no need to edit this constructor.
	 * @param m Number of hospitals
	 * @param n Number of students
	 * @param A map linking each hospital with its preference list
	 * @param A map linking each student with their preference list
	 * @return
	 */
	public HW2_Student_Solution(int m, int n, HashMap<Integer, ArrayList<Integer>> hospitalList, HashMap<Integer, ArrayList<Integer>> studentList) {
		_nHospital = m;
		_nStudent = n;
		_hospitalList = hospitalList;
		_studentList = studentList;
		_allMatch=new ArrayList<Match>(m);
		_proposedTill=new ArrayList<Integer>(m); //Pointer to the student till whom the hospital has asked on its preference list
		//_allStudent=_studentList.keySet();//Set Containing all Students
		//_allHospital=_hospitalList.keySet();//Set containing all Hospitals
		_allHospital=new HashSet<Integer>();
		_s2h=new HashMap<Integer,Integer>(); //Map containing the hospital the student is currently engaged with
		_h2s=new HashMap<Integer,HashSet<Integer>>(); //Map containing all the students engaged with a hospital
		for(int i=0;i<m;i++){
			_proposedTill.add(1);
			_allHospital.add(i+1);
		}
	}
	/**
	 * This method must be filled in by you. You may add other methods and subclasses as you see fit,
   * but they must remain within the HW1_Student_Solution class.
	 * @return Your stable matches
	 */
	public ArrayList<Match> getMatches() {
		//System.out.println("HI");
		Iterator<Integer> it=_allHospital.iterator();
		while(it.hasNext()){
			int currentHospital=it.next(); //currentHospital=gives the hospital which still has vacancy
			//System.out.println(_allHospital);
			//System.out.println(_hospitalList);
			//it.remove();
			ArrayList<Integer> cHList=_hospitalList.get(currentHospital); //Gives the preference list of the current Hospital
			int nVacancy=cHList.get(0); //The number of vacancy in current hospital
			//System.out.println("Vacancy="+nVacancy);
			int propC=_proposedTill.get(currentHospital-1); //Returns the index till where the current hospital has asked in its pref list
			//System.out.println("Proposed till="+propC);
			_proposedTill.set(currentHospital-1, propC+1); //Updates the student to whom the current hospital has already asked
			int nStudent=cHList.get(propC); //New student current hospital didn't ask till now
			ArrayList<Integer> cSList=_studentList.get(nStudent); //New student's preference list
			if(_s2h.containsKey(nStudent)){ //Already student is engaged with another hospital
				int currentHS=_s2h.get(nStudent); //Current hospital student is engaged to
				if(cSList.indexOf(currentHS)>cSList.indexOf(currentHospital)){ //Student gets a better offer
					_s2h.put(nStudent, currentHospital); //Update the hospital the student is engaged with
					HashSet<Integer> nList=_h2s.get(currentHS);
					nList.remove(nStudent);
					_h2s.put(currentHS,nList); //Update the list of doctors in the hospital
					//System.out.println(_hospitalList);
					ArrayList<Integer> cHL=_hospitalList.get(currentHS);
					//System.out.println(cHL);
					int nV=cHL.get(0)+1; //Update the vacancies in the other hospital
					cHL.set(0, nV);
					_hospitalList.put(currentHS,cHL); //Update the vacancies in the other hospital
					//System.out.println(_hospitalList);
					if(!_allHospital.contains(currentHS)){ //if the hospital is not in the list add it back again
						_allHospital.add(currentHS);
					}
					if(_h2s.containsKey(currentHospital)){
						HashSet<Integer> list=_h2s.get(currentHospital);
						list.add(nStudent);
						_h2s.put(currentHospital,list);
					}
					else{
						HashSet<Integer> list=new HashSet<Integer>();
						list.add(nStudent);
						_h2s.put(currentHospital, list);
					}
					nVacancy=nVacancy-1;
					_hospitalList.get(currentHospital).set(0,nVacancy);
				}
			}
			else{ //Student is not engaged with any hospital
				_s2h.put(nStudent, currentHospital);
				//System.out.println(_s2h);
				if(_h2s.containsKey(currentHospital)){
					HashSet<Integer> list=_h2s.get(currentHospital);
					list.add(nStudent);
					_h2s.put(currentHospital,list);
				}
				else{
					HashSet<Integer> list=new HashSet<Integer>();
					list.add(nStudent);
					_h2s.put(currentHospital, list);
				}
				nVacancy=nVacancy-1;
				_hospitalList.get(currentHospital).set(0,nVacancy);
			}
			if(nVacancy==0){ //No vacancy all spots filled
				//System.out.println("HI");
				//it.remove();
				_allHospital.remove(currentHospital);
				it=_allHospital.iterator();
					//_proposedTill.set(currentHospital,propC); //Change the value of the pointer to the preference list
					//_hospitalList.get(currentHospital).set(0,0); //Make the number of vacancy in current hospital=0
			}
			else{
				it=_allHospital.iterator();
			}
		}
		//System.out.println(_h2s);
		for(int i=1;i<=_nHospital;i++){
			HashSet<Integer> student=_h2s.get(i);
			Iterator<Integer> it1=student.iterator();
			while(it1.hasNext()){
				_allMatch.add(new Match(i,it1.next()));
			}
		}
                // Returns an empty ArrayList for now	
		return _allMatch;
	}
}
