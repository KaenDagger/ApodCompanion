package io.kaendagger.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.kaendagger.apodcompanion.data.APODRepository
import io.kaendagger.apodcompanion.data.Result
import io.kaendagger.apodcompanion.data.model.Apod
import io.kaendagger.apodcompanion.data.model.ApodOffline
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

class MainActivityViewModel @Inject
constructor(private val context: Context,
            private val picasso: Picasso,
            private val apodRepository: APODRepository) : ViewModel() {

    suspend fun getTodayApod(): Deferred<Result<Apod>> {
        return viewModelScope.async {
            val response = apodRepository.getTodayAPOD()
            if (response.isSuccessful){
                val apod = response.body()
                if (apod != null){
                    downloadImage(apod)
                    Result.Success(apod)
                }else{
                    Result.Error(NullPointerException("Received Null"))
                }
            }else{
                Result.Error(IOException("Error fetching data"))
            }
        }
    }

    suspend fun getPastAPODs(): Deferred<List<ApodOffline>> = viewModelScope.async {
        apodRepository.getPastAPODs()
    }

    private suspend fun downloadImage(apod: Apod) {

        val directory = context.applicationInfo.dataDir
        Log.i("PUI", "download image $directory")
        picasso.load(apod.url).into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.i("PUI", "bitmap failed ${e?.message}")
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                // file download to disk
                viewModelScope.launch(Dispatchers.IO) {
                    val file = File("$directory/${apod.date}.jpeg")
                    Log.i("PUI", "directory $directory/${apod.date}")
                    file.createNewFile()
                    val oStream = FileOutputStream(file)
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, oStream)
                    oStream.apply {
                        flush()
                        close()
                    }

                    val apodOffline = ApodOffline(
                        apod.date,
                        apod.explanation,
                        apod.title,
                        "$directory/${apod.date}.jpeg"
                    )
                    apodRepository.insertApodOffline(apodOffline)
                }
            }
        })
    }
}