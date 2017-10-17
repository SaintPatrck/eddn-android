package com.phapps.elitedangerous.eddn;

public interface EdDnStreamListener {
    void onMessageReceived(String message);

    void onError(String message);
}
