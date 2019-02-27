package com.samsung.mvgo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.samsung.mvgo.R;
import com.samsung.mvgo.model.ArrivalsResponse;
import com.samsung.mvgo.model.Routes;
import com.samsung.mvgo.rest.ApiInterface;

import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MVGo";
//    private List<ArrivalsResponse> dataArrayList;
    private List<Routes> mStopsList;
    TextView tvNextArrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvNextArrival = (TextView) findViewById(R.id.tv_next_arrival);
        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.ridemvgo.org/").
                addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface requestInterface = retrofit.create(ApiInterface.class);
        //TODO: check based on current time
//        Call<List<ArrivalsResponse>> call = requestInterface.getVehicleArrivalsAM();
//        call.enqueue(new Callback<List<ArrivalsResponse>>() {
//            @Override
//            public void onResponse(Call<List<ArrivalsResponse>> call, Response<List<ArrivalsResponse>> response) {
//                int statusCode = response.code();
//                Log.d(TAG, "Reponse from MVGO " + statusCode);
//
//                dataArrayList = response.body();
//
//                if (dataArrayList.size() == 0) {
//                    tvNextArrival.setText("There are no arrival predictions at this time.");
//                } else {
//                    tvNextArrival.setText("Next Arrival MVGo shuttle to SamsungCampus:\n" +
//                            dataArrayList.get(0).getArrivalsList().get(0).getArrivalTime());
//                }
//            }
        Call<List<Routes>> call = requestInterface.getRoutes();
        call.enqueue(new Callback<List<Routes>>() {
            @Override
            public void onResponse(Call<List<Routes>> call, Response<List<Routes>> response) {
                int status = response.code();

                mStopsList= response.body();
                if (mStopsList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (Routes r : mStopsList) {
                        sb.append(r.getDisplayName());
                        sb.append("\n");
                    }
                    tvNextArrival.setText(sb.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Routes>> call, Throwable t) {

            }
        });

    }

}
