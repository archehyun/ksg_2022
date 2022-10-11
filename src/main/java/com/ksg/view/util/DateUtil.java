package com.ksg.view.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

    static SimpleDateFormat output_format = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat input_format = new SimpleDateFormat("yyyyMMdd");

    public static String convertType(String date)
    {
        try {
            return output_format.format(input_format.parse(date));
        } catch (ParseException e) {
            
            return date;
        }
    }

    public static String convertType(String date, String format)
    {
        try {
            output_format.applyPattern(format);
            
            return output_format.format(input_format.parse(date));
        } catch (ParseException e) {
            
            return date;
        }
    }

    
}
