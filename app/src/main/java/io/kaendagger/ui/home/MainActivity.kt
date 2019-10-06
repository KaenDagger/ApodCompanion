package io.kaendagger.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import io.kaendagger.apodcompanion.R
import io.kaendagger.apodcompanion.createViewModel
import io.kaendagger.apodcompanion.data.Result
import io.kaendagger.apodcompanion.data.model.Apod
import io.kaendagger.apodcompanion.data.model.ApodOffline
import io.kaendagger.apodcompanion.di.APODComponent
import io.kaendagger.apodcompanion.di.APODRoomModule
import io.kaendagger.apodcompanion.di.ContextModule
import io.kaendagger.apodcompanion.di.DaggerAPODComponent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    lateinit var apodComponent:APODComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apodComponent = DaggerAPODComponent.builder()
            .aPODRoomModule(APODRoomModule(this.application))
            .contextModule(ContextModule(this))
            .build()

        val mavm = createViewModel {  apodComponent.getMaViewModel()}
//        val apodComponent = DaggerAPODComponent

        launch {

            val pastApods = mavm.getPastAPODs().await()
            Log.i("PUI"," past apods ${pastApods[0].path}")
            if (pastApods.isNotEmpty()){
                setImages(pastApods)
            }else{
                val apodResult = mavm.getTodayApod().await()
                when(apodResult){
                    is Result.Success -> setTodaysImage(apodResult.data)
                    is Result.Error -> Log.e("PUI","Error fetching")
                }
            }
        }
    }

    private fun setImages(list: List<ApodOffline>){
        val imageAdapter = apodComponent.getImageAdapter().apply {
            setItems(list)
        }

        // last inserted image is current/todays image
        apodComponent.getPicasso().load(File(list[list.lastIndex].path)).into(ivCurrentApod)

        rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(this@MainActivity,3)
        }
    }
    private fun setTodaysImage(apod: Apod) {
        apodComponent.getPicasso().load(apod.url).into(ivCurrentApod)
    }
}
