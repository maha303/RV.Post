package com.example.rvpost

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {

    @GET("/custom-people/")
    fun getData():Call<List<NamesItem>>

    @POST("/custom-people/")
     fun addUser(@Body userData:NamesItem): Call<List<NamesItem>>
}