package in.co.rays.proj4.bean;

/**
 * DepartmentBean represents a department in the system.
 * It extends BaseBean and contains information about
 * the department's name and description.
 * Also provides key-value representation for dropdowns.
 * 
 * @author Krati
 * @version 1.0
 */
public class DepartmentBean extends BaseBean {

    /** Name of the department */
    private String name;

    /** Description of the department */
    private String description;

    /**
     * Returns the name of the department.
     * 
     * @return name of the department
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the department.
     * 
     * @param name the department name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the department.
     * 
     * @return description of the department
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the department.
     * 
     * @param description the department description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

    /**
     * Returns the key for dropdown representation.
     * Here, it returns the id as a String.
     * 
     * @return key as String
     */

}
