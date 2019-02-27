package com.samsung.mvgo.rest;

import com.samsung.mvgo.model.ArrivalsResponse;
import com.samsung.mvgo.model.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("Region/0/Routes")
    Call<List<Routes>> getRoutes();
    @GET("Stop/3820407/Arrivals?customerID=156")
    Call<List<ArrivalsResponse>> getVehicleArrivalsAM();
    @GET("Stop/3951128/Arrivals?customerID=156")
    Call<List<ArrivalsResponse>> getVehicleArrivalsPM();
}