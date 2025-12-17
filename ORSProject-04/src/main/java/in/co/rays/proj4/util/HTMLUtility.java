package in.co.rays.proj4.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import in.co.rays.proj4.bean.DropdownListBean;
import in.co.rays.proj4.model.RoleModel;

/**
 * HTMLUtility class provides utility methods to generate HTML dropdown lists
 * dynamically from Maps or Lists. It supports pre-selecting a value in the dropdown.
 * 
 * @author Krati
 */
public class HTMLUtility {

    /**
     * Generates HTML select element from a HashMap.
     * 
     * @param name        the name attribute of the select element
     * @param selectedVal the value to be selected by default
     * @param map         HashMap containing key-value pairs for options
     * @return HTML string for the select element
     */
    public static String getList(String name, String selectedVal, HashMap<String, String> map) {
        StringBuffer sb = new StringBuffer(
                "<select style=\"width: 169px;text-align-last: center;\"; class='form-control' name='" + name + "'>");
        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        Set<String> keys = map.keySet();
        String val = null;

        for (String key : keys) {
            val = map.get(key);
            if (key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("\n<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Generates HTML select element from a List of DropdownListBean.
     * 
     * @param name        the name attribute of the select element
     * @param selectedVal the value to be selected by default
     * @param list        List containing DropdownListBean objects
     * @return HTML string for the select element
     */
    
    public static String getList(String name, String selectedVal, List list) {

        List<DropdownListBean> dd = (List<DropdownListBean>) list;

        StringBuffer sb = new StringBuffer("<select style=\"width: 169px;text-align-last: center;\"; "
                + "class='form-control' name='" + name + "'>");
        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        String key = null;
        String val = null;

        for (DropdownListBean obj : dd) {
            key = obj.getKey();
            val = obj.getValue();

            if (key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("\n<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Test method to generate HTML select from a HashMap.
     */
    public static void testGetListByMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("male", "male");
        map.put("female", "female");

        String selectedValue = "male";
        String htmlSelectFromMap = HTMLUtility.getList("gender", selectedValue, map);

        System.out.println(htmlSelectFromMap);
    }

    /**
     * Test method to generate HTML select from a List of DropdownListBean fetched
     * from RoleModel.
     * 
     * @throws Exception if any error occurs while fetching list
     */
    public static void testGetListByList() throws Exception {
        RoleModel model = new RoleModel();

        List list = model.list();
        String selectedValue = "1";
        String htmlSelectFromList = HTMLUtility.getList("role", selectedValue, list);

        System.out.println(htmlSelectFromList);
    }

    /**
     * Main method for testing the utility methods.
     * 
     * @param args command line arguments
     * @throws Exception if any error occurs
     */
    public static void main(String[] args) throws Exception {
        // testGetListByMap();
        testGetListByList();
    }
}
