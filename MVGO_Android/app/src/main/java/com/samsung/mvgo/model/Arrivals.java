package com.samsung.mvgo.model;

import com.google.gson.annotations.SerializedName;

public class Arrivals {

    /*
        ArriveTime: "5:21 PM"
        BusName: "1552"
        Direction: 0
        IsLastStop: false
        IsLayover: false
        Minutes: 0
        OnBreak: false
        RouteID: 10447
        RouteId: 10447
        RouteName: "East Whisman - PM"
        Rules: []
        SchedulePrediction: false
        ScheduledArriveTime: null
        ScheduledMinutes: 0
        ScheduledTime: null
        SecondsToArrival: -21.8829194
        StopID: 3951128
        StopId: 3951128
        Time: "0"
        TripId: null
        VehicleID: 4227
        VehicleId: 4227
        VehicleName: "1552"
     */

    @SerializedName("BusName")
    private String busName;

    @SerializedName("RouteID")
    private Integer routeId;

    @SerializedName("StopID")
    private Integer stopId;

    @SerializedName("VehicleID")
    private Integer vehicleId;

    @SerializedName("VehicleName")
    private String vehicleName;

    @SerializedName("ArriveTime")
    private String arrivalTime;

    public Arrivals(String busName, Integer routeId, Integer stopId, Integer vehicleId, String vehicleName, String arrivalTime) {
        this.busName = busName;
        this.routeId = routeId;
        this.stopId = stopId;
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.arrivalTime = arrivalTime;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getStopId() {
        return stopId;
    }

    public void setStopId(Integer stopId) {
        this.stopId = stopId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
