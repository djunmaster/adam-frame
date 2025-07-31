package com.pilot.constants;

import java.time.format.DateTimeFormatter;

public interface Constants {

    interface DateFormatter {
        String DATE_FORMAT = "yyyy-MM-dd";
        String DATE_FORMAT2 = "yyyy/MM/dd";
        String DATE_FORMAT3 = "yyyyMMdd";
        String CHINESE_DATE_FORMAT = "yyyy年MM月dd日";
        String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        String HOUR_FORMAT2 = "yyyy-MM-dd HH";
        String MINUTE_FORMAT2 = "yyyy-MM-dd HH:mm";
        String MINUTE_FORMAT = "HH:mm";
        String HOUR_FORMAT = "dd日HH时";
        String MONTH_FORMAT = "yyyy年MM月";

        String HOUR_FORMAT_SAMPLE = "H'h'";
        String DAY_FORMAT_SAMPLE = "d'd'";
        String MONTH_FORMAT_SAMPLE = "M'm'";
        String MONTH_FORMAT_1 = "yyyy/MM";
        String MONTH_FORMAT_STANDARD = "yyyy-MM";

        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
        DateTimeFormatter CHINESE_DATE_FORMATTER = DateTimeFormatter.ofPattern(CHINESE_DATE_FORMAT);
        DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
        DateTimeFormatter MINUTE_FORMATTER = DateTimeFormatter.ofPattern(MINUTE_FORMAT);
        DateTimeFormatter MINUTE_FORMATTER2 = DateTimeFormatter.ofPattern(MINUTE_FORMAT2);
        DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern(HOUR_FORMAT);
        DateTimeFormatter HOUR_FORMATTER2 = DateTimeFormatter.ofPattern(HOUR_FORMAT2);
        DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern(DAY_FORMAT_SAMPLE);
        DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern(MONTH_FORMAT_STANDARD);
    }

}
