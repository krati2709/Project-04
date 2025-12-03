package in.co.rays.proj4.bean;

/**
 * CollegeBean represents a college in the system.
 * It extends BaseBean and contains information about
 * the college's name, address, state, city, and phone number.
 * Also provides key-value representation for dropdowns.
 * 
 * @author Krati
 * @version 1.0
 */
public class CollegeBean extends BaseBean {

    /** Name of the college */
    private String name;

    /** Address of the college */
    private String address;

    /** State where the college is located */
    private String state;

    /** City where the college is located */
    private String city;

    /** Contact phone number of the college */
    private String phoneNo;

    /**
     * Returns the name of the college.
     * 
     * @return name of the college
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the college.
     * 
     * @param name the college name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the address of the college.
     * 
     * @return address of the college
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the college.
     * 
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the state where the college is located.
     * 
     * @return state of the college
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state where the college is located.
     * 
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the city where the college is located.
     * 
     * @return city of the college
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city where the college is located.
     * 
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the contact phone number of the college.
     * 
     * @return phoneNo of the college
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets the contact phone number of the college.
     * 
     * @param phoneNo the phone number to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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
     * Here, it returns the college name.
     * 
     * @return value as String
     */
    public String getValue() {
        return name;
    }
}
