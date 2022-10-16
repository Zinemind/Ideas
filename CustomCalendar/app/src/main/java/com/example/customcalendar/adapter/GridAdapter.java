package com.example.customcalendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.customcalendar.manager.CalendarManager;
import com.example.customcalendar.R;
import com.example.customcalendar.model.DateObject;
import com.example.customcalendar.model.StatusObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class GridAdapter extends ArrayAdapter<DateObject> {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private String selectedDate;
    private Context context;
    private List<DateObject> dateObjects = new ArrayList<DateObject>();
    private TimeZone timeZone;
    private Calendar todayCalendar;

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public GridAdapter(Context context, List<DateObject> dateObjects, Calendar todayCalendar) {
        super(context, R.layout.single_cell_layout,dateObjects);
        this.context = context;
        this.dateObjects = dateObjects;
        this.todayCalendar = todayCalendar;
        this.timeZone = todayCalendar.getTimeZone();
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }
        TableLayout dateCell = (TableLayout) view.findViewById(R.id.dateCell);
        ImageView headStatus = (ImageView) view.findViewById(R.id.headStatus);
        TextView cellNumber = (TextView) view.findViewById(R.id.calendar_date_id);
        TableRow tableRow = (TableRow) view.findViewById(R.id.statusRow);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(20, TableRow.LayoutParams.WRAP_CONTENT);
        TextView textview;
        layoutParams.setMargins(1, 0, 0, 0);
        tableRow.removeAllViews();

        //read the date for the cell
        DateObject dateObject = dateObjects.get(position);
        Calendar dateCal = Calendar.getInstance(timeZone);
        String[] dateValues= CalendarManager.getDateValues(dateObject.getDisplayDate());
        if(dateValues!=null){
            int dayValue = Integer.parseInt(dateValues[0]);
            int displayMonth = Integer.parseInt(dateValues[1]);
            int displayYear = Integer.parseInt(dateValues[2]);
            dateCal.set(Calendar.DAY_OF_MONTH,dayValue);
            dateCal.set(Calendar.MONTH,displayMonth);
            dateCal.set(Calendar.YEAR,displayYear);
            displayMonth++;

        int currentMonth = todayCalendar.get(Calendar.MONTH) + 1;
        int currentYear = todayCalendar.get(Calendar.YEAR);

        //add the ash color to the background of the selected date
        if (dateObject.getDisplayDate().equals(this.selectedDate)) {
            dateCell.setBackgroundColor(context.getResources().getColor(R.color.backgroundash));
        } else {
            dateCell.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }

        //set the color black only for the dates in the month
        if (displayMonth == currentMonth && displayYear == currentYear) {
            cellNumber.setTextColor(context.getResources().getColor(R.color.colorBlack));
        } else {
            cellNumber.setTextColor(context.getResources().getColor(R.color.colorDisabledNumber));
        }
        //Add day to calendar
        cellNumber.setText(String.valueOf(dayValue));
        cellNumber.setTag(dateCal);

        Calendar tempCalendar = Calendar.getInstance(timeZone);
        //check the cell is current date
        if (CalendarManager.isSameDay(tempCalendar, dateCal)) {
            Integer activeTime = dateObject.getActivateTime();
            if (activeTime != null) {
                if (activeTime <= ((tempCalendar.get(Calendar.HOUR_OF_DAY) * 60) + tempCalendar.get(Calendar.MINUTE))) {
                    cellNumber.setBackgroundResource(R.drawable.round);
                    cellNumber.setTextColor(context.getResources().getColor(R.color.colorWhite));
                }
            }
        }

        Calendar nextDay = dateCal;
        nextDay.add(Calendar.DATE, 1);
        if (CalendarManager.isSameDay(tempCalendar, nextDay)) {
            DateObject nextObject = dateObjects.get(position + 1);
            if (nextObject != null) {
                Integer activeTime = nextObject.getActivateTime();
                if (activeTime != null) {
                    if (activeTime > ((tempCalendar.get(Calendar.HOUR_OF_DAY) * 60) + tempCalendar.get(Calendar.MINUTE))) {
                        cellNumber.setBackgroundResource(R.drawable.round);
                        cellNumber.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    }
                }
            }
        }

        StatusObject statusObject = dateObject.getStatus();
        if (statusObject != null) {
            //top status condition
            if (statusObject.getStarRatingStatus() != null && statusObject.getStarRatingStatus()) {
                headStatus.setVisibility(View.VISIBLE);
            }
            //bottom status condition
            if (statusObject.getShiftStatus() != null) {
                for (int i = 0; i < statusObject.getShiftStatus().length; i++) {
                    Boolean singleStatus = statusObject.getShiftStatus()[i];
                    textview = new TextView(context);
                    textview.setText(dateObject.getDisplayName());
                    textview.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    textview.setWidth(20);
                    textview.setHeight(10);
                    tableRow.addView(textview, layoutParams);
                    if (singleStatus) {
                        textview.setBackgroundColor(context.getResources().getColor(R.color.colorAlert));
                    } else {
                        textview.setBackgroundColor(context.getResources().getColor(R.color.colorAlertError));
                    }
                }
            }
        }
        }

        return view;
    }

    @Override
    public int getCount() {
        return dateObjects.size();
    }


    @Override
    public DateObject getItem(int position) {
        return dateObjects.get(position);
    }

    @Override
    public int getPosition(DateObject item) {
        return dateObjects.indexOf(item);
    }

}