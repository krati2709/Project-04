package in.co.rays.proj4.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DataUtility provides helper methods to convert and manipulate data types
 * such as String, int, long, Date, and Timestamp.
 * 
 * @author krati
 */
public class DataUtility {

    /** Standard application date format */
    public static final String APP_DATE_FORMAT = "dd-MM-yyyy";

    /** Standard application timestamp format */
    public static final String APP_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    /** Formatter for dates */
    private static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);

    /** Formatter for timestamps */
    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);

    /**
     * Returns trimmed string if not null.
     * 
     * @param val input string
     * @return trimmed string or null
     */
    public static String getString(String val) {
        if (DataValidator.isNotNull(val)) {
            return val.trim();
        } else {
            return val;
        }
    }

    /**
     * Converts an object to string safely.
     * 
     * @param val input object
     * @return string representation or empty string if null
     */
    public static String getStringData(Object val) {
        if (val != null) {
            return val.toString();
        } else {
            return "";
        }
    }

    /**
     * Converts string to integer safely.
     * 
     * @param val input string
     * @return parsed integer or 0 if invalid
     */
    public static int getInt(String val) {
        if (DataValidator.isInteger(val)) {
            return Integer.parseInt(val);
        } else {
            return 0;
        }
    }

    /**
     * Converts string to long safely.
     * 
     * @param val input string
     * @return parsed long or 0 if invalid
     */
    public static long getLong(String val) {
        if (DataValidator.isLong(val)) {
            return Long.parseLong(val);
        } else {
            return 0;
        }
    }

    /**
     * Parses string into Date using standard format.
     * 
     * @param val date string
     * @return Date object or null if parsing fails
     */
    public static Date getDate(String val) {
        Date date = null;
        try {
            date = formatter.parse(val);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * Formats Date into string using standard format.
     * 
     * @param date input Date
     * @return formatted string or empty string if null
     */
    public static String getDateString(Date date) {
        try {
            return formatter.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * Parses string into Timestamp using standard timestamp format.
     * 
     * @param val timestamp string
     * @return Timestamp object or null if parsing fails
     */
    public static Timestamp getTimestamp(String val) {
        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
        } catch (Exception e) {
            return null;
        }
        return timeStamp;
    }

    /**
     * Converts milliseconds to Timestamp.
     * 
     * @param l milliseconds
     * @return Timestamp object or null if invalid
     */
    public static Timestamp getTimestamp(long l) {
        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp(l);
        } catch (Exception e) {
            return null;
        }
        return timeStamp;
    }

    /**
     * Returns current timestamp.
     * 
     * @return current Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp(new Date().getTime());
        } catch (Exception e) {
        }
        return timeStamp;
    }

    /**
     * Returns milliseconds from Timestamp.
     * 
     * @param tm input Timestamp
     * @return milliseconds or 0 if null
     */
    public static long getTimestamp(Timestamp tm) {
        try {
            return tm.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Main method to test DataUtility methods.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Test getString
        System.out.println("getString Test:");
        System.out.println("Original: '  Hello World  ' -> Trimmed: '" + getString("  Hello World  ") + "'");
        System.out.println("Null input: " + getString(null));

        // Test getStringData
        System.out.println("\ngetStringData Test:");
        System.out.println("Object to String: " + getStringData(1234));
        System.out.println("Null Object: '" + getStringData(null) + "'");

        // Test getInt
        System.out.println("\ngetInt Test:");
        System.out.println("Valid Integer String: '124' -> " + getInt("124"));
        System.out.println("Invalid Integer String: 'abc' -> " + getInt("abc"));
        System.out.println("Null String: -> " + getInt(null));

        // Test getLong
        System.out.println("\ngetLong Test:");
        System.out.println("Valid Long String: '123456789' -> " + getLong("123456789"));
        System.out.println("Invalid Long String: 'abc' -> " + getLong("abc"));

        // Test getDate
        System.out.println("\ngetDate Test:");
        String dateStr = "10/15/2024";
        Date date = getDate(dateStr);
        System.out.println("String to Date: '" + dateStr + "' -> " + date);

        // Test getDateString
        System.out.println("\ngetDateString Test:");
        System.out.println("Date to String: '" + getDateString(new Date()) + "'");

        // Test getTimestamp (String)
        System.out.println("\ngetTimestamp(String) Test:");
        String timestampStr = "10/15/2024 10:30:45";
        Timestamp timestamp = getTimestamp(timestampStr);
        System.out.println("String to Timestamp: '" + timestampStr + "' -> " + timestamp);

        // Test getTimestamp (long)
        System.out.println("\ngetTimestamp(long) Test:");
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp ts = getTimestamp(currentTimeMillis);
        System.out.println("Current Time Millis to Timestamp: '" + currentTimeMillis + "' -> " + ts);

        // Test getCurrentTimestamp
        System.out.println("\ngetCurrentTimestamp Test:");
        Timestamp currentTimestamp = getCurrentTimestamp();
        System.out.println("Current Timestamp: " + currentTimestamp);

        // Test getTimestamp(Timestamp)
        System.out.println("\ngetTimestamp(Timestamp) Test:");
        System.out.println("Timestamp to long: " + getTimestamp(currentTimestamp));
    }
}
