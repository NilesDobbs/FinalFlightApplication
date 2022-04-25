package com.coderscampus.flightreservationapp.util;

/**
 * class for some helper methods
 *
 */
public class StringUtils {

    /**
     * check if string is blank or null
     */
    public static boolean isStringEmpty(String str) {
        if(str == null)
            return true;
        return str.trim().equals("");
    }
}
