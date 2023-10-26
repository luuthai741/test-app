package com.example.demo.utils.util;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateUtil {
    public static String DD_MM_YYYY = "dd/MM/yyyy";
    public static String DD_MM_YYYY_2 = "dd_MM_yyyy";
    public static String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
    public static String DB_FORMAT = "YYYY-MM-dd HH:mm:ss";
    public static String convertToString(Temporal temporal, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(temporal);
    }
    public static LocalDateTime convertStringToLocalDateTime(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, formatter);
    }
}
