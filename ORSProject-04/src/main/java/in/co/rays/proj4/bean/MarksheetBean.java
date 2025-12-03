package in.co.rays.proj4.bean;

/**
 * MarksheetBean represents a student's marksheet in the system.
 * It extends BaseBean and contains details such as roll number,
 * student ID, name, and marks in physics, chemistry, and maths.
 * Also provides key-value representation for dropdowns.
 * 
 * Note: getKey() and getValue() methods are currently returning null
 * and should be implemented as needed.
 * 
 * @author Krati
 * @version 1.0
 */
public class MarksheetBean extends BaseBean {

    /** Roll number of the student */
    private String rollNo;

    /** ID of the student */
    private long studentId;

    /** Name of the student */
    private String name;

    /** Marks obtained in Physics */
    private Integer physics;

    /** Marks obtained in Chemistry */
    private Integer chemistry;

    /** Marks obtained in Maths */
    private Integer maths;

    /**
     * Returns the roll number of the student.
     * 
     * @return rollNo
     */
    public String getRollNo() {
        return rollNo;
    }

    /**
     * Sets the roll number of the student.
     * 
     * @param rollNo the roll number to set
     */
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    /**
     * Returns the student ID.
     * 
     * @return studentId
     */
    public long getStudentId() {
        return studentId;
    }

    /**
     * Sets the student ID.
     * 
     * @param studentId the student ID to set
     */
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns the name of the student.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the student.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the marks obtained in Physics.
     * 
     * @return physics marks
     */
    public Integer getPhysics() {
        return physics;
    }

    /**
     * Sets the marks obtained in Physics.
     * 
     * @param physics the marks to set
     */
    public void setPhysics(int physics) {
        this.physics = physics;
    }

    /**
     * Returns the marks obtained in Chemistry.
     * 
     * @return chemistry marks
     */
    public Integer getChemistry() {
        return chemistry;
    }

    /**
     * Sets the marks obtained in Chemistry.
     * 
     * @param chemistry the marks to set
     */
    public void setChemistry(int chemistry) {
        this.chemistry = chemistry;
    }

    /**
     * Returns the marks obtained in Maths.
     * 
     * @return maths marks
     */
    public Integer getMaths() {
        return maths;
    }

    /**
     * Sets the marks obtained in Maths.
     * 
     * @param maths the marks to set
     */
    public void setMaths(int maths) {
        this.maths = maths;
    }

    /**
     * Returns the key for dropdown representation.
     * Currently returns null and should be implemented.
     * 
     * @return key as String (currently null)
     */
    public String getKey() {
        return null;
    }

    /**
     * Returns the value for dropdown representation.
     * Currently returns null and should be implemented.
     * 
     * @return value as String (currently null)
     */
    public String getValue() {
        return null;
    }
}
