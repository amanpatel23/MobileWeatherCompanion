package com.example.aman.mobileweathercompanion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aman.mobileweathercompanion.weather.Day;

/**
 * Created by Aman on 4/15/17.
 */

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder>{

    private Day[] mDays;

    public DayAdapter(Day[] days){
        mDays = days;

    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card ,parent ,false);
        DayViewHolder viewHolder = new DayViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {
        holder.bindDay(mDays[position]);

    }

    @Override
    public int getItemCount() {
        return mDays.length;
    }

    public class DayViewHolder extends RecyclerView.ViewHolder{

        public TextView mDayLabel;
        public TextView mSummaryLabel;
        public TextView mTemperatureLabel;
        public ImageView mIconImageView;

        public DayViewHolder(View itemView) {
            super(itemView);

            mDayLabel =(TextView) itemView.findViewById(R.id.day);
            mSummaryLabel=(TextView) itemView.findViewById(R.id.summary);
            mTemperatureLabel=(TextView) itemView.findViewById(R.id.temp_label);
            mIconImageView=(ImageView) itemView.findViewById(R.id.weather_icon);

        }
        public void bindDay(Day day){
            mDayLabel.setText(day.getDayOfTheWeek());
            mSummaryLabel.setText(day.getSummary()+ "");
            mTemperatureLabel.setText(day.getTemperatureMin() + " - " + day.getTemperatureMax() + " degrees");
            mIconImageView.setImageResource(day.getIconId());


        }
    }


}