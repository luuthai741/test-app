package com.example.demo.utils.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ConvertUtil {
    public static String convertNumber(Object number){
        Locale lc = new Locale("nv","VN");
        NumberFormat nf = NumberFormat.getInstance(lc);
        return nf.format(number);
    }

    public static String replaceNullStringToBlank(String value){
        return value.isBlank() ? "" : value;
    }
}
