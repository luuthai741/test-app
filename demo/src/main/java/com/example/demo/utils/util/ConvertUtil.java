package com.example.demo.utils.util;

import java.text.NumberFormat;
import java.util.*;

public class ConvertUtil {
    public static Set<String> PAGES = new HashSet<>();
    public static String convertNumber(Object number){
        Locale lc = new Locale("nv","VN");
        NumberFormat nf = NumberFormat.getInstance(lc);
        return nf.format(number);
    }

    public static String replaceNullStringToBlank(String value){
        return value.isBlank() ? "" : value;
    }
}
