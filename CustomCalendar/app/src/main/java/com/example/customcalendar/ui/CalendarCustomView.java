package com.example.customcalendar.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customcalendar.manager.CalendarManager;
import com.example.customcalendar.adapter.GridAdapter;
import com.example.customcalendar.R;
import com.example.customcalendar.model.DateObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarCustomView extends LinearLayout {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private Button todayButton;
    private GridView calendarGridView;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal;
    private Context context;
    private GridAdapter mAdapter;
    public CalendarListner calendarListner = null;
    public List<DateObject> dateObjects = new ArrayList<DateObject>();
    private String startDate, endDate;
    private TimeZone timezone = Calendar.getInstance().getTimeZone();
    private Boolean isCalendarVisible = false;
    Calendar calendar;
    ProgressDialog mProcessDialog;

    @Override
    public boolean canResolveLayoutDirection() {
        return super.canResolveLayoutDirection();
    }

    public CalendarCustomView(Context context) {
        super(context);
        calendarListner = (CalendarListner) context;

    }

    public CalendarCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        calendarListner = (CalendarListner) context;
    }

    public void showCalender(TimeZone timezone) {
        isCalendarVisible = true;
        setTimezone(timezone);
        cal = Calendar.getInstance(this.timezone);
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();
        setTodayClickEvents();
    }

    private void setTodayClickEvents() {
        todayButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                Boolean isSameMonth = false;
                calendar = Calendar.getInstance(getTimezone());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.clear(Calendar.MINUTE);
                calendar.clear(Calendar.SECOND);
                calendar.clear(Calendar.MILLISECOND);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                startDate = CalendarManager.getDateToString(calendar);

                if (cal.get(Calendar.MONTH) == (calendar.get(Calendar.MONTH))) {
                    isSameMonth = true;
                }

                calendar = Calendar.getInstance(getTimezone());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.clear(Calendar.MINUTE);
                calendar.clear(Calendar.SECOND);
                calendar.clear(Calendar.MILLISECOND);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                endDate = CalendarManager.getDateToString(calendar);

                cal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                cal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

                setUpCalendarAdapter();
                calendarListner.redirectToCurrentMonth(isSameMonth);

            }
        });
    }

    public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        calendarListner = (CalendarListner) context;
    }

    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        calendarGridView = (GridView) view.findViewById(R.id.calendar_grid);
        todayButton = (Button) view.findViewById(R.id.today);
    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
                calendarListner.onMonthChanged();
            }
        });
    }

    private void setNextButtonClickEvent() {
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
                calendarListner.onMonthChanged();
            }
        });
    }

    private void setGridCellClickEvents() {
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showProgressBar();
                Calendar todayCalender = Calendar.getInstance(getTimezone());
                Calendar selectedCalendar = Calendar.getInstance(getTimezone());
                DateObject selectedDateObject = ((DateObject) mAdapter.getItem(position));
                if (selectedDateObject != null && selectedDateObject.getStatus() != null && selectedDateObject.getStatus().getShiftStatus() != null && selectedDateObject.getStatus().getShiftStatus().length > 0) {
                 String[] dateValues=CalendarManager.getDateValues(selectedDateObject.getDisplayDate());
                   if(dateValues!=null){
                       selectedCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateValues[0]));
                       selectedCalendar.set(Calendar.MONTH,Integer.parseInt(dateValues[1]));
                       selectedCalendar.set(Calendar.YEAR,Integer.parseInt(dateValues[2]));
                   }

                    Calendar temp = (Calendar) selectedCalendar.clone();
                    mAdapter.setSelectedDate(selectedDateObject.getDisplayDate());
                    mAdapter.notifyDataSetChanged();
                    Integer activeTime = selectedDateObject.getActivateTime();
                    if (activeTime != null) {
                        if (activeTime < 0) {
                            //this day activated activation time is from previous day
                            temp.add(Calendar.DAY_OF_YEAR, -1);
                            activeTime += 1440;
                        }
                        temp.set(Calendar.HOUR_OF_DAY, (activeTime / 60));
                        temp.set(Calendar.MINUTE, (activeTime % 60));
                        if (CalendarManager.isLessOfEqual(temp, todayCalender)) {
                            selectedCalendar.set(Calendar.HOUR_OF_DAY, todayCalender.get(Calendar.HOUR_OF_DAY));
                            selectedCalendar.set(Calendar.MINUTE, todayCalender.get(Calendar.MINUTE));
                            calendarListner.onClickDate(selectedCalendar.getTime());
                        } else {
                            dismissProcessDialog();
                            Toast.makeText(context, "You cannot select a future date.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dismissProcessDialog();
                        Toast.makeText(context, "Date active time is missing.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dismissProcessDialog();
                    Toast.makeText(context, "No shift for this date.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface CalendarListner {
        public void onClickDate(Object object);

        public void redirectToCurrentMonth(boolean sameMonth);

        public void onMonthChanged();
    }


    private void setUpCalendarAdapter() {
        List<DateObject> allDateObjects = new ArrayList<DateObject>();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        mCal.set(Calendar.HOUR_OF_DAY, 0);
        mCal.set(Calendar.MINUTE, 0);
        while (allDateObjects.size() < MAX_CALENDAR_COLUMN) {
            String dateInfo =  mCal.get(Calendar.DAY_OF_MONTH) + "/" + (mCal.get(Calendar.MONTH) + 1)+ "/" + mCal.get(Calendar.YEAR);
            int pos = dateObjects.indexOf(new DateObject(dateInfo));
            if (pos > -1) {
                allDateObjects.add(dateObjects.get(pos));
            } else {
                allDateObjects.add(new DateObject(dateInfo));
            }
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + allDateObjects.size());
        formatter.setTimeZone(getTimezone());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        mAdapter = new GridAdapter(context, allDateObjects, cal);
        calendarGridView.setAdapter(mAdapter);

        startDate = allDateObjects.get(0).getDisplayDate();
        endDate = allDateObjects.get(MAX_CALENDAR_COLUMN - 1).getDisplayDate();
    }

    public void setData(List<DateObject> listDateObjects) {
        dateObjects = listDateObjects;
        setUpCalendarAdapter();
    }

    public Date getStartDate() {

        calendar = Calendar.getInstance(getTimezone());
        String[] dateValue=CalendarManager.getDateValues(startDate);
        if(dateValue!=null) {
            calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateValue[0]));
            calendar.set(Calendar.MONTH,Integer.parseInt(dateValue[1]) );
            calendar.set(Calendar.YEAR, Integer.parseInt(dateValue[2]));
        }
        calendar.add(Calendar.DAY_OF_YEAR, -14);
        return calendar.getTime();
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = timezone;
    }

    public Date getEndDate() {
        calendar = Calendar.getInstance(getTimezone());
        String[] dateValue=CalendarManager.getDateValues(endDate);
        if(dateValue!=null) {
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateValue[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(dateValue[1]));
            calendar.set(Calendar.YEAR, Integer.parseInt(dateValue[2]));
        }
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        endDate =   calendar.get(Calendar.DAY_OF_MONTH) + "/" +  (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        return calendar.getTime();
    }

    private void showProgressBar() {
        if (mProcessDialog == null)
            mProcessDialog = ProgressDialog.show(this.context, "Calendar", "Please wait...");
        Window window = mProcessDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setGravity(Gravity.CENTER);
    }

    public void dismissProcessDialog() {
        if (mProcessDialog != null)
            mProcessDialog.dismiss();
        mProcessDialog = null;
    }


}