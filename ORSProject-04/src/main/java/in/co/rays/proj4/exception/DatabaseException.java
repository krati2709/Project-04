package in.co.rays.proj4.exception;

/**
 * DatabaseException is thrown when any database-related error occurs,
 * such as connectivity issues, SQL exceptions, query failures,
 * or errors while interacting with the persistence layer.
 * <p>
 * This exception helps separate database-specific issues from
 * general application errors, allowing controllers and models
 * to handle them appropriately.
 * </p>
 * 
 * @author Krati
 */
public class DatabaseException extends Exception {

    /**
     * Constructs a DatabaseException with the specified detail message.
     *
     * @param msg the detail message describing the error
     */
    public DatabaseException(String msg) {
        super(msg);
    }
}
