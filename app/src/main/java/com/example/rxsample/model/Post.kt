package com.example.rxsample.model

import com.google.gson.annotations.SerializedName

data class Post(@SerializedName("userId") val userId: Int, val id: Int, val title: String,
                val body: String, var comments: List<Comment>)
