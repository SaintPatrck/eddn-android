package com.phapps.elitedangerous.eddn.sample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.phapps.elitedangerous.eddn.EdDnStreamListener
import com.phapps.elitedangerous.eddn.EdDnZmqService

class MainActivity : AppCompatActivity() {

    private lateinit var mConsole: EditText

    private var mBinder: EdDnZmqService.EdDnStreamBinder? = null
    private var mBound: Boolean = false

    private var mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            mBinder = null
            mBound = false
        }

        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            mBinder = binder as EdDnZmqService.EdDnStreamBinder
            if (mBinder != null) {
                mBinder!!.connect()
                mBinder!!.subscribe(mEddnListener)
                mBound = true
            }
        }
    }

    private var mEddnListener: EdDnStreamListener = object : EdDnStreamListener {
        override fun onMessageReceived(message: String?) {
            mConsole.append(message + "\n\n")
        }

        override fun onError(message: String?) {
            mConsole.append(message + "\n\n")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mConsole = findViewById(R.id.console)
    }

    override fun onResume() {
        super.onResume()
        bindService(
                Intent(this@MainActivity, EdDnZmqService::class.java),
                mServiceConnection,
                Context.BIND_AUTO_CREATE
        )
    }

    override fun onPause() {
        super.onPause()
        if (mBinder != null) {
            mBinder!!.unsubscribe(mEddnListener)
            mBinder!!.disconnect()
        }

        unbindService(mServiceConnection)
    }
}
