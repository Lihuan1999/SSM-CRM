package org.example.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  对 Date 类型数据进行处理的工具类
 */
public class DateUtils {
    /**
     *  yyyy-MM-dd HH:mm:ss
     */
    public static String formateDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     *  yyyy-MM-dd
     */
    public static String formateDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     *  HH:mm:ss
     */
    public static String formateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
