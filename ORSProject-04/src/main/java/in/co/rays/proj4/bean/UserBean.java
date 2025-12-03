package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * UserBean represents a user in the system.
 * It extends BaseBean and contains user-specific information
 * such as name, login credentials, date of birth, role, and contact details.
 * Also provides key-value representation for dropdowns.
 * 
 * @author Krati
 * @version 1.0
 */
public class UserBean extends BaseBean {

    /** First name of the user */
    private String firstName;

    /** Last name of the user */
    private String lastName;

    /** Login ID of the user */
    private String login;

    /** Password of the user */
    private String password;

    /** Confirm password of the user */
    private String confirmPassword;

    /** Date of birth of the user */
    private Date dob;

    /** Mobile number of the user */
    private String mobileNo;

    /** Role ID assigned to the user */
    private long roleId;

    /** Gender of the user */
    private String gender;

    /**
     * Returns the first name of the user.
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * 
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * 
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the login ID of the user.
     * 
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login ID of the user.
     * 
     * @param login the login ID to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Returns the password of the user.
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the confirm password of the user.
     * 
     * @return confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets the confirm password of the user.
     * 
     * @param confirmPassword the confirm password to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Returns the date of birth of the user.
     * 
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the user.
     * 
     * @param dob the date of birth to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Returns the mobile number of the user.
     * 
     * @return mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the mobile number of the user.
     * 
     * @param mobileNo the mobile number to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Returns the role ID assigned to the user.
     * 
     * @return roleId
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * Sets the role ID of the user.
     * 
     * @param roleId the role ID to set
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    /**
     * Returns the gender of the user.
     * 
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     * 
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
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
