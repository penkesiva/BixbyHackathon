package com.samsung.mvgo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.samsung.mvgo.R;
import com.samsung.mvgo.model.ArrivalsResponse;
import com.samsung.mvgo.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MVGo";
    private List<ArrivalsResponse> dataArrayList;
    TextView tvNextArrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvNextArrival = (TextView) findViewById(R.id.tv_next_arrival);
        loadJSON();
    }

    private void loadJSON(){
        dataArrayList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.ridemvgo.org/").
                addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface requestInterface = retrofit.create(ApiInterface.class);
        Call<List<ArrivalsResponse>> call = requestInterface.getVehicleArrivals();
        call.enqueue(new Callback<List<ArrivalsResponse>>() {
            @Override
            public void onResponse(Call<List<ArrivalsResponse>> call, Response<List<ArrivalsResponse>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Reponse from MVGO " + statusCode);

                dataArrayList = response.body();

                tvNextArrival.setText("Next Arrival MVGo shuttle to SamsungCampus:\n" +
                        dataArrayList.get(0).getArrivalsList().get(0).getArrivalTime());
            }

            @Override
            public void onFailure(Call<List<ArrivalsResponse>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

}
