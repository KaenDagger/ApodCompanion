package io.kaendagger.apodcompanion.data.remote

import io.kaendagger.apodcompanion.data.model.Apod
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APODApi{

    @GET
    suspend fun getAPOD(@Query("date") date:String):Response<Apod>
}