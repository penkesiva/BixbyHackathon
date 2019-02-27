package com.samsung.mvgo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Vehicle {

    /* Sample JSon output of the MVGO vehicles

        https://www.ridemvgo.org/Route/3891/Vehicles => returns list of dictionaries

        0:
                APCPercentage: 0
                Coordinate: {Latitude: 37.396995324662676, Longitude: -122.04965770325296}
                Latitude: 37.396995324662676
                Longitude: -122.04965770325296
                DoorStatus: 0
                HasAPC: false
                Heading: "SE"
                ID: 4226
                IconPrefix: ""
                Latitude: 37.396995324662676
                Longitude: -122.04965770325296
                Name: "1548"
                PatternId: 10447
                RouteId: 3891
                Speed: 26
                Updated: "5:02:28P"
                UpdatedAgo: "4s ago"

    */

    @SerializedName("ID")
    private Integer Id;

    @SerializedName("Name")
    private String Name;

    @SerializedName("RouteId")
    private Integer RouteId;

    @SerializedName("Latitude")
    private Double Latitude;

    @SerializedName("Longitude")
    private Double Longitude;

    public Vehicle(int id, String name, int routeId, Double latitude, Double longitude) {
        this.Id = id;
        this.Name = name;
        this.RouteId = routeId;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getRouteId() {
        return RouteId;
    }

    public void setRouteId(Integer routeId) {
        RouteId = routeId;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

}
