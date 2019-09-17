package com.example.rxsample

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class ExampleHandler(looper: Looper) : Handler(looper) {

    companion object {
        private val TAG = ExampleHandler::class.java.simpleName
        const val TASK_A = 1
        const val TASK_B = 2
    }

    override fun handleMessage(msg: Message?) {
        when (msg?.what) {
            TASK_A -> {
                Log.d(
                    TAG,
                    "TASK 1 EXECUTED msg.what:${msg.what} msg.arg1:${msg.arg1} msg.arg2:${msg.arg2}"
                )
            }
            TASK_B -> {
                Log.d(
                    TAG,
                    "TASK 2 EXECUTED msg.what:${msg.what} msg.arg1:${msg.arg1} msg.arg2:${msg.arg2}"
                )
            }
        }
    }
}