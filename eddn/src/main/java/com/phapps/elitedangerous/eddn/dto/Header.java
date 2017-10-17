package com.phapps.elitedangerous.eddn.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Header {

    /**
     * (Required)
     */
    @SerializedName("uploaderID")
    @Expose
    private String mUploaderId;
    /**
     * (Required)
     */
    @SerializedName("softwareName")
    @Expose
    private String mSoftwareName;
    /**
     * (Required)
     */
    @SerializedName("softwareVersion")
    @Expose
    private String mSoftwareVersion;
    /**
     * Timestamp upon receipt at the gateway. If present, this property will be overwritten by the
     * gateway; submitters are not intended to populate this property.
     */
    @SerializedName("gatewayTimestamp")
    @Expose
    private Date mGatewayTimestamp;

    /**
     * (Required)
     */
    public String getUploaderId() {
        return mUploaderId;
    }

    /**
     * (Required)
     */
    public void setUploaderId(String uploaderId) {
        this.mUploaderId = uploaderId;
    }

    /**
     * (Required)
     */
    public String getSoftwareName() {
        return mSoftwareName;
    }

    /**
     * (Required)
     */
    public void setSoftwareName(String softwareName) {
        this.mSoftwareName = softwareName;
    }

    /**
     * (Required)
     */
    public String getSoftwareVersion() {
        return mSoftwareVersion;
    }

    /**
     * (Required)
     */
    public void setSoftwareVersion(String softwareVersion) {
        this.mSoftwareVersion = softwareVersion;
    }

    /**
     * Timestamp upon receipt at the gateway. If present, this property will be overwritten by the
     * gateway; submitters are not intended to populate this property.
     */
    public Date getGatewayTimestamp() {
        return mGatewayTimestamp;
    }

    /**
     * Timestamp upon receipt at the gateway. If present, this property will be overwritten by the
     * gateway; submitters are not intended to populate this property.
     */
    public void setGatewayTimestamp(Date gatewayTimestamp) {
        this.mGatewayTimestamp = gatewayTimestamp;
    }
}
