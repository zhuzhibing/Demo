package net.luculent.automatioin.laks;

import org.springframework.format.datetime.standard.DateTimeContext;
import org.springframework.format.datetime.standard.DateTimeContextHolder;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 9:20 2018/1/31
 * @Modified By:
 */
public class Test {

    public static void main(String[] args) {
        final Clock clock = Clock.systemUTC();

        System.out.println( clock.instant() );
        System.out.println( clock.millis() );

        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println("dateTime from the system is:"+dateTime);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime d6 = LocalDateTime.parse("2013/12/31 23:59:59", formatter);
        System.out.println(formatter.format(dateTime));

        Chronology c  = HijrahChronology.INSTANCE;
        ChronoLocalDateTime d = c.localDateTime(LocalDateTime.now());
        System.out.println(d);


//        System.out.println(DateTimeContextHolder.getFormatter(formatter, Locale.US).parse("2013/12/31 23:59:59"));
//        DateTimeContextHolder.getDateTimeContext().getChronology();

    }
}
