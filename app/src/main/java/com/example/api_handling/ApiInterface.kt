package com.example.api_handling

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @GET("posts/{id}")
    fun get_data(@Path("id") post_id:Int):Call<User>

    @POST("posts")
    fun post_data(@Body user:User):Call<User>

}