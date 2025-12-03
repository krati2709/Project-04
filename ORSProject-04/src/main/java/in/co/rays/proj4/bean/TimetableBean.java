package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * TimetableBean represents a timetable entry in the system.
 * It extends BaseBean and contains information about the semester,
 * exam details, and associated course and subject.
 * Also provides key-value representation for dropdowns.
 * 
 * Note: getKey() and getValue() methods are currently returning null
 * and should be implemented as needed.
 * 
 * Author: Krati
 * Version: 1.0
 */
public class TimetableBean extends BaseBean {

    /** Semester of the timetable */
    private String semester;

    /** Description of the timetable */
    private String description;

    /** Date of the exam */
    private Date examDate;

    /** Time of the exam */
    private String examTime;

    /** Course ID associated with the timetable */
    private long courseId;

    /** Course name associated with the timetable */
    private String courseName;

    /** Subject ID associated with the timetable */
    private long subjectId;

    /** Subject name associated with the timetable */
    private String subjectName;

    /**
     * Returns the semester of the timetable.
     * 
     * @return semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Sets the semester of the timetable.
     * 
     * @param semester the semester to set
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * Returns the description of the timetable.
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the timetable.
     * 
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the exam date.
     * 
     * @return examDate
     */
    public Date getExamDate() {
        return examDate;
    }

    /**
     * Sets the exam date.
     * 
     * @param examDate the exam date to set
     */
    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    /**
     * Returns the exam time.
     * 
     * @return examTime
     */
    public String getExamTime() {
        return examTime;
    }

    /**
     * Sets the exam time.
     * 
     * @param examTime the exam time to set
     */
    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    /**
     * Returns the course ID associated with the timetable.
     * 
     * @return courseId
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Sets the course ID associated with the timetable.
     * 
     * @param courseId the course ID to set
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the course name associated with the timetable.
     * 
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course name associated with the timetable.
     * 
     * @param courseName the course name to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Returns the subject ID associated with the timetable.
     * 
     * @return subjectId
     */
    public long getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the subject ID associated with the timetable.
     * 
     * @param subjectId the subject ID to set
     */
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Returns the subject name associated with the timetable.
     * 
     * @return subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Sets the subject name associated with the timetable.
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
