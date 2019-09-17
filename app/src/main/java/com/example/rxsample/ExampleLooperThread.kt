package com.example.rxsample

import android.os.Handler
import android.os.Looper
import android.util.Log

class ExampleLooperThread: Thread() {

    lateinit var handler: Handler
    var looper: Looper? = null

    override fun run() {

        Looper.prepare()

        looper = Looper.myLooper()

        handler = ExampleHandler(Looper.myLooper()!!)

        Looper.loop()

        Log.d(TAG, "END OF EXAMPLE LOOPER THREAD")
    }

    companion object {
        private val TAG = ExampleLooperThread::class.java.simpleName
    }
}