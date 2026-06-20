package com.meenz.aistockdashboard.util;

public class NumberUtil {

    public static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}