package com.phapps.elitedangerous.eddn.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phapps.elitedangerous.eddn.enums.Event;

import java.util.Date;
import java.util.List;

/**
 * Contains all properties from the listed events in the client's journal minus Localised strings
 * and the properties marked below as 'disallowed'
 */
public class Message {

    /**
     * (Required)
     */
    @SerializedName("timestamp")
    @Expose
    private Date mTimestamp;
    /**
     * (Required)
     */
    @SerializedName("event")
    @Expose
    private Event mEvent;
    /**
     * Must be added by the sender if not present in the journal event
     * (Required)
     */
    @SerializedName("StarSystem")
    @Expose
    private String mStarSystem;
    /**
     * Must be added by the sender if not present in the journal event
     * (Required)
     */
    @SerializedName("StarPos")
    @Expose
    private List<Double> mStarPosition = null;
    @SerializedName("CockpitBreach")
    @Expose
    private Object mCockpitBreach;
    @SerializedName("BoostUsed")
    @Expose
    private Object mBoostUsed;
    @SerializedName("FuelLevel")
    @Expose
    private Object mFuelLevel;
    @SerializedName("FuelUsed")
    @Expose
    private Object mFuelUsed;
    @SerializedName("JumpDist")
    @Expose
    private Object mJumpDistance;
    @SerializedName("Latitude")
    @Expose
    private Double mLatitude;
    @SerializedName("Longitude")
    @Expose
    private Double mLongitude;

    /**
     * (Required)
     */
    public Date getTimestamp() {
        return mTimestamp;
    }

    /**
     * (Required)
     */
    public void setTimestamp(Date timestamp) {
        this.mTimestamp = timestamp;
    }

    /**
     * (Required)
     */
    public Event getEvent() {
        return mEvent;
    }

    /**
     * (Required)
     */
    public void setEvent(Event event) {
        this.mEvent = event;
    }

    /**
     * Must be added by the sender if not present in the journal event
     * (Required)
     */
    public String getStarSystem() {
        return mStarSystem;
    }

    /**
     * Must be added by the sender if not present in the journal event
     * (Required)
     */
    public void setStarSystem(String starSystem) {
        this.mStarSystem = starSystem;
    }

    /**
     * Must be added by the sender if not present in the journal event
     * (Required)
     */
    public List<Double> getStarPosition() {
        return mStarPosition;
    }

    /**
     * Must be added by the sender if not present in the journal event
     * (Required)
     */
    public void setStarPosition(List<Double> starPosition) {
        this.mStarPosition = starPosition;
    }

    public Object getCockpitBreach() {
        return mCockpitBreach;
    }

    public void setCockpitBreach(Object cockpitBreach) {
        this.mCockpitBreach = cockpitBreach;
    }

    public Object getBoostUsed() {
        return mBoostUsed;
    }

    public void setBoostUsed(Object boostUsed) {
        this.mBoostUsed = boostUsed;
    }

    public Object getFuelLevel() {
        return mFuelLevel;
    }

    public void setFuelLevel(Object fuelLevel) {
        this.mFuelLevel = fuelLevel;
    }

    public Object getFuelUsed() {
        return mFuelUsed;
    }

    public void setFuelUsed(Object fuelUsed) {
        this.mFuelUsed = fuelUsed;
    }

    public Object getJumpDistance() {
        return mJumpDistance;
    }

    public void setJumpDistance(Object jumpDistance) {
        this.mJumpDistance = jumpDistance;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        this.mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        this.mLongitude = longitude;
    }
}
