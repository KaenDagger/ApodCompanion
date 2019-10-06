package io.kaendagger.apodcompanion.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.kaendagger.apodcompanion.data.database.APODDao
import io.kaendagger.apodcompanion.data.model.Apod
import io.kaendagger.apodcompanion.data.model.ApodOffline
import io.kaendagger.apodcompanion.data.remote.APODApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

class APODRepository @Inject constructor(
    val apodService: APODApi,
    val apodDao: APODDao,
    val picasso: Picasso,
    val context: Context
) {

    suspend fun getTodayAPOD(): Response<Apod> {
        Log.i("PUI","repo today apod")
        return apodService.getAPOD()
    }

    suspend fun getPastAPODs(): List<ApodOffline> {
        return apodDao.getPastAPODs()
    }

    suspend fun insertApodOffline(apodOffline: ApodOffline){
        apodDao.insertAPODOffline(apodOffline)
    }
}