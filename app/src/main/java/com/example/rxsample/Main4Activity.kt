package com.example.rxsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxsample.model.Post
import com.example.rxsample.request.ServiceGenerator
import com.jakewharton.rxbinding.view.RxView
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main4.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class Main4Activity : AppCompatActivity() {

    companion object {
        private val TAG = Main4Activity::class.java.simpleName
    }

    private var timeSinceLastRequest : Long = 0L

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        /*compositeDisposable.add(
            getPostObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ post ->
            }, { onError ->
                println("On Error:${onError.localizedMessage}")
            }, {},
                {
                    compositeDisposable.add(it)
                })
        )*/

        //val task = Task("This is the description", false, 10)
        //val listOfTask = com.example.rxsample.repository.DataSource.createTaskList()


        /*val observable = Observable.create<Task> {emitter ->
            if (!emitter.isDisposed) {
                emitter.onNext(task)
                emitter.onComplete()
            }
        }*/

        /*val observable = Observable.create<Task> { emitter ->

            for (task in listOfTask) {
                if (!emitter.isDisposed) {
                    emitter.onNext(task)
                }
            }

            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        }

        observable.subscribe {
            Log.d(TAG, "Task:${it.description}")
        }*/

        /*val observable = Observable.just(com.example.rxsample.repository.DataSource.createTaskList())

        observable.subscribe { list ->
            list.forEach {
                Log.d(TAG, "Task:${it.description}")
            }
        }*/

        /*val observable = Observable
            .range(0, 9)
            .repeat(2)
            .subscribeOn(Schedulers.io())
            .map { integer ->
                Log.d(TAG, "ThreadName:${Thread.currentThread().name}")
                Task("Description:${integer*2}", false, integer)
            }
            .observeOn(AndroidSchedulers.mainThread())

        observable.subscribe {
            Log.d(TAG, "Task:$it")
            Log.d(TAG, "ThreadName:${Thread.currentThread().name}")
        }*/

        /*val observable = Observable.fromIterable(DataSource.createTaskList())
            .subscribeOn(Schedulers.io())
            .buffer(2)
            .observeOn(AndroidSchedulers.mainThread())

        observable.subscribe { taskList ->
            Log.d(TAG, "=====================")
            for (task in taskList) {
                Log.d(TAG, "Task:${task.description}")
            }
        }*/

        val clickObservable = RxView.clicks(button)
            .map { 1 }
            .buffer(1, TimeUnit.SECONDS)


        /*val subscription = clickObservable.subscribe { intList ->
            //Log.d(TAG, "List.size:${intList.size}")
        }*/

        timeSinceLastRequest = System.currentTimeMillis()

        /*val observableTextQuery = Observable.create<String> { emitter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!emitter.isDisposed) {
                        newText?.let {
                            emitter.onNext(it)
                        }
                    }
                    return false
                }
            })
        }.debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        observableTextQuery.subscribe {
            val currentTime = System.currentTimeMillis()
            Log.d(TAG, "Last time requested time:${currentTime - timeSinceLastRequest}")
            Log.d(TAG, "Entered Text:$it")

            timeSinceLastRequest = currentTime
        }*/

        /*RxView.clicks(button)
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                val timeTaken = System.currentTimeMillis() - timeSinceLastRequest

                Log.d(TAG, "Clicked:$timeTaken")
                timeSinceLastRequest = System.currentTimeMillis()
            }


        val singleObservable = Single.create<String> { singleEmitter ->
            if (!singleEmitter.isDisposed) {
                singleEmitter.onSuccess("Hello World")
            }
        }

        val singleObserver = object : SingleObserver<String>{

            override fun onSuccess(t: String) {
                Log.d(TAG, "on Success: $t")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe")
                compositeDisposable.add(d)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError")
            }
        }

        singleObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(singleObserver)*/
        
        /*val longObservable = Observable.rangeLong(0, 10000000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            longObservable.subscribe {
            Log.d(TAG, "Long:$it")
        }*/

        val longFlowable = Flowable.rangeLong(0, 10_000_000)
            .onBackpressureBuffer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        longFlowable.subscribe {
            //Log.d(TAG, "Long:$it")
            counter.text = "Counter:$it"
        }


    }

    private fun getPostObservable(): Observable<Post> {

        return ServiceGenerator.getRequestApi()
            .getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { listOfPost ->
                println("List Size:$listOfPost")
                Observable.fromIterable(listOfPost).subscribeOn(Schedulers.io())
            }
    }

    private fun getCommentObservable(post: Post): Observable<Post> {
        return ServiceGenerator.getRequestApi()
            .getComments(post.id)
            .map { comments ->
                val delay = (Random(5).nextInt(5) + 1) * 1000 // sleep thread for x ms
                Thread.sleep(delay.toLong())
                Log.d(
                    TAG,
                    "apply: sleeping thread " + Thread.currentThread().name + " for " + delay.toString() + "ms"
                )
                post.comments = comments
                post
            }
            .subscribeOn(Schedulers.io())
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
