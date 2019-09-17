package com.example.rxsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.annotation.WorkerThread

class Main2Activity : AppCompatActivity() {

    private val exampleLooperThread = ExampleLooperThread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    fun startThread(view: View) {
        exampleLooperThread.start()
        //myWorkerThreadFunction()
    }
    fun stopThread(view: View) {
        exampleLooperThread.looper?.quitSafely()
    }
    fun taskA(view: View) {
        /*val threadHandler = Handler(exampleLooperThread.looper)
        threadHandler.post {
            for (i in 0..5) {
                Log.d("THREAD", "Task-A$i")
                SystemClock.sleep(1000)
            }
        }*/

        val msg = Message.obtain()
        msg.what = ExampleHandler.TASK_A
        exampleLooperThread.handler.sendMessage(msg)
    }
    fun taskB(view: View) {
        val msg = Message.obtain()
        msg.what = ExampleHandler.TASK_B
        exampleLooperThread.handler.sendMessage(msg)
    }
}
