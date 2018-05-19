package com.phapps.elitedangerous.eddn;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class StreamListenerServiceInstrumentedTest {
    private static final String TAG = StreamListenerServiceInstrumentedTest.class.getName();

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Test
    public void testStream() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        Intent serviceIntent =
                new Intent(InstrumentationRegistry.getTargetContext(), EdDnZmqService.class);

        EdDnZmqService.EdDnStreamBinder binder =
                (EdDnZmqService.EdDnStreamBinder)mServiceRule.bindService(serviceIntent);

        binder.connect();
        binder.subscribe(new EdDnStreamListener() {
            @Override
            public void onMessageReceived(String message) {
                Log.d(TAG, "Stream message: " + message);
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "Stream error: " + message);
            }
        });

        // Stream for 10 seconds
        signal.await(10, TimeUnit.SECONDS);
    }
}
