package com.example.retrofit_radier.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): List<Comment>
}
