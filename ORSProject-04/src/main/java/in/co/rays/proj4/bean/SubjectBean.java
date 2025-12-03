package in.co.rays.proj4.bean;

/**
 * SubjectBean represents a subject in the system.
 * It extends BaseBean and contains information about
 * the subject's name, description, and associated course.
 * Also provides key-value representation for dropdowns.
 * 
 * Author: Krati
 * Version: 1.0
 */
public class SubjectBean extends BaseBean {

    /** Name of the subject */
    private String name;

    /** ID of the course the subject belongs to */
    private long courseId;

    /** Name of the course the subject belongs to */
    private String courseName;

    /** Description of the subject */
    private String description;

    /**
     * Returns the name of the subject.
     * 
     * @return name of the subject
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the subject.
     * 
     * @param name the subject name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the course ID the subject belongs to.
     * 
     * @return courseId
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Sets the course ID the subject belongs to.
     * 
     * @param courseId the course ID to set
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the course name the subject belongs to.
     * 
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course name the subject belongs to.
     * 
     * @param courseName the course name to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Returns the description of the subject.
     * 
     * @return description of the subject
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the subject.
     * 
     * @param description the subject description to set
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
     * Here, it returns the subject name.
     * 
     * @return value as String
     */
    public String getValue() {
        return name;
    }
}
