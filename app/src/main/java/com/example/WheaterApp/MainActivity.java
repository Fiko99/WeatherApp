package com.example.WheaterApp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> weather = new ArrayList<>();
    Spinner s;
    MyPojo forecast;
    LineChart chart;
    String name = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weather.add("Temperatura");
        weather.add("Ciśnienie atmosferyczne");
        weather.add("Prędkość wiatru");
        weather.add("Zachmurzenie");
        initChart();
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weather);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s = findViewById(R.id.spinner);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                while (forecast == null);
                if(item.equals("Temperatura")) {
                    drawTemperatureChart();
                }
                if(item.equals("Ciśnienie atmosferyczne")) {
                    drawPressureChart();
                }
                if(item.equals("Prędkość wiatru")) {
                    drawWindSpeedChart();
                }
                if(item.equals("Zachmurzenie")) {
                    drawCloudyChart();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                downloadData();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                downloadData();
            }
        });
        downloadData();
    }

    private void downloadData() {
        try {
            URL nbpEndpoint = new URL("https://api.openweathermap.org/data/2.5/forecast?zip=43-400,PL&appid=1680ed0b219d314f917f53151f37a57a&lang=pl&units=metric");
            HttpURLConnection nbpConnection = (HttpURLConnection) nbpEndpoint.openConnection();
            nbpConnection.setRequestProperty("Accept", "application/json");
            if (nbpConnection.getResponseCode() == 200) {
                InputStreamReader is = new InputStreamReader(nbpConnection.getInputStream());
                Gson gson = new Gson();
                forecast = gson.fromJson(is, MyPojo.class);
                nbpConnection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initChart() {
        chart = findViewById(R.id.chart1);
        chart.setBackgroundColor(Color.WHITE);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
    }

    private void drawTemperatureChart() {
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        List[] list = forecast.getList();
        for(int i = 0; i<list.length; i++) {
            float temp = Float.parseFloat(forecast.getList()[i].getMain().getTemp());
            Entry entry = new Entry((float) i,temp);
            values.add(entry);
            dates.add((forecast.getList()[i].getDt_txt().substring(11,19)));
        }
        name = "Temperatura [°C]";
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new DataXAxisValueFormatter(dates));
        LineDataSet set1;
        set1 = new LineDataSet(values, name);
        set1.setLineWidth(3f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    private void drawPressureChart() {
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        List[] list = (List[]) forecast.getList();
        for(int i = 0; i<list.length; i++) {
            float temp = Float.parseFloat(forecast.getList()[i].getMain().getPressure());
            Entry entry = new Entry((float) i,temp);
            values.add(entry);
            dates.add((forecast.getList()[i].getDt_txt().substring(11,19)));
        }
        name = "Cisnienie [hPa]";
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new DataXAxisValueFormatter(dates));
        LineDataSet set1;
        set1 = new LineDataSet(values, name);
        set1.setLineWidth(3f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    private void drawWindSpeedChart() {
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        List[] list = (List[]) forecast.getList();
        for(int i = 0; i<list.length; i++) {
            float temp = Float.parseFloat(forecast.getList()[i].getWind().getSpeed());
            Entry entry = new Entry((float) i,temp);
            values.add(entry);
            dates.add((forecast.getList()[i].getDt_txt().substring(11,19)));
        }
        name = "Predkosc wiatru [m/s]";
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new DataXAxisValueFormatter(dates));
        LineDataSet set1;
        set1 = new LineDataSet(values, name);
        set1.setLineWidth(3f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    private void drawCloudyChart() {
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        List[] list = (List[]) forecast.getList();
        for(int i = 0; i<list.length; i++) {
            float temp = Float.parseFloat(forecast.getList()[i].getClouds().getAll());
            Entry entry = new Entry((float) i,temp);
            values.add(entry);
            dates.add((forecast.getList()[i].getDt_txt().substring(11,19)));
        }
        name = "Zachmurzenie [%]";
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new DataXAxisValueFormatter(dates));
        LineDataSet set1;
        set1 = new LineDataSet(values, name);
        set1.setLineWidth(3f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }
}