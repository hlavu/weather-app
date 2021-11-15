package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class CityAdapter extends BaseAdapter {
    ArrayList<WeatherData> weatherDataList;
    Context context;

    public CityAdapter(Context context, ArrayList<WeatherData> weatherDataList){
        this.context = context;
        this.weatherDataList = weatherDataList;
    }
    @Override
    public int getCount() {
        return weatherDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WeatherData data = weatherDataList.get(i);
        View myView;
        if(view == null){
            myView = LayoutInflater.from(context).inflate(R.layout.activity_history_list,null);
        } else myView = view;
        TextView city = (TextView) myView.findViewById(R.id.city);
        TextView time = (TextView) myView.findViewById(R.id.time);
        TextView temp = (TextView) myView.findViewById(R.id.temp);
        ImageView bg = (ImageView) myView.findViewById(R.id.bg);

        city.setText(data.city.toUpperCase());
        time.setText(data.timeStr + " - " + data.weather);
        temp.setText(data.tempC + "°");
        return myView;
    }

    @SuppressLint("SetTextI18n")
    public void setData(WeatherData cityData) {

//        setBackground(Integer.parseInt(cityData.timeStr.substring(0,2)), bg);

    }

    // gets time and changes img src based on time
//    @SuppressLint("UseCompatLoadingForDrawables")
//    void setBackground(int hour, ImageView imgV){
//        Drawable src = getDrawable(R.drawable.night_);
//        displayWeather.setTextColor(Color.parseColor("#ffffff"));
//
//        if(hour >= 4 && hour < 7 ) {
//            src = getDrawable(R.drawable.dawn_);
//            displayWeather.setTextColor(Color.parseColor("#000000"));
//        } else if(hour >=7 && hour < 16){
//            src = getDrawable(R.drawable.day_);
//        } else if(hour >= 16 && hour < 19 ){
//            src = getDrawable(R.drawable.dusk_);
//            displayWeather.setTextColor(Color.parseColor("#000000"));
//        }
//        imgV.setBackground(src);
//    }


}
