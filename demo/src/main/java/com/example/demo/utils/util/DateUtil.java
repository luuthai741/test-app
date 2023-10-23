package com.example.demo.utils.util;

import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateUtil {
    public static String DD_MM_YYYY = "dd/MM/yyyy";
    public static String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";
    public static String DB_FORMAT = "YYYY-MM-dd hh:mm:ss";
    public static String convertToString(Temporal temporal, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(temporal);
    }
}
