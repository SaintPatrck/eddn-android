package com.phapps.elitedangerous.eddn;

public interface IEdDnStream {
    void connect();

    void subscribe(EdDnStreamListener listener);

    void unsubscribe(EdDnStreamListener listener);

    void disconnect();
}
