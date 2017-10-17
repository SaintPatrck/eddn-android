package com.phapps.elitedangerous.eddn.enums;

import com.google.gson.annotations.SerializedName;

public enum Event {
    @SerializedName("Docked")
    DOCKED,

    @SerializedName("FSDJump")
    FSD_JUMP,

    @SerializedName("Scan")
    SCAN,

    @SerializedName("Location")
    LOCATION
}
