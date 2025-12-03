package in.co.rays.proj4.exception;

/**
 * Exception thrown when a duplicate record is encountered,
 * typically during operations like add or update where a unique
 * field (such as login, email, or primary key) already exists
 * in the system.
 * <p>
 * This exception helps the application differentiate duplicate
 * data errors from other types of exceptions.
 * </p>
 * 
 * @author Krati
 */
public class DuplicateRecordException extends Exception {

    /**
     * Constructs a DuplicateRecordException with the specified
     * detail message.
     *
     * @param msg the detail message describing the duplicate issue
     */
    public DuplicateRecordException(String msg) {
        super(msg);
    }
}
