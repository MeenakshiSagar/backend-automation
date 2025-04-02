package com.saralapp.helpers;

public class StringHelper {

    /**
     * Repeats the given string 's' for 'times' number of times.
     *
     * @param s the string to repeat
     * @param times the number of times to repeat
     * @return the repeated string
     */
    public static String repeat(String s, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

}
