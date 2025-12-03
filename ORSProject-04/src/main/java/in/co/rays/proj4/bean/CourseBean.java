package in.co.rays.proj4.bean;

/**
 * CourseBean represents a course in the system.
 * It extends BaseBean and contains information about
 * the course's name, duration, and description.
 * Also provides key-value representation for dropdowns.
 * 
 * @author Krati
 * @version 1.0
 */
public class CourseBean extends BaseBean {

    /** Name of the course */
    private String name;

    /** Duration of the course */
    private String duration;

    /** Description of the course */
    private String description;

    /**
     * Returns the name of the course.
     * 
     * @return name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     * 
     * @param name the course name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the duration of the course.
     * 
     * @return duration of the course
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the course.
     * 
     * @param duration the course duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Returns the description of the course.
     * 
     * @return description of the course
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the course.
     * 
     * @param description the course description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Here, it returns the course name.
     * 
     * @return value as String
     */
    public String getValue() {
        return name;
    }
}
