package in.ashwanik.utils;


import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class DateTimeUtilsUnitTest {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2019, 10, 1, 5, 5, 5);

    @Test
    public void givenTwoCalendarWhenMaxThenMaxDateIsReturned() {
        Calendar newDate = Calendar.getInstance();
        newDate.add(Calendar.DAY_OF_MONTH, 5);
        LocalDateTime localDateTime = DateTimeUtils.getMaxDateTime(Calendar.getInstance(), null, newDate);
        assertThat(LocalDateTime.now().plusDays(5).plusMinutes(5)).isAfter(localDateTime);
    }

    @Test
    public void givenTwoCalendarWhenMinThenMinDateIsReturned() {
        Calendar newDate = Calendar.getInstance();
        newDate.add(Calendar.DAY_OF_MONTH, -5);
        LocalDateTime localDateTime = DateTimeUtils.getMinDateTime(Calendar.getInstance(), null, newDate);
        assertThat(LocalDateTime.now()).isAfter(localDateTime);
    }

    @Test
    public void givenDateAndRangeWhenCheckInBetweenReturnTrue() {
        assertThat(DateTimeUtils.isBetween(LocalDateTime.now(), LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1))).isTrue();
    }

    @Test
    public void givenDateWhenConvertToLocalDateTimeThenReturnConvertedLocalDateTime() {
        assertThat(DateTimeUtils.toUTC(new Date())).isBefore(DateTimeUtils.getUtcNow().plusMinutes(5));
    }

    @Test
    public void givenMillsWhenConvertToLocalDateTimeThenReturnConvertedLocalDateTime() {
        LocalDateTime localDateTime = DateTimeUtils.toUTC(DateTimeUtils.getUtcNowMills());
        assertThat(localDateTime).isBefore(DateTimeUtils.getUtcNow().plusMinutes(5));
    }

    @Test
    public void givenLocalDateTimeWhenGetUtcMillisThenReturnUtcMillis() {
        long utcMillis = DateTimeUtils.getUtcNowMills();
        long now = DateTimeUtils.toUTCMillis(LocalDateTime.now().atZone(ZoneId.systemDefault()));
        assertThat(now - utcMillis).isLessThan(100);
    }

    @Test
    public void givenZeroMillsWhenConvertToLocalDateTimeThenReturnConvertedLocalDateTime() {
        assertThat(DateTimeUtils.toUTC(0)).isNull();
    }

    @Test
    public void givenLocalDateTimeWhenConvertedToStringThenIsoFormattedStringIsGenerated() {
        LocalDateTime localDateTime = LOCAL_DATE_TIME;
        assertThat(DateTimeUtils.getUtcIsoString(localDateTime)).isEqualTo("2019-10-01T05:05:05Z");
    }

    @Test
    public void givenDateWhenGetFirstDayThenFirstDayOfTheMonthIsReturned() {
        LocalDateTime localDateTime = LOCAL_DATE_TIME;
        assertThat(DateTimeUtils.getFirstDayOfMonth(localDateTime)).isEqualTo(LocalDate.of(2019, 10, 1));
    }

    @Test
    public void givenDateWhenGetLastDayThenLastDayOfTheMonthIsReturned() {
        LocalDateTime localDateTime = LOCAL_DATE_TIME;
        assertThat(DateTimeUtils.getLastDayOfMonth(localDateTime)).isEqualTo(LocalDate.of(2019, 10, 31));
    }

    @Test
    public void givenDateWhenGetStartOfDayThenFirstDayTimeIsReturned() {
        LocalDateTime localDateTime = LOCAL_DATE_TIME;
        assertThat(DateTimeUtils.getStartOfDay(localDateTime)).isEqualTo(LocalDateTime.of(2019, 10, 1, 0, 0, 0));
    }

    @Test
    public void givenDateWhenGetEndOfDayThenEndDayTimeIsReturned() {
        LocalDateTime localDateTime = LOCAL_DATE_TIME;
        assertThat(DateTimeUtils.getEndOfDay(localDateTime)).isEqualTo(LocalDateTime.of(2019, 10, 1, 23, 59, 59));
    }
}
