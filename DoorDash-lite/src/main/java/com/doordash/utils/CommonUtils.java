package com.doordash.utils;

import java.util.List;

public class CommonUtils {

    public static boolean isNullOrEmpty(String in) {
        return in == null || in.isEmpty();
    }


    public static boolean isNullOrEmpty(List in) {
        return in == null || in.isEmpty();
    }


    public static double convertCentsToDollar(int cents) {
        if (cents < 0) return 0;
        return cents / 100;
    }

}
