package com.example.demo.utils.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConvertUtil {
    public static List<String> PAGES = new ArrayList<>();
    public static String convertNumber(Object number){
        Locale lc = new Locale("nv","VN");
        NumberFormat nf = NumberFormat.getInstance(lc);
        return nf.format(number);
    }

    public static String replaceNullStringToBlank(String value){
        return value.isBlank() ? "" : value;
    }
}
