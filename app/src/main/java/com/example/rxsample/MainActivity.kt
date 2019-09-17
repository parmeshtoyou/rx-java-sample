package com.example.rxsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val itemList = listOf(100, 200, 300, 400, 500)
    private var compositeDisposable = CompositeDisposable()

    private val TAG = MainActivity::class.java.simpleName

    companion object {
        lateinit var buttonStart: Button
        lateinit var handler: Handler
        @Volatile
        var stopThread = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = Handler()

        /*compositeDisposable.add(Observable.fromArray(itemList).subscribeOn(Schedulers.io()).subscribe {
            println("Item#$it")
        })*/

        buttonStart = start

        start.setOnClickListener {
            startThread()
        }

        stop.setOnClickListener {
            stopThread()
        }
    }

    private fun startThread() {
        stopThread = false
        //BackgroundThread(10).start()
        Thread(ExampleRunnable(10)).start()
    }

    private fun stopThread() {
        stopThread = true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    class BackgroundThread(private val seconds: Int) : Thread() {

        override fun run() {
            for (i in 0..seconds) {
                Log.d("BACKGROUND-THREAD", "Thread Started:$i")
                SystemClock.sleep(1000)
            }
        }
    }

    class ExampleRunnable(private val seconds: Int) : Runnable {

        override fun run() {
            val threadHandler = Handler(Looper.getMainLooper())
            //This is providing handler of Main Thread.
            //Looper is the PRO here, which is dealing with main thread and handler.


            for (i in 0..seconds) {
                if (stopThread) return
                if (i == 5) {
                    //buttonStart.text = "50%"
                    //above line will crash with CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.

                    /*handler.post {
                        buttonStart.text = "50%"
                    }*/

                    /*buttonStart.post {
                        buttonStart.text = "50%"
                    }*/

                    threadHandler.post {
                        MainActivity.buttonStart.text = "50%"
                    }
                }
                threadHandler.post {
                    buttonStart.text = "i:$i"
                }
                Log.d("BACKGROUND-THREAD", "Runnable Started:$i")
                SystemClock.sleep(1000)
            }
        }

    }
}