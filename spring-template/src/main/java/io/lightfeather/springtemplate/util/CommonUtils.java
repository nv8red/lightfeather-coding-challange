package io.lightfeather.springtemplate.util;

public class CommonUtils {

    public static boolean isNonNumeric(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }
}
