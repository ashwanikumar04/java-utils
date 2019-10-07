package in.ashwanik.utils;


import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class DateTimeUtils {

    private DateTimeUtils() {
        throw new UnsupportedOperationException("Class instance is not available");
    }

    /**
     * Converts a calendar instance to LocalDateTime
     *
     * @param calendar Calendar instance which needs to be converted
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(final Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        final TimeZone tz = calendar.getTimeZone();
        final ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    /**
     * Gets the maximum of the dates.
     *
     * @param dateTimes Variable list of dates
     * @return Max of all the dates. Will return {@link java.time.LocalDateTime#MIN } in case no date is passed
     */
    public static LocalDateTime getMaxDateTime(final LocalDateTime... dateTimes) {
        return Arrays
                .stream(dateTimes)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.MIN);
    }

    /**
     * Gets the maximum of the dates.
     *
     * @param dates Variable list of dates
     * @return Max of all the dates. Will return {@link java.time.LocalDateTime#MIN } in case no date is passed
     */
    public static LocalDateTime getMaxDateTime(final Calendar... dates) {
        LocalDateTime[] dateTimes = mapToDatetime(dates);
        return getMaxDateTime(dateTimes);
    }

    /**
     * Gets the minimum of the dates.
     *
     * @param dateTimes Variable list of dates
     * @return Minimum of all the dates. Will return {@link java.time.LocalDateTime#MAX } in case no date is passed
     */
    public static LocalDateTime getMinDateTime(final LocalDateTime... dateTimes) {
        return Arrays
                .stream(dateTimes)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.MAX);
    }

    /**
     * Gets the minimum of the dates.
     *
     * @param dates Variable list of dates
     * @return Minimum of all the dates. Will return {@link java.time.LocalDateTime#MAX } in case no date is passed
     */
    public static LocalDateTime getMinDateTime(final Calendar... dates) {
        LocalDateTime[] dateTimes = mapToDatetime(dates);
        return getMinDateTime(dateTimes);
    }

    /**
     * Check if a date is between two dates
     *
     * @param localDateTime Date which needs to be checked
     * @param from          Lower bound of the range
     * @param to            Upper bound of the range
     * @return TRUE/FALSE
     */
    public static boolean isBetween(final LocalDateTime localDateTime,
                                    final LocalDateTime from,
                                    final LocalDateTime to) {
        final LocalDateTime currentFrom = from == null ? LocalDateTime.MIN : from;
        final LocalDateTime currentTo = to == null ? LocalDateTime.MAX : to;
        return (localDateTime.isAfter(currentFrom) || localDateTime.isEqual(currentFrom))
                && (localDateTime.isBefore(currentTo) || localDateTime.isEqual(currentTo));
    }

    /**
     * Converts {@link java.util.Date} to java 8 LocalDateTime
     *
     * @param date Date
     * @return Converted LocalDateTime
     */
    public static LocalDateTime toUTC(final Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    /**
     * Converts milliseconds to UTC datetime
     *
     * @param millis UTC milliseconds
     * @return Datetime
     */
    public static LocalDateTime toUTC(final long millis) {
        if (millis == 0) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
    }

    /**
     * Converts a date time to UTC millis
     *
     * @param localDateTime Zoned date time
     * @return Epoch millis
     */
    public static long toUTCMillis(final ZonedDateTime localDateTime) {
        return localDateTime.withZoneSameInstant(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    /**
     * Returns current UTC time
     *
     * @return UTC time
     */
    public static LocalDateTime getUtcNow() {
        return getNow().toLocalDateTime();
    }

    /**
     * Returns current UTC milliseconds
     *
     * @return UTC millis
     */
    public static long getUtcNowMills() {
        return getNow().toInstant().toEpochMilli();
    }

    /**
     * Generates string as '2019-10-01T05:05:05Z'
     *
     * @param localDateTime Date time
     * @return ISO UTC string
     */
    public static String getUtcIsoString(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.UTC).toString();
    }

    /**
     * Returns the start of the date
     *
     * @param dateTime Datetime
     * @return Start time of the day
     */
    public static LocalDateTime getStartOfDay(LocalDateTime dateTime) {
        return dateTime.withHour(0).withMinute(0).withSecond(0);
    }

    /***
     * Returns the end of the day
     * @param dateTime Datetime
     * @return End time of the day
     */
    public static LocalDateTime getEndOfDay(LocalDateTime dateTime) {
        return dateTime.withHour(23).withMinute(59).withSecond(59);
    }

    /**
     * Returns the first day of the month
     *
     * @param localDateTime LocalDateTime
     * @return First of the month
     */
    public static LocalDate getFirstDayOfMonth(LocalDateTime localDateTime) {
        LocalDateTime dateTime = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
        return dateTime.toLocalDate();
    }

    /**
     * Returns the last of the month
     *
     * @param localDateTime LocalDateTime
     * @return Last day of the month
     */
    public static LocalDate getLastDayOfMonth(LocalDateTime localDateTime) {
        LocalDateTime dateTime = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        return dateTime.toLocalDate();
    }


    private static ZonedDateTime getNow() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }

    @NotNull
    private static LocalDateTime[] mapToDatetime(Calendar[] dates) {
        return Arrays.stream(dates)
                .filter(Objects::nonNull)
                .map(DateTimeUtils::toLocalDateTime)
                .toArray(LocalDateTime[]::new);
    }
}
