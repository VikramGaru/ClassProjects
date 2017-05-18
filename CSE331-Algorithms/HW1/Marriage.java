/**
 * For use in CSE 331. This is a convenient class used to organize pairings. The output of your hw will be an
 * ArrayList of Marriages.
 *
 * You can access the man or woman of a marriage by calling marriage._man, or marriage._woman
 */
public class Marriage {
    public Integer _man;
    public Integer _woman;

    Marriage(Integer man, Integer woman) {
        _man = man;
        _woman = woman;
    }

    /**
     * Used to compare if two marriages match.
     * @param compare The other Marriage that this is being compared to.
     * @return true if they share the same man & woman
     */
    public boolean equals(Marriage compare){
        return (_man == compare._man) && (_woman == compare._woman);
    }

    public String toString() {
        return "(" + _man + ", " + _woman + ")";
    }
}
