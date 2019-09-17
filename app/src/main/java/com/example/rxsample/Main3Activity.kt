package com.example.rxsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    private val exampleThreadHandler = ExampleThreadHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        startThread.setOnClickListener {
            exampleThreadHandler.start()
        }

        stopThread.setOnClickListener {
            exampleThreadHandler.quit()
        }

        startTaskA.setOnClickListener {
            val msg = Message.obtain()
            msg.what = ExampleHandler.TASK_A
            msg.arg1 = 100
            msg.arg2 = 200
            exampleThreadHandler.getHandler().sendMessage(msg)
        }

        startTaskB.setOnClickListener {
            val msg = Message.obtain()
            msg.what = ExampleHandler.TASK_B
            msg.arg1 = 200
            msg.arg2 = 300
            exampleThreadHandler.getHandler().sendMessage(msg)
        }

    }
}
