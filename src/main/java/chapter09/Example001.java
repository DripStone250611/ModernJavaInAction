package chapter09;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimeZone;

import static java.time.temporal.TemporalAdjusters.*;

/**
 * @author : YINAN
 * @date : 2023/8/14
 * @effect :
 */
public class Example001 {
    public static void main(String[] args) {
        LocalDate date1 = LocalDate.of(2023, 8, 15);
        LocalDate date2 = date1.with(dayOfWeekInMonth(1, DayOfWeek.WEDNESDAY));
        System.out.println(date2);
        LocalDate date3 = date1.with(nextOrSame(DayOfWeek.WEDNESDAY));
        System.out.println(date3);
        LocalDate date4 = date1.with(nextOrSame(DayOfWeek.MONDAY));
        System.out.println(date4);
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        System.out.println(zoneId);
    }
}
