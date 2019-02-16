package com.excel.process.other;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author : funnyzpc
 */
public class UUIDUtil {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS");
    private static final DateTimeFormatter DATE_FORMAT_SHORT = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 生成uuid
     */
    public static String generate(){
        return UUID.randomUUID().toString();
    }

    /**
     * 生成30位表ID (时间项为排序所使用)
     * @return
     */
    public static String primaryKey(){
        return DATE_FORMAT.format(LocalDateTime.now()).concat(UUID.randomUUID().toString().substring(24, 36).toUpperCase());
    }

    public static String shortUUID(){
        return new StringBuilder(UUID.randomUUID().toString())
                .reverse()
                .substring(0,8)
                .toUpperCase();
    }

    public static String mediumUUID(){
        return new StringBuilder(DATE_FORMAT_SHORT.format(LocalDateTime.now()))
                .append(UUID.randomUUID().toString().substring(28,36).toUpperCase())
                .toString();
    }

}
