/**
 * For use in CSE 331. This is a convenient class used to organize pairings. The output of your hw will be an
 * ArrayList of Match.
 *
 * You can access the hospital or student of a match by calling match.getHospital(), or match.getStudent()
 */

public class Match {
	public Integer _hospital;
	public Integer _student;
	Match(Integer hospital, Integer student) {
		_hospital = hospital;
		_student = student;
	}
	public String toString() {
		return "(" + _hospital + ", " + _student + ")";
	}
	
	public Integer getHospital(){
		return this._hospital;
	}
	
	public Integer getStudent(){
		return this._student;
	}
	
	/**
	 * used to compare if two matches are the same
	 * @param match that will be compared
	 * @return true if they share the same hospital and student
	 */
	public boolean equals(Match aMatch) {
		return ((this._hospital.equals(aMatch.getHospital())) && (this._student.equals(aMatch.getStudent()))) ;
	}
}