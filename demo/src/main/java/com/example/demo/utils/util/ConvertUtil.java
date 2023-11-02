package com.example.demo.utils.util;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ConvertUtil {
    public static Set<String> PAGES = new HashSet<>();
    public static String convertNumber(Object number){
        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance("VND");
        DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
        df.setCurrency(currency);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);
        return numberFormat.format(number);
    }

    public static String replaceNullStringToBlank(String value){
        return StringUtils.isBlank(value) ? "" : value;
    }

}
