package com.samsung.mvgo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehiclesResponse {
    @SerializedName("vehicles")
    private List<Vehicle> vehicles;

    public List<Vehicle> getResults() {
        return vehicles;
    }
}
