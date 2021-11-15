package com.example.weatherapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WeatherData{
    String APIKEY_ = "c22541c8c24aef3809ab3e168f7b1d7e";
    String city;
    String weather;
    String dateStr;
    String timeStr;
    String sunRiseStr;
    String sunSetStr;
    int tempC;
    int tempF;
    int feelTemp;
    int humidity;
    double windSpeed;
    String err = "";

    WeatherData(){
        this.city = "";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getData(String cityName, Context context, MainActivity mainObj, View myView, boolean fromMain, boolean fromChild){
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + APIKEY_;
        // creates request queue
        RequestQueue mRequestQueue;
        // instantiates the cache
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        // setups the network to use the HTTPURLConnection client
        Network network = new BasicNetwork(new HurlStack());
        // instantiates the request queue
        mRequestQueue = new RequestQueue(cache, network);
        // starts the queue
        mRequestQueue.start();

        @SuppressLint("SetTextI18n") JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiUrl, null, response -> {
                    try {
                        // gets JSON objects
                        JSONObject mainWeather = response.getJSONObject("main");
                        JSONObject windInfo = response.getJSONObject("wind");

                        JSONObject sunInfo = response.getJSONObject("sys");

                        // converts unix time to date
                        Date dateInfo = new Date(response.getLong("dt")*1000);
                        Date sunRiseD = new Date(sunInfo.getLong("sunrise") * 1000);
                        Date sunSetD = new Date(sunInfo.getLong("sunset") * 1000);
                        int timezone = response.getInt("timezone");

                        // formats output
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
                        dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneOffset.ofTotalSeconds(timezone)));
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");
                        timeFormat.setTimeZone(TimeZone.getTimeZone(ZoneOffset.ofTotalSeconds(timezone)));

                        city = cityName.toUpperCase(Locale.ROOT);
                        this.weather = response.getJSONArray("weather").getJSONObject(0).getString("main");

                        this.dateStr = dateFormat.format(dateInfo);
                        timeStr = timeFormat.format(dateInfo);
                        sunRiseStr = timeFormat.format(sunRiseD);
                        sunSetStr = timeFormat.format(sunSetD);

                        // converts °K to °C
                        this.tempC = (int) (Math.round(mainWeather.getDouble("temp") - 273.15));
                        feelTemp = (int)(Math.round(mainWeather.getDouble("feels_like") - 273.15));

                        // converts °K to °F
                        tempF = (int) (Math.round(mainWeather.getDouble("temp") * 9/5 - 459.67));
                        // gets weather information
                        humidity = mainWeather.getInt("humidity");
                        windSpeed = windInfo.getDouble("speed");
                        if(fromMain){
                            mainObj.setData(this);
                        } else if(fromChild) {
                            TextView city = (TextView) myView.findViewById(R.id.city);
                            TextView time = (TextView) myView.findViewById(R.id.time);
                            TextView temp = (TextView) myView.findViewById(R.id.temp);
                            ImageView bg = (ImageView) myView.findViewById(R.id.bg);

                            city.setText(this.city.toUpperCase());
                            time.setText(timeStr + " - " + weather);
                            temp.setText(tempC + "°");
//                            historyObj.setData(this);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                        error -> {
                            // displays error
                            err = "Invalid city!!!";
                        });

        // Add the request to the RequestQueue
        mRequestQueue.add(jsObjRequest);
    }


}
