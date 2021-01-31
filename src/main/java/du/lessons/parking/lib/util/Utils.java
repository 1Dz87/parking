package du.lessons.parking.lib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Boolean stringIsEmpty(String str) {
        return str == null || str.length() < 1;
    }

    public static String getEmptyString() {
        return "";
    }

    public static Boolean checkboxToBoolean(String value) {
        if (stringIsEmpty(value)) {
            return false;
        }
        return value.equals("on");
    }

    public static Date convert(String str) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Wrong date format");
        }
    }
}
