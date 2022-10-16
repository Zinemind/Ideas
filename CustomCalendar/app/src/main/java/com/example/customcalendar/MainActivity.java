package com.example.customcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.customcalendar.model.DateObject;
import com.example.customcalendar.ui.CalendarCustomView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements CalendarCustomView.CalendarListner{
    CalendarCustomView calendarView;
    List<DateObject> dateObjects = new ArrayList<DateObject>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarCustomView) findViewById(R.id.custom_calendar);

        calendarView.showCalender(TimeZone.getDefault(), CalendarCustomView.TITLETYPE.BLACK);//To set Title color black, use blue or read title type for the different title color
        dateObjects.clear();
        dateObjects.addAll(DataObjectDummy.getDummy(calendarView.getStartDate(),calendarView.getEndDate()));
        calendarView.setData(dateObjects);
        calendarView.dismissProcessDialog();
    }

    @Override
    public void onClickDate(Object object) {
        Date selectedDate = (Date) object;
        Toast.makeText(this, "Date :"+selectedDate.toString()+" selected", Toast.LENGTH_SHORT).show();
        calendarView.dismissProcessDialog();
    }

    @Override
    public void redirectToCurrentMonth(boolean sameMonth) {
        dateObjects.clear();
        dateObjects.addAll(DataObjectDummy.getDummy(calendarView.getStartDate(),calendarView.getEndDate()));
        calendarView.setData(dateObjects);
        calendarView.dismissProcessDialog();
    }

    @Override
    public void onMonthChanged() {
        dateObjects.clear();
        dateObjects.addAll(DataObjectDummy.getDummy(calendarView.getStartDate(),calendarView.getEndDate()));
        calendarView.setData(dateObjects);
        calendarView.dismissProcessDialog();
    }
}