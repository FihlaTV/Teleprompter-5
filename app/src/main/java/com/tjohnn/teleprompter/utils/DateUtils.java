package com.tjohnn.teleprompter.utils;

import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String dateToFormatString1(Date date){
        return String.format(Locale.ENGLISH, "%ta %tb %td %tY", date);
    }


}
