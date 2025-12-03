package in.co.rays.proj4.bean;

/**
 * RoleBean represents a user role in the system.
 * It extends BaseBean and provides constants for common roles.
 * Also implements methods to get key-value representation for dropdowns.
 * 
 * @author Krati
 * @version 1.0
 */
public class RoleBean extends BaseBean {

    /** Admin role constant */
    public static final int ADMIN = 1;

    /** Student role constant */
    public static final int STUDENT = 2;

    /** College role constant */
    public static final int COLLEGE = 3;

    /** Kiosk role constant */
    public static final int KIOSK = 4;

    /** Faculty role constant */
    public static final int FACULTY = 5;

    /** Name of the role */
    private String name;

    /** Description of the role */
    private String description;

    /**
     * Returns the name of the role.
     * 
     * @return name of the role
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     * 
     * @param name the role name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the role.
     * 
     * @return description of the role
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the role.
     * 
     * @param description the role description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the key value for dropdown representation.
     * Here, it returns the id as a String.
     * 
     * @return key as String
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the value for dropdown representation.
     * Here, it returns the role name.
     * 
     * @return value as String
     */
    @Override
    public String getValue() {
        return name;
    }
}
