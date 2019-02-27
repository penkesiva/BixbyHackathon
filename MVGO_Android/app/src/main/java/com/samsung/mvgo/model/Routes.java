package com.samsung.mvgo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Routes {

    private class Patterns {
        @SerializedName("ID")
        private int mRouteID;
    }
    /*
        ArrivalsEnabled: true
        ArrivalsShowVehicleNames: true
        BackwardDirectionName: null
        Color: "#FFBB33"
        CustomerID: 156
        DirectionStops: null
        DisplayName: "East Bayshore - AM"
        ForwardDirectionName: null
        ID: 3652
        IsHeadway: false
        Name: "East Bayshore - AM"
        NumberOfVehicles: 0
        Patterns: [{ID: 10478, Name: "East Bayshore - AM", Direction: "Loop", Directionality: "Loop"}]
            0: {ID: 10478, Name: "East Bayshore - AM", Direction: "Loop", Directionality: "Loop"}
                Direction: "Loop"
                Directionality: "Loop"
                ID: 10478
                Name: "East Bayshore - AM"
        Points: null
        RegionIDs: []
        ShortName: "East Bayshore AM"
        ShowLine: true
        TextColor: "#FFFFFF"
     */
    @SerializedName("DisplayName")
    private String mDisplayName;

    @SerializedName("ID")
    private int mDirectionID;

    @SerializedName("Patterns")
    private List<Patterns> mPatterns;

    public Routes(String mDisplayName, int mDirectionID, List<Patterns> mPatterns) {
        this.mDisplayName = mDisplayName;
        this.mDirectionID = mDirectionID;
        this.mPatterns = mPatterns;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    public int getDirectionID() {
        return mDirectionID;
    }

    public void setDirectionID(int mDirectionID) {
        this.mDirectionID = mDirectionID;
    }

    public List<Patterns> getPatterns() {
        return mPatterns;
    }

    public void setPatterns(List<Patterns> mPatterns) {
        this.mPatterns = mPatterns;
    }
}
