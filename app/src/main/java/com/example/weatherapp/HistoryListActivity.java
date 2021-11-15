package com.example.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryListActivity extends AppCompatActivity implements IBindData {

    ArrayList<String> cities;
    ArrayList<WeatherData> weatherDataList;
    CityAdapter adapter;
    ListView historyList;

    IBindData iBindData;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        historyList = (ListView) findViewById(R.id.historyList);

        iBindData = this;


        Intent myIntent = getIntent();
        if(myIntent != null){
            AppController controller = new AppController(iBindData);
            cities = myIntent.getStringArrayListExtra("cities");
            weatherDataList = new ArrayList<>();
            adapter = new CityAdapter(this, weatherDataList);
            historyList.setAdapter(adapter);
            for (String city: cities){
                weatherDataList.add(new WeatherData(city));
                adapter.notifyDataSetChanged();
                controller.getData(this, city);
            }
        }
    }

    @Override
    public void bindData(WeatherData weatherData) {
        Log.e("DATA", weatherData.city);
        for (int i = 0; i< weatherDataList.size(); i++){
            if(weatherDataList.get(i).city.equals(weatherData.city)) {
                weatherDataList.set(i, weatherData);
            }
        }
        adapter.notifyDataSetChanged();
    }
}