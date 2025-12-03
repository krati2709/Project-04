package in.co.rays.proj4.exception;

/**
 * Exception thrown when a requested record is not found in the database.
 * <p>
 * This exception is typically used in operations such as update, delete,
 * or find, where the expected record does not exist.
 * </p>
 * 
 * @author Krati
 */
public class RecordNotFoundException extends Exception {

    /**
     * Constructs a RecordNotFoundException with the specified detail message.
     *
     * @param msg the message describing why the record was not found
     */
    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
