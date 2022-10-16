package com.example.customcalendar.manager;

import java.util.Calendar;

public class CalendarManager {

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            return false;
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isLessOfEqual(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) <= cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) <= cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR))
            return true;
        else if (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) <= cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) <= cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.HOUR_OF_DAY) <= cal2.get(Calendar.HOUR_OF_DAY) &&
                cal1.get(Calendar.MINUTE) < cal2.get(Calendar.MINUTE))
            return true;
        else
            return false;

    }
    public static String[] getDateValues(String date) {
        if (date == null)
            return null;
        String[] result = date.split("/");
        if (result.length == 3) {
            int month =Integer.parseInt(result[1]);
            month--;
            result[1]=Integer.toString(month);
            return result;
        }
        return null;
    }
    public static String getDateToString(Calendar calendar){
        return  calendar.get(Calendar.DAY_OF_MONTH) + "/" + String.format("%02d", (calendar.get(Calendar.MONTH) + 1)) + "/" + calendar.get(Calendar.YEAR);
    }
}
