package com.samsung.mvgo.rest;

import java.util.List;

import com.samsung.mvgo.model.Arrivals;
import com.samsung.mvgo.model.ArrivalsResponse;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("Stop/3951128/Arrivals?customerID=156")
    Call<List<ArrivalsResponse>> getVehicleArrivals();
}
