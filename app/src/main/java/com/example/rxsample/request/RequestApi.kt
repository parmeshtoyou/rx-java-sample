package com.example.rxsample.request

import com.example.rxsample.model.Comment
import com.example.rxsample.model.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApi {

    @GET("posts")
    fun getPosts(): Observable<List<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: Int): Observable<List<Comment>>
}