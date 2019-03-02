package com.samsung.mvgo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.samsung.mvgo.R;
import com.samsung.mvgo.model.ArrivalsResponse;
import com.samsung.mvgo.model.Routes;
import com.samsung.mvgo.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectionActivity extends AppCompatActivity {

    private static final String TAG = "MVGo";
    private List<ArrivalsResponse> dataArrayList;
    private List<Routes> mStopsList;
    TextView tvNextArrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes_list);

        ListView routesView = findViewById(R.id.routes_list_view);

        HashMap<String, List<String>> routesMap = new HashMap<>();

        List<String> eastBayshoreMorning = new ArrayList<>(); //TODO update list
        eastBayshoreMorning.add("Mountain View Transit Center");
        eastBayshoreMorning.add("Shoreline Blvd - Terra Bella Ave");
        eastBayshoreMorning.add("Shoreline - Pear");
        eastBayshoreMorning.add("Shoreline - Charleston");
        eastBayshoreMorning.add("Stierlin Ct - Google");
        eastBayshoreMorning.add("Crittenden Lane - Google");
        eastBayshoreMorning.add("Shoreline - Charleston");
        eastBayshoreMorning.add("Pear - Inigo");
        eastBayshoreMorning.add("1065 La Avenida - Microsoft Bldg 1");
        eastBayshoreMorning.add("1255 Pear Ave - Google");
        eastBayshoreMorning.add("1288 Pear - Microsoft Bldg 6");
        eastBayshoreMorning.add("Shoreline - Pear");
        eastBayshoreMorning.add("Shoreline/Terra Bella");

        List<String> eastBayshoreEvening = new ArrayList<>();
        eastBayshoreEvening.add("Mountain View Transit Center");
        eastBayshoreEvening.add("Shoreline Blvd - Terra Bella Ave");
        eastBayshoreEvening.add("Shoreline - Pear");
        eastBayshoreEvening.add("Shoreline - Charleston");
        eastBayshoreEvening.add("Stierlin Ct - Google");
        eastBayshoreEvening.add("Crittenden Lane - Google");
        eastBayshoreEvening.add("Shoreline - Charleston");
        eastBayshoreEvening.add("Pear - Inigo");
        eastBayshoreEvening.add("1065 La Avenida - Microsoft Bldg 1");
        eastBayshoreEvening.add("1255 Pear Ave - Google");
        eastBayshoreEvening.add("1288 Pear - Microsoft Bldg 6");
        eastBayshoreEvening.add("Shoreline - Pear");
        eastBayshoreEvening.add("Shoreline/Terra Bella");

        List<String> westBayshoreMorning = new ArrayList<>(); //TODO - update list
        westBayshoreMorning.add("Mountain View Transit Center");
        westBayshoreMorning.add("Shoreline Blvd - Terra Bella Ave");
        westBayshoreMorning.add("Shoreline - Pear");
        westBayshoreMorning.add("Shoreline - Charleston");
        westBayshoreMorning.add("Stierlin Ct - Google");
        westBayshoreMorning.add("Crittenden Lane - Google");
        westBayshoreMorning.add("Shoreline - Charleston");
        westBayshoreMorning.add("Pear - Inigo");
        westBayshoreMorning.add("1065 La Avenida - Microsoft Bldg 1");
        westBayshoreMorning.add("1255 Pear Ave - Google");
        westBayshoreMorning.add("1288 Pear - Microsoft Bldg 6");
        westBayshoreMorning.add("Shoreline - Pear");
        westBayshoreMorning.add("Shoreline/Terra Bella");

        routesMap.put("East Bayshore - Morning", eastBayshoreMorning);
        routesMap.put("East Bayshore - Evening", eastBayshoreEvening);
        routesMap.put("West Bayshore - Morning", westBayshoreMorning);
        routesMap.put("West Bayshore - Evening", null);
        routesMap.put("East Wishman - Morning", null);
        routesMap.put("East Wishman - Evening", null);

        final ArrayList<String> list = new ArrayList<>(routesMap.keySet());

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        routesView.setAdapter(adapter);

        routesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
            }

        });

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private void showRoutes() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.ridemvgo.org/").
                addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface requestInterface = retrofit.create(ApiInterface.class);

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

    private void showArrivalTimes() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.ridemvgo.org/").
                addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface requestInterface = retrofit.create(ApiInterface.class);

        Call<List<ArrivalsResponse>> call = requestInterface.getVehicleArrivalsAM();
        call.enqueue(new Callback<List<ArrivalsResponse>>() {
            @Override
            public void onResponse(Call<List<ArrivalsResponse>> call, Response<List<ArrivalsResponse>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Reponse from MVGO " + statusCode);

                dataArrayList = response.body();

                if (dataArrayList.size() == 0) {
                    tvNextArrival.setText("There are no arrival predictions at this time.");
                } else {
                    tvNextArrival.setText("Next Arrival MVGo shuttle to SamsungCampus:\n" +
                            dataArrayList.get(0).getArrivalsList().get(0).getArrivalTime());
                }
            }

            @Override
            public void onFailure(Call<List<ArrivalsResponse>> call, Throwable t) {

            }
        });
    }
}
