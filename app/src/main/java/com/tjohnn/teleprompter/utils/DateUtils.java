package com.tjohnn.teleprompter.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String dateToFormatString1(Date date){
        return String.format(Locale.ENGLISH, "%1$ta %1$tb %1$td %1$tY", date);
    }

    /**
     * Converts calendar to time of 12hr format e.g 11:00 AM
     *
     * @param calendar {@link Calendar} to get date info from
     * @return String
     */
    public static String calendarToTime12(Calendar calendar){
        return String.format(Locale.ENGLISH, "%1$tH:%1$tM %1$Tp", calendar);
    }

    /**
     * Converts calendar to date format of form '1 Jul 2018'
     *
     * @param calendar {@link Calendar} to get date info from
     * @return String
     */
    public static String calendarToDateFormat(Calendar calendar){
        return String.format(Locale.ENGLISH, "%1$td %1$tb %1$tY", calendar);
    }

    /**
     * Converts date to date string format of form '1 Jul 2018 11:00AM'
     *
     * @param date {@link Calendar} to get date info from
     * @return String
     */
    public static String dateToDateTimeString(Date date){
        return String.format(Locale.ENGLISH, "%1$td %1$tb %1$tY %1$tH:%1$tM %1$Tp", date);
    }


}
