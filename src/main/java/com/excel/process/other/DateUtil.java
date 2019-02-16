package com.excel.process.other;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author : funnyzpc
 */
public class DateUtil {

    public final static String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public final static String FORMAT_YYYYMMDD = "yyyyMMdd";
    public final static String FORMAT_YYYY_MM_DD_HMS = "yyyy-MM-dd HH:mm:ss";
    public final static long DAY_MILLI = 24L * 60 * 60 * 1000;

    /**
     * 日期转换成时间字符串
     * @param date
     * @param formatStr
     */
    public static String format(Date date, String formatStr){
        if(date == null || StringUtils.isEmpty(formatStr)){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        return simpleDateFormat.format(date);
    }

    /**
     * LocalDate、LocalDateTime 格式化字符串
     */
    public static String format(LocalDate localDate, String fmtPattern){
        if(localDate == null || StringUtils.isEmpty(fmtPattern)){
            return null;
        }
        return DateTimeFormatter.ofPattern(fmtPattern).format(localDate);
    }
    public static String format(LocalDateTime localDateTime, String fmtPattern){
        if(localDateTime == null || StringUtils.isEmpty(fmtPattern)){
            return null;
        }
        return DateTimeFormatter.ofPattern(fmtPattern).format(localDateTime);
    }


    /**
     * 设置日期时区常量
     */
    public static final ZoneId CHINA_ZONE_ID = ZoneId.systemDefault();

    /**
     *  jdk8 获取日期
     * @param dateStart 开始日期
     * @param dateEnd   结束日期
     * @return 日期间隔
     */
    public static int daysBetween(Date dateStart, Date dateEnd){
        Long countDay = dateEnd.toInstant().atZone(CHINA_ZONE_ID).toLocalDate().toEpochDay()-dateStart.toInstant().atZone(CHINA_ZONE_ID).toLocalDate().toEpochDay();
        return countDay.intValue();
    }

    public static Date getDistanceDay(Date date,Long dayCount){
        return Date.from(date.toInstant()
                .atZone(CHINA_ZONE_ID)
                .toLocalDateTime()
                .plusDays(dayCount)
                .atZone(CHINA_ZONE_ID)
                .toInstant());
    }

    
}