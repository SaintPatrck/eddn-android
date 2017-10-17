package com.phapps.elitedangerous.eddn;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class EdDnZmqService extends Service {
    private static final String TAG = EdDnZmqService.class.getName();

    private EdDnStreamBinder mEdDnStreamBinder;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mEdDnStreamBinder = new EdDnStreamBinder();
        mHandler = new Handler(getMainLooper());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mEdDnStreamBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mEdDnStreamBinder != null) {
            mEdDnStreamBinder.disconnect();
            mEdDnStreamBinder = null;
        }
    }

    public class EdDnStreamBinder extends Binder implements IEdDnStream {

        private EdDnStreamerThread mStreamerThread;

        @Override
        public void connect() {
            if (mStreamerThread == null) {
                mStreamerThread = new EdDnStreamerThread();
                new Thread(mStreamerThread).start();
            }
        }

        @Override
        public void subscribe(EdDnStreamListener listener) {
            if (mStreamerThread != null) {
                mStreamerThread.subscribe(listener);
            }
        }

        @Override
        public void unsubscribe(EdDnStreamListener listener) {
            if (mStreamerThread != null) {
                mStreamerThread.unsubscribe(listener);
            }
        }

        public void disconnect() {
            if (mStreamerThread != null) {
                mStreamerThread.disconnect();
                mStreamerThread.interrupt();
                mStreamerThread = null;
            }
        }
    }

    private class EdDnStreamerThread extends Thread {
        private static final String SCHEMA_KEY = "$schemaRef";
        private static final String RELAY = "tcp://eddn.edcd.io:9500";

        private List<EdDnStreamListener> mListeners = new ArrayList<>();
        private ZContext mZContext;
        private ZMQ.Socket mSocket;

        void subscribe(@NonNull EdDnStreamListener listener) {
            mListeners.add(listener);
        }

        void unsubscribe(@NonNull EdDnStreamListener listener) {
            mListeners.remove(listener);
        }

        @Override
        public void run() {

            if (connect()) {
                poll();
                disconnect();
            }
        }

        private boolean connect() {
            mZContext = new ZContext();
            mSocket = mZContext.createSocket(ZMQ.SUB);
            mSocket.subscribe("".getBytes());
            mSocket.setReceiveTimeOut(30 * 1000);

            boolean result = mSocket.connect(RELAY);
            if (result) {
                Log.d(TAG, RELAY + " relay connected");
            } else {
                broadcastError("Failed to connect to relay");
            }

            return result;
        }

        private void poll() {
            ZMQ.Poller poller = mZContext.createPoller(2);
            poller.register(mSocket, ZMQ.Poller.POLLIN);
            byte[] output = new byte[256 * 1024];
            while (!isInterrupted()) {
                int poll = poller.poll(10);
                if (poll == ZMQ.Poller.POLLIN) {

                    if (poller.pollin(0)) {
                        byte[] received = mSocket.recv(ZMQ.NOBLOCK);
                        if (received.length > 0) {
                            Inflater inflater = new Inflater();
                            inflater.setInput(received);
                            try {

                                int outLen = inflater.inflate(output);
                                String outputString = new String(output, 0, outLen, "UTF-8");

                                if (outputString.contains(SCHEMA_KEY)) {
                                    Log.d(TAG, "output: " + outputString);
                                    if (!isInterrupted()) {
                                        broadcastMessage(outputString);
                                    }
                                }
                            } catch (DataFormatException e) {
                                Log.e(TAG, "connect: Failed to decompress data", e);
                            } catch (UnsupportedEncodingException e) {
                                Log.e(TAG, "connect: Failed to decode data", e);
                            }
                        }
                    }
                }
            }
        }

        private void disconnect() {
            if (mSocket != null) {
                mSocket.disconnect(RELAY);
                mSocket = null;
            }
            Log.i(TAG, "poll: " + RELAY + " relay disconnected");
        }

        private void broadcastMessage(final String outputString) {
            // TODO: deserialize messages before broadcasting.
            for (final EdDnStreamListener subscriber : mListeners) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (subscriber != null) {
                            subscriber.onMessageReceived(outputString);
                        }
                    }
                });
            }
        }

        private void broadcastError(final String message) {
            for (final EdDnStreamListener subscriber : mListeners) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (subscriber != null) {
                            subscriber.onError(message);
                        }
                    }
                });
            }
        }
    }
}
