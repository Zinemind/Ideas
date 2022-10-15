package com.example.customcalendar;

import com.example.customcalendar.manager.CalendarManager;
import com.example.customcalendar.model.DateObject;
import com.example.customcalendar.model.StatusObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataObjectDummy {
    public static List<DateObject> getDummy(Date startDate, Date endDate){
        List<DateObject> result=new ArrayList<>();
        Calendar cal=Calendar.getInstance();
        cal.setTime(startDate);
        while(cal.getTime().before(endDate)){
            cal.add(Calendar.DATE,1);
            result.add(getDateObjectNew(cal));
        }
        return result;
    }

    public static DateObject getDateObjectNew(Calendar cal) {

        Boolean headStatus = false;
        double randomNumber=Math.random();
        if(randomNumber%2==0){
            headStatus=true;
        }
        int statusCount=3;
        Boolean[] status = new Boolean[statusCount];

        double randomNumber1=Math.random();
        if(randomNumber1%2==0){
            status[0]=true;
        }else{
            status[0]=false;
        }
        double randomNumber2=Math.random();
        if(randomNumber2%2==0){
            status[1]=true;
        }else{
            status[1]=false;
        }
        double randomNumber3=Math.random();
        if(randomNumber3%2==0){
            status[2]=true;
        }else{
            status[2]=false;
        }
        StatusObject statusObject = new StatusObject(status, headStatus);
        int activateTime = 1441;//maximum value
        return new DateObject(" ", CalendarManager.getDateToString(cal), statusObject, activateTime);
    }

}
