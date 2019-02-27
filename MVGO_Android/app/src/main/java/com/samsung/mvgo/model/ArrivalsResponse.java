package com.samsung.mvgo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ArrivalsResponse {

    @SerializedName("Arrivals")
    private List<Arrivals> arrivalsList = new ArrayList<>();

    public List<Arrivals> getArrivalsList() {
        return arrivalsList;
    }

    public void setArrivalsList(List<Arrivals> arrivalsList) {
        this.arrivalsList = arrivalsList;
    }
}
