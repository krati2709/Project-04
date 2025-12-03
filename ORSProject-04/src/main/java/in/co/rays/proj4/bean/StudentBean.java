package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * StudentBean represents a student in the system.
 * It extends BaseBean and contains personal and contact details,
 * as well as information about the college the student belongs to.
 * Also provides key-value representation for dropdowns.
 * 
 * Author: Krati
 * Version: 1.0
 */
public class StudentBean extends BaseBean {

    /** First name of the student */
    private String firstName;

    /** Last name of the student */
    private String lastName;

    /** Date of birth of the student */
    private Date dob;

    /** Gender of the student */
    private String gender;

    /** Mobile number of the student */
    private String mobileNo;

    /** Email of the student */
    private String email;

    /** College ID the student belongs to */
    private long collegeId;

    /** College name the student belongs to */
    private String collegeName;

    /**
     * Returns the first name of the student.
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the student.
     * 
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the student.
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the student.
     * 
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the date of birth of the student.
     * 
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the student.
     * 
     * @param dob the date of birth to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Returns the gender of the student.
     * 
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the student.
     * 
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the mobile number of the student.
     * 
     * @return mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the mobile number of the student.
     * 
     * @param mobileNo the mobile number to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Returns the email of the student.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the student.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the college ID the student belongs to.
     * 
     * @return collegeId
     */
    public long getCollegeId() {
        return collegeId;
    }

    /**
     * Sets the college ID the student belongs to.
     * 
     * @param collegeId the college ID to set
     */
    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * Returns the college name the student belongs to.
     * 
     * @return collegeName
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * Sets the college name the student belongs to.
     * 
     * @param collegeName the college name to set
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * Returns the key for dropdown representation.
     * Here, it returns the id as a String.
     * 
     * @return key as String
     */
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the value for dropdown representation.
     * Here, it returns the full name (firstName + lastName).
     * 
     * @return value as String
     */
    public String getValue() {
        return firstName + " " + lastName;
    }
}
