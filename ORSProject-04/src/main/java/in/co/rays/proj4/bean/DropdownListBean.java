package in.co.rays.proj4.bean;

/**
 * DropdownListBean is an interface for beans that can be represented
 * as key-value pairs in dropdown lists or selection components.
 * Any bean implementing this interface must provide a key and value.
 * 
 * @author Krati
 * @version 1.0
 */
public interface DropdownListBean {

    /**
     * Returns the key for dropdown representation.
     * Typically, this is a unique identifier for the bean.
     * 
     * @return key as a String
     */
    public String getKey();

    /**
     * Returns the value for dropdown representation.
     * Typically, this is the display name or description of the bean.
     * 
     * @return value as a String
     */
    public String getValue();
}
