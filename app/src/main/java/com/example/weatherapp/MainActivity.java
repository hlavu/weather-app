package com.example.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton search;
    EditText city;
    TextView location;
    TextView temperature;
    TextView displayWeather;
    TextView currentDate;
    TextView currentTime;
    TextView feels;
    ImageView background;
    TextView humid;
    TextView wind;
    TextView sunRise;
    TextView sunSet;
    ImageButton list;
    ArrayList<String> cityList = new ArrayList<>();
    WeatherData currCityData;
    // uses this value to set back ground based on the current time
    LocalTime myTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sets views
        city = (EditText) findViewById(R.id.searchLocation);
        search = (ImageButton) findViewById(R.id.searchButton);
        list = (ImageButton) findViewById(R.id.listButton);
        location = (TextView) findViewById(R.id.location);
        temperature = (TextView) findViewById(R.id.temperature);
        displayWeather = (TextView) findViewById(R.id.weather);
        feels = (TextView) findViewById(R.id.feel);
        background = (ImageView) findViewById(R.id.bg);
        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);
        humid = (TextView) findViewById(R.id.humid);
        wind = (TextView) findViewById(R.id.wind);
        sunRise = (TextView) findViewById(R.id.dawn);
        sunSet = (TextView) findViewById(R.id.dusk);

        // gets current time
        myTime =  LocalTime.now();
        setBackground(myTime.getHour(), background);

        search.setOnClickListener(view -> {
            // hides soft keyboard
            hideSoftKeyboard(MainActivity.this);

            // if user did not provide a city name, show toast to announce
            if(city.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Please provide a city name!!!", Toast.LENGTH_SHORT).show();
            } else {
                // calls function requesting API
                currCityData = new WeatherData();
                currCityData.getData(city.getText().toString(), this.getApplicationContext(), MainActivity.this, null,true, false);
                city.setText(""); // resets value of edit text
            }

        });

        list.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, HistoryList.class);
            myIntent.putStringArrayListExtra("cities", this.cityList);
            startActivity(myIntent);
        });

    }

    @SuppressLint("SetTextI18n")
    public void setData(WeatherData cityData) {
        if(cityData.err.isEmpty()){
        // sets content for xml views
        setBackground(Integer.parseInt(cityData.timeStr.substring(0,2)), background);
        currentDate.setText(cityData.dateStr);
        currentTime.setText(cityData.timeStr);
        temperature.setText(cityData.tempC + "°");
        location.setText(cityData.city.toUpperCase());
        displayWeather.setText(cityData.weather);
        feels.setText("Feels like: " + cityData.feelTemp + "°C");
        humid.setText(cityData.humidity + "%");
        wind.setText(cityData.windSpeed + "m/s");
        sunRise.setText(cityData.sunRiseStr);
        sunSet.setText(cityData.sunSetStr);
        // add city to CityList
        this.cityList.add(cityData.city.toLowerCase());
        }  else {
            Toast.makeText(getApplicationContext(), "Invalid city!!!", Toast.LENGTH_SHORT).show();
        }
    }

    // gets time and changes img src based on time
    @SuppressLint("UseCompatLoadingForDrawables")
    void setBackground(int hour, ImageView imgV){
        Drawable src = getDrawable(R.drawable.night_);
        displayWeather.setTextColor(Color.parseColor("#ffffff"));

        if(hour >= 4 && hour < 7 ) {
            src = getDrawable(R.drawable.dawn_);
            displayWeather.setTextColor(Color.parseColor("#000000"));
        } else if(hour >=7 && hour < 16){
            src = getDrawable(R.drawable.day_);
        } else if(hour >= 16 && hour < 19 ){
            src = getDrawable(R.drawable.dusk_);
            displayWeather.setTextColor(Color.parseColor("#000000"));
        }
        imgV.setBackground(src);
    }

    // hides soft keyboard when search btn is clicked
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
            activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}






