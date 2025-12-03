package in.co.rays.proj4.util;

import java.util.ResourceBundle;

/**
 * PropertyReader is a utility class that reads key-value pairs from
 * <code>system.properties</code> file using ResourceBundle.
 * <p>
 * It provides methods to fetch values by key, and also replace placeholders
 * like {0}, {1}, ... with actual parameters.
 * </p>
 * 
 * Example usage:
 * <pre>
 * String val = PropertyReader.getValue("error.require");
 * String valWithParam = PropertyReader.getValue("error.require", "loginId");
 * String valWithParams = PropertyReader.getValue("error.multipleFields", new String[]{"Roll No", "Student Name"});
 * </pre>
 * 
 * Author: Krati
 */
public class PropertyReader {

    /** ResourceBundle to load properties from system.properties */
    private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

    /**
     * Returns the value associated with the given key from the properties file.
     * If the key is not found, the key itself is returned.
     * 
     * @param key the property key
     * @return value associated with key or key itself if not found
     */
    public static String getValue(String key) {
        String val = null;
        try {
            val = rb.getString(key);
        } catch (Exception e) {
            val = key;
        }
        return val;
    }

    /**
     * Returns the value associated with the given key, replacing the {0} placeholder
     * with the provided parameter.
     * 
     * @param key   the property key
     * @param param parameter to replace {0} in the property value
     * @return formatted property value
     */
    public static String getValue(String key, String param) {
        String msg = getValue(key);
        msg = msg.replace("{0}", param);
        return msg;
    }

    /**
     * Returns the value associated with the given key, replacing multiple placeholders
     * {0}, {1}, ... with the provided parameters.
     * 
     * @param key    the property key
     * @param params array of parameters to replace placeholders
     * @return formatted property value
     */
    public static String getValue(String key, String[] params) {
        String msg = getValue(key);
        for (int i = 0; i < params.length; i++) {
            msg = msg.replace("{" + i + "}", params[i]);
        }
        return msg;
    }

    /**
     * Demonstrates the usage of PropertyReader methods.
     */
    public static void main(String[] args) {

        System.out.println("Single key example:");
        System.out.println(PropertyReader.getValue("error.require"));

        System.out.println("\nSingle parameter replacement example:");
        System.out.println(PropertyReader.getValue("error.require", "loginId"));

        System.out.println("\nMultiple parameter replacement example:");
        String[] params = { "Roll No", "Student Name" };
        System.out.println(PropertyReader.getValue("error.multipleFields", params));
    }
}
