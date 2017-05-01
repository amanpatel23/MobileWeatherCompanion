package com.example.aman.mobileweathercompanion;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.mobileweathercompanion.UI.AlertDialogFragment;
import com.example.aman.mobileweathercompanion.data.LongLat;
import com.example.aman.mobileweathercompanion.data.LongLatDB;
import com.example.aman.mobileweathercompanion.data.Zip;
import com.example.aman.mobileweathercompanion.data.ZipDb;
import com.example.aman.mobileweathercompanion.weather.CurrentWeather;
import com.example.aman.mobileweathercompanion.weather.Day;
import com.example.aman.mobileweathercompanion.weather.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.INVISIBLE;
import static android.widget.ImageView.OnClickListener;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    private Forecast mForecast;

    @BindView(R.id.rLayout) RelativeLayout relativeLayout;
    @BindView(R.id.time_label) TextView mTimeLabel;
    @BindView(R.id.temp_label) TextView mTemperatureLabel;
    @BindView(R.id.location_label) TextView mLocationLabel;
    @BindView(R.id.summary_label) TextView mSummaryLabel;
    @BindView(R.id.icon_imageView) ImageView mIconImageView;
    @BindView(R.id.refresh_imageView) ImageView mRefreshImageView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.editZip) TextView meditZip;
    @BindView(R.id.button2) Button zipButton;
    @BindView(R.id.percipChance) TextView mPercipChance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final Geocoder geocoder = new Geocoder(this);
        mProgressBar.setVisibility(View.INVISIBLE);
        mPercipChance.setVisibility(View.INVISIBLE);
        final boolean[] farenheight = {true};
        final boolean[] celcius = {false};

        final double[] latitude = {39.3938};
        final double[] longitude = {-76.6092};

        getForecast(latitude[0],longitude[0]);

        ArrayList<LongLat> myData = new ArrayList<LongLat>();
        ArrayList<Zip> myData2 = new ArrayList<Zip>();
        myData = getData();
        myData2 = getData2();
        int myData2num = myData2.size()-1;

        try{
            getForecast((myData.get(myData.size()-1).getLongitude()),(myData.get(myData.size()-1)).getLatitude());
        }
        catch (Exception e){

        }
        try {
            getZipForecast(myData2.get(myData2num).getZipString());

        }
        catch(Exception e){

        }

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude[0],longitude[0]);
            }
        });


        zipButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final String zip = meditZip.getText().toString();

                try {
                    List<Address> addresses = geocoder.getFromLocationName(zip, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        String message = String.format("Latitude: %f, Longitude: %f",
                                address.getLatitude(), address.getLongitude());
                        latitude[0] = address.getLatitude();
                        longitude[0] = address.getLongitude();
                        getForecast(address.getLatitude(),address.getLongitude());
                        mLocationLabel.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
                        setDb2(zip);
                    }
                } catch (IOException e) {

                }
            }

        });

        mSummaryLabel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPercipChance.getVisibility() == INVISIBLE) {
                    mPercipChance.setVisibility(View.VISIBLE);
                }
                else {
                    mPercipChance.setVisibility(View.INVISIBLE);
                }
            }
        });

        mTemperatureLabel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (farenheight[0] == true && celcius[0] == false) {
                    CurrentWeather current = mForecast.getCurrentWeather();
                    int f = current.getmTemp();
                    int c = ((f - 32) * 5) / 9;
                    mTemperatureLabel.setText(c + "");
                    celcius[0] = true;
                    farenheight[0] = false;
                }
                else if(farenheight[0] == false && celcius[0] == true) {
                    CurrentWeather current = mForecast.getCurrentWeather();
                    mTemperatureLabel.setText(current.getmTemp() + "");
                    celcius[0] = false;
                    farenheight[0] = true;
                }
            }
        });

    }
    public void setDb (double latitude, double longitude){
        LongLatDB db = new LongLatDB(this, null, null, 1);
        LongLat temp = new LongLat(longitude, latitude);
        db.addCord(temp);
    }

    public void setDb2 (String zip){
        ZipDb db = new ZipDb(this, null, null, 1);
        Zip temp = new Zip(zip);
        db.addCord(temp);
    }

    public ArrayList<LongLat> getData(){
        LongLatDB db = new LongLatDB(this, null, null, 1);
        ArrayList <LongLat> da = new ArrayList<LongLat>();
        da = db.getData();

        return da;
    }

    public ArrayList <Zip> getData2(){
        ZipDb db = new ZipDb(this, null, null, 1);
        ArrayList <Zip> da = new ArrayList<Zip>();
        da = db.getData();

        return da;
    }

    public void getZipForecast(String zip){
        final Geocoder geocoder = new Geocoder(this);

        final double[] latitude = {39.3938};
        final double[] longitude = {-76.6092};

        try {
            List<Address> addresses = geocoder.getFromLocationName(zip, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String message = String.format("Latitude: %f, Longitude: %f",
                        address.getLatitude(), address.getLongitude());
                latitude[0] = address.getLatitude();
                longitude[0] = address.getLongitude();
                getForecast(address.getLatitude(),address.getLongitude());
                mLocationLabel.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
            }
        } catch (IOException e) {

        }
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "b06f99a656f3a1b1405dff109e42c4a5";
        String forecastUrl = "https://api.darksky.net/forecast/" + apiKey + "/" + latitude + "," + longitude;

        if(isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception Caught", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception Caught", e);
                    }

                }
            });
        }
        else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if(mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDisplay() {
        CurrentWeather current = mForecast.getCurrentWeather();
        mTimeLabel.setText(current.getFormattedTime() + "");
        mTemperatureLabel.setText(current.getmTemp() + "");
        mSummaryLabel.setText(current.getmSummary() + "");
        mPercipChance.setText(Math.round(current.getmPrecipChance()) + "% Chance of Precipitation");
        Drawable drawable = getResources().getDrawable(current.getIconId());
        mIconImageView.setImageDrawable(drawable);

        if(current.getmTemp() > 76) {
            relativeLayout.setBackground(getResources().getDrawable(R.drawable.hot));
        }
        else if(current.getmTemp() > 60 && current.getmTemp() <= 76) {
            relativeLayout.setBackground(getResources().getDrawable(R.drawable.warm));
        }
        else {
            relativeLayout.setBackground(getResources().getDrawable(R.drawable.cold));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Forecast parseForecastDetails(String jsonData)throws JSONException{
        Forecast forecast = new Forecast();

        forecast.setCurrentWeather(getCurrentDetails(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));
        return forecast;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setmHumidity(currently.getDouble("humidity"));
        currentWeather.setmTime(currently.getLong("time"));
        currentWeather.setmIcon(currently.getString("icon"));
        currentWeather.setmPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setmSummary(currently.getString("summary"));
        currentWeather.setmTemp(currently.getDouble("temperature"));
        currentWeather.setmTimeZone(timezone);

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
    }


    private Day[] getDailyForecast(String jsonData)throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTemperatureMin(jsonDay.getDouble("temperatureMin"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;


        }
        return days;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view){
        Intent intent = new Intent(this,DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        startActivity(intent);
    }


}
