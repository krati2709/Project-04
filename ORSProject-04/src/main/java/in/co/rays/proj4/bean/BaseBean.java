package in.co.rays.proj4.bean;

import java.sql.Timestamp;

/**
 * Abstract base class for all beans in the application.
 * Provides common fields like id, createdBy, modifiedBy,
 * createdDatetime, and modifiedDatetime.
 * Implements the DropdownListBean interface.
 * 
 * @author Krati
 * @version 1.0
 */
public abstract class BaseBean implements DropdownListBean {

    /** Unique identifier for the bean */
    protected long id;

    /** User who created this bean */
    protected String createdBy;

    /** User who last modified this bean */
    protected String modifiedBy;

    /** Timestamp when the bean was created */
    protected Timestamp createdDatetime;

    /** Timestamp when the bean was last modified */
    protected Timestamp modifiedDatetime;

    /**
     * Returns the unique identifier of the bean.
     * 
     * @return id of the bean
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the bean.
     * 
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the name of the user who created this bean.
     * 
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of the user who created this bean.
     * 
     * @param createdBy the creator's name
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the name of the user who last modified this bean.
     * 
     * @return modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the name of the user who last modified this bean.
     * 
     * @param modifiedBy the modifier's name
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Returns the timestamp when this bean was created.
     * 
     * @return createdDatetime
     */
    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     * Sets the timestamp when this bean was created.
     * 
     * @param createdDatetime the creation timestamp
     */
    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    /**
     * Returns the timestamp when this bean was last modified.
     * 
     * @return modifiedDatetime
     */
    public Timestamp getModifiedDatetime() {
        return modifiedDatetime;
    }

    /**
     * Sets the timestamp when this bean was last modified.
     * 
     * @param modifiedDatetime the modification timestamp
     */
    public void setModifiedDatetime(Timestamp modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }
}
