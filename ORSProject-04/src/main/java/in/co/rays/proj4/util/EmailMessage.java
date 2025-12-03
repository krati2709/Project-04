package in.co.rays.proj4.util;

/**
 * EmailMessage is a simple JavaBean class representing an email message.
 * It contains the recipient, subject, message body, and message type (HTML or Text).
 * 
 * @author Krati
 */
public class EmailMessage {

    /** Recipient email address */
    private String to;

    /** Email subject */
    private String subject;

    /** Email message content */
    private String message;

    /** Type of message: HTML_MSG or TEXT_MSG */
    private int messageType = TEXT_MSG;

    /** Constant representing HTML message type */
    public static final int HTML_MSG = 1;

    /** Constant representing plain text message type */
    public static final int TEXT_MSG = 2;

    /** Default constructor */
    public EmailMessage() {
    }

    /**
     * Parameterized constructor to create an email message with recipient, subject, and message.
     *
     * @param to      recipient email address
     * @param subject subject of the email
     * @param message content of the email
     */
    public EmailMessage(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    /**
     * Sets the recipient email address.
     * 
     * @param to recipient email address
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Gets the recipient email address.
     * 
     * @return recipient email address
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the email subject.
     * 
     * @param subject subject of the email
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the email subject.
     * 
     * @return subject of the email
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the email message content.
     * 
     * @param message content of the email
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the email message content.
     * 
     * @return content of the email
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message type (HTML or Text).
     * 
     * @param messageType type of the message (HTML_MSG or TEXT_MSG)
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    /**
     * Gets the message type.
     * 
     * @return type of the message (HTML_MSG or TEXT_MSG)
     */
    public int getMessageType() {
        return messageType;
    }
}
