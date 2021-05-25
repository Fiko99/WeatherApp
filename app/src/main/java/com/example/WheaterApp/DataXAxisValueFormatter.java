package com.example.WheaterApp;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class DataXAxisValueFormatter extends ValueFormatter {
    ArrayList<String> dates;

    public DataXAxisValueFormatter(ArrayList<String> dates) {
        this.dates = dates;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int idx = (int)value;
        return dates.get(idx);

        //java.util.Date time=new java.util.Date((dates.get(idx) + 3*3600)*1000);

        //String pattern = "HH:mm";
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


        //return String.valueOf(simpleDateFormat.format(time));
    }
}
