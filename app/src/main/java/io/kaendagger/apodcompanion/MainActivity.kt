package io.kaendagger.apodcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.kaendagger.apodcompanion.data.model.Apod
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import io.kaendagger.apodcompanion.data.Result
import java.io.IOException
import java.lang.Error
import java.lang.Exception
import java.lang.NullPointerException

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val apodComponent = DaggerAPODComponent.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch {
            val apodResult = getTodaysApodAsync().await()
            when(apodResult){
                is Result.Success -> setTodaysImage(apodResult.data)
                is Result.Error -> Log.e("PUI","Error fetching")
            }
        }
    }

    private fun getTodaysApodAsync(): Deferred<Result<Apod>> {
        val service = apodComponent.getAPODService()
        return async(Dispatchers.IO) {
            val response = service.getAPOD()
            return@async when {
                response.isSuccessful -> {
                    val apod = response.body()
                    when {
                        apod != null -> Result.Success(apod)
                        else -> Result.Error(NullPointerException("APOD is Null"))
                    }
                }
                else -> Result.Error(IOException("Error fetching Image"))
            }
        }
    }

    private fun setTodaysImage(apod: Apod) {
        apodComponent.getPicasso().load(apod.url).into(ivCurrentApod)
    }
}
