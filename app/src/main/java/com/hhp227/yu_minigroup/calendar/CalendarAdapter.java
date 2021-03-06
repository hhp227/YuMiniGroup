package com.hhp227.yu_minigroup.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hhp227.yu_minigroup.R;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CalendarAdapter extends BaseAdapter {
    static final int FIRST_DAY_OF_WEEK = 0;

    Context context;

    Calendar cal;

    public String[] days;

    //OnAddNewEventClick mAddEvent;

    ArrayList<Day> dayList = new ArrayList<>();

    public CalendarAdapter(Context context, Calendar cal) {
        this.cal = cal;
        this.context = context;
        cal.set(Calendar.DAY_OF_MONTH, 1);
        refreshDays();
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int position) {
        return dayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (position >= 0 && position < 7) {
            v = vi.inflate(R.drawable.calendar_day_of_week, null);
            TextView day = v.findViewById(R.id.textView1);

            if (position == 0) {
                day.setText("일");
            } else if (position == 1) {
                day.setText("월");
            } else if (position == 2) {
                day.setText("화");
            } else if (position == 3) {
                day.setText("수");
            } else if (position == 4) {
                day.setText("목");
            } else if (position == 5) {
                day.setText("금");
            } else if (position == 6) {
                day.setText("토");
            }

        } else {
            v = vi.inflate(R.drawable.calendar_day_view, null);
            FrameLayout today = v.findViewById(R.id.today_frame);
            Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
            Day d = dayList.get(position);
            if (d.getYear() == cal.get(Calendar.YEAR) && d.getMonth() == cal.get(Calendar.MONTH) && d.getDay() == cal.get(Calendar.DAY_OF_MONTH))
                today.setVisibility(View.VISIBLE);
            else
                today.setVisibility(View.GONE);

            TextView dayTV = v.findViewById(R.id.textView1);

            RelativeLayout rl = v.findViewById(R.id.rl);
            ImageView iv = v.findViewById(R.id.imageView1);
            ImageView blue = v.findViewById(R.id.imageView2);
            ImageView purple = v.findViewById(R.id.imageView3);
            ImageView green = v.findViewById(R.id.imageView4);
            ImageView orange = v.findViewById(R.id.imageView5);
            ImageView red = v.findViewById(R.id.imageView6);

            blue.setVisibility(View.VISIBLE);
            purple.setVisibility(View.VISIBLE);
            green.setVisibility(View.VISIBLE);
            purple.setVisibility(View.VISIBLE);
            orange.setVisibility(View.VISIBLE);
            red.setVisibility(View.VISIBLE);

            iv.setVisibility(View.VISIBLE);
            dayTV.setVisibility(View.VISIBLE);
            rl.setVisibility(View.VISIBLE);

            Day day = dayList.get(position);

            if (day.getNumOfEvenets() > 0) {
                Set<Integer> colors = day.getColors();

                iv.setVisibility(View.INVISIBLE);
                blue.setVisibility(View.INVISIBLE);
                purple.setVisibility(View.INVISIBLE);
                green.setVisibility(View.INVISIBLE);
                purple.setVisibility(View.INVISIBLE);
                orange.setVisibility(View.INVISIBLE);
                red.setVisibility(View.INVISIBLE);
                if (colors.contains(0)) {
                    iv.setVisibility(View.VISIBLE);
                }
                if (colors.contains(2)) {
                    blue.setVisibility(View.VISIBLE);
                }
                if (colors.contains(4)) {
                    purple.setVisibility(View.VISIBLE);
                }
                if (colors.contains(5)) {
                    green.setVisibility(View.VISIBLE);
                }
                if (colors.contains(3)) {
                    orange.setVisibility(View.VISIBLE);
                }
                if (colors.contains(1)) {
                    red.setVisibility(View.VISIBLE);
                }
            } else {
                iv.setVisibility(View.INVISIBLE);
                blue.setVisibility(View.INVISIBLE);
                purple.setVisibility(View.INVISIBLE);
                green.setVisibility(View.INVISIBLE);
                purple.setVisibility(View.INVISIBLE);
                orange.setVisibility(View.INVISIBLE);
                red.setVisibility(View.INVISIBLE);
            }
            if (day.getDay() == 0) {
                rl.setVisibility(View.GONE);
            } else {
                dayTV.setVisibility(View.VISIBLE);
                dayTV.setText(String.valueOf(day.getDay()));
            }
        }
        return v;
    }

    public void refreshDays() {
        // clear items
        dayList.clear();

        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + 7;
        int firstDay = (int)cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        TimeZone tz = TimeZone.getDefault();

        // figure size of the array
        if (firstDay == 1) {
            days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
        } else {
            days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
        }

        int j = FIRST_DAY_OF_WEEK;

        // populate empty days before first real day
        if (firstDay > 1) {
            for (j = 0; j < (firstDay - FIRST_DAY_OF_WEEK) + 7; j++) {
                days[j] = "";
                Day d = new Day(context,0,0,0);
                dayList.add(d);
            }
        } else {
            for (j = 0; j < (FIRST_DAY_OF_WEEK * 6) + 7; j++) {
                days[j] = "";
                Day d = new Day(context,0,0,0);
                dayList.add(d);
            }
            j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
        }

        // populate days
        int dayNumber = 1;

        if (j > 0 && dayList.size() > 0 && j != 1) {
            dayList.remove(j - 1);
        }

        for (int i = j - 1; i < days.length; i++) {
            Day d = new Day(context, dayNumber, year, month);

            Calendar cTemp = Calendar.getInstance();
            cTemp.set(year, month, dayNumber);
            int startDay = Time.getJulianDay(cTemp.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cTemp.getTimeInMillis())));

            d.setAdapter(this);
            d.setStartDay(startDay);

            days[i] = "" + dayNumber;
            dayNumber++;
            dayList.add(d);
        }
    }

    public int getPrevMonth() {
        if (cal.get(Calendar.MONTH) == cal.getActualMinimum(Calendar.MONTH)) {
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR - 1));
        }
        int month = cal.get(Calendar.MONTH);
        if (month == 0) {
            return month = 11;
        }

        return month - 1;
    }

    public int getMonth() {
        return cal.get(Calendar.MONTH);
    }

//	public abstract static class OnAddNewEventClick{
//		public abstract void onAddNewEventClick();
//	}
}