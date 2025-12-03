package in.co.rays.proj4.exception;

/**
 * ApplicationException is a custom unchecked exception used to indicate 
 * application-level errors such as database issues, system failures,
 * or unexpected exceptions during processing.
 * <p>
 * This exception is thrown from Model classes and caught in Controllers
 * to show user-friendly error messages.
 * </p>
 * 
 * @author Krati
 */
public class ApplicationException extends Exception {

    /**
     * Constructs an ApplicationException with the specified detail message.
     *
     * @param msg the detail message describing the exception
     */
    public ApplicationException(String msg) {
        super(msg);
    }
}
