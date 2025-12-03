package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * FacultyBean represents a faculty member in the system.
 * It extends BaseBean and contains personal, contact, and
 * academic details including college, course, and subject information.
 * Also provides key-value representation for dropdowns.
 * 
 * Note: getKey() and getValue() methods are currently returning null
 * and should be implemented as needed.
 * 
 * @author Krati
 * @version 1.0
 */
public class FacultyBean extends BaseBean {

    /** First name of the faculty member */
    private String firstName;

    /** Last name of the faculty member */
    private String lastName;

    /** Date of birth of the faculty member */
    private Date dob;

    /** Gender of the faculty member */
    private String gender;

    /** Mobile number of the faculty member */
    private String mobileNo;

    /** Email of the faculty member */
    private String email;

    /** College ID where the faculty works */
    private long collegeId;

    /** College name where the faculty works */
    private String collegeName;

    /** Course ID assigned to the faculty */
    private long courseId;

    /** Course name assigned to the faculty */
    private String courseName;

    /** Subject ID assigned to the faculty */
    private long subjectId;

    /** Subject name assigned to the faculty */
    private String subjectName;

    /**
     * Returns the first name of the faculty member.
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the faculty member.
     * 
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the faculty member.
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the faculty member.
     * 
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the date of birth of the faculty member.
     * 
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the faculty member.
     * 
     * @param dob the date of birth to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Returns the gender of the faculty member.
     * 
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the faculty member.
     * 
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the mobile number of the faculty member.
     * 
     * @return mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the mobile number of the faculty member.
     * 
     * @param mobileNo the mobile number to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Returns the email of the faculty member.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the faculty member.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the college ID of the faculty member.
     * 
     * @return collegeId
     */
    public long getCollegeId() {
        return collegeId;
    }

    /**
     * Sets the college ID of the faculty member.
     * 
     * @param collegeId the college ID to set
     */
    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * Returns the college name of the faculty member.
     * 
     * @return collegeName
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * Sets the college name of the faculty member.
     * 
     * @param collegeName the college name to set
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * Returns the course ID assigned to the faculty member.
     * 
     * @return courseId
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Sets the course ID assigned to the faculty member.
     * 
     * @param courseId the course ID to set
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the course name assigned to the faculty member.
     * 
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course name assigned to the faculty member.
     * 
     * @param courseName the course name to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Returns the subject ID assigned to the faculty member.
     * 
     * @return subjectId
     */
    public long getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the subject ID assigned to the faculty member.
     * 
     * @param subjectId the subject ID to set
     */
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Returns the subject name assigned to the faculty member.
     * 
     * @return subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Sets the subject name assigned to the faculty member.
     * 
     * @param subjectName the subject name to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
