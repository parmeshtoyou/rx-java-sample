package com.example.rxsample

import android.os.HandlerThread
import android.os.Looper
import android.os.Process
import android.util.Log


class ExampleThreadHandler(name: String? = "ExampleThreadHandler", priority: Int = Process.THREAD_PRIORITY_BACKGROUND)
    : HandlerThread(name, priority) {

    private val TAG = ExampleThreadHandler::class.java.simpleName

    private lateinit var handler: ExampleHandler


    override fun onLooperPrepared() {
        Log.d(TAG, "onLooperPrepared")
        handler = ExampleHandler(Looper.myLooper()!!)
    }

    override fun run() {
        Log.d(TAG, "Example Thread Handler Started")
        super.run()

        Log.d(TAG, "Example Exit")
    }

    fun getHandler() = handler

}