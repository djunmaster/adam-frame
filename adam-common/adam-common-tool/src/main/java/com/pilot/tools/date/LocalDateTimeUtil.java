package com.pilot.tools.date;

import com.google.common.collect.Lists;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocalDateTimeUtil {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter DATETIME_FORMATTER_0 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDateTimeUtil() {
    }

    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    public static long getLocalDateTimeStamp() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static String getLocalDateTimeStringTime(long time) {
        return LocalDateTime.ofEpochSecond(time / 1000L, 0, ZoneOffset.ofHours(8)).format(DATETIME_FORMATTER);
    }

    public static long getLocalDateTimeToMsTime(LocalDateTime time) {
        return time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static String getLocalTimeString() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    public static String getLocalTimeString(String formatter) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return LocalTime.now().format(dateTimeFormatter);
    }

    public static String getLocalDateString() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    public static String getLocalDateString(String formatter) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return LocalDate.now().format(dateTimeFormatter);
    }

    public static String localDateTime2String(LocalDateTime localDateTime) {
        return localDateTime.format(DATETIME_FORMATTER);
    }

    public static String localDateTime2String() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    public static String localDate2String(LocalDate localDate) {
        return localDate.format(DATE_FORMATTER);
    }

    public static LocalTime string2LocalTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    public static LocalDate string2LocalDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static LocalDate string2LocalDate(String date, String formatter) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return LocalDate.parse(date, dateTimeFormatter);
    }

    public static LocalDateTime string2LocalDateTime(String dateTime) {
        return "yyyy-MM-dd HH:mm".length() == dateTime.length() ? LocalDateTime.parse(dateTime, DATETIME_FORMATTER_0) : LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
    }

    public static LocalDateTime string2LocalDateTime(String dateTime, String formatter) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    public static LocalTime date2LocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalTime();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static int periodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getDays();
    }

    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }

    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    public static int getWeekOfMonth(String strData) {
        return getWeekOfMonth(string2LocalDateTime(strData));
    }

    public static int getWeekOfMonth(String strData, String formatter) {
        return getWeekOfMonth(string2LocalDateTime(strData, formatter));
    }

    public static int getWeekOfMonth(LocalDateTime date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(localDateTime2Date(date));
        return calendar.get(4);
    }

    public static List<LocalDateTime> getMonthWeekDayList(String date) {
        return getMonthWeekDayList(string2LocalDateTime(date));
    }

    public static List<LocalDateTime> getMonthWeekDayList(LocalDateTime date) {
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(localDateTime2Date(date));
        LocalDateTime monthFirstDay = date.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime monthLastDay = date.with(TemporalAdjusters.lastDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime startDay = date.plusDays(-7L);
        if (startDay.isBefore(monthFirstDay)) {
            startDay = monthFirstDay;
        }

        LocalDateTime endDay = date.plusDays(7L);
        if (endDay.isAfter(monthLastDay)) {
            endDay = monthLastDay;
        }

        List<LocalDateTime> retList = Lists.newArrayList();

        for(int weekOfMonth = getWeekOfMonth(date); !startDay.isAfter(endDay); startDay = startDay.plusDays(1L)) {
            if (weekOfMonth == getWeekOfMonth(startDay)) {
                retList.add(startDay);
            }
        }

        return retList;
    }

    public static List<LocalDateTime> getMonthDayList(String date) {
        return getMonthDayList(string2LocalDateTime(date));
    }

    public static List<LocalDateTime> getMonthDayList(LocalDateTime date) {
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(localDateTime2Date(date));
        LocalDateTime monthFirstDay = date.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime monthLastDay = date.with(TemporalAdjusters.lastDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);

        ArrayList retList;
        for(retList = Lists.newArrayList(); !monthFirstDay.isAfter(monthLastDay); monthFirstDay = monthFirstDay.plusDays(1L)) {
            retList.add(monthFirstDay);
        }

        return retList;
    }
}
