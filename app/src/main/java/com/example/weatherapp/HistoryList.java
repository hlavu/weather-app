package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryList extends AppCompatActivity {

    ArrayList<String> cities;
    CityAdapter adapter;
    ListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        historyList = (ListView) findViewById(R.id.historyList);

        Intent myIntent = getIntent();
        if(myIntent != null){
            cities = myIntent.getStringArrayListExtra("cities");
            adapter = new CityAdapter(this, cities);
            historyList.setAdapter(adapter);
        }
    }
}