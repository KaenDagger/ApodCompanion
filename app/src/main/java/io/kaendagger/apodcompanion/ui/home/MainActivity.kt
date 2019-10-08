package io.kaendagger.apodcompanion.ui.home

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import io.kaendagger.apodcompanion.*
import io.kaendagger.apodcompanion.data.Result
import io.kaendagger.apodcompanion.data.model.Apod
import io.kaendagger.apodcompanion.di.*
import io.kaendagger.apodcompanion.ui.APODViewModel
import io.kaendagger.apodcompanion.ui.viewer.ViewerActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.File
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    lateinit var mainActivityComponent: MainActivityComponent

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    @Inject
    lateinit var picasso: Picasso

    private lateinit var mavm: APODViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityComponent = DaggerMainActivityComponent.builder()
            .aPODRoomModule(APODRoomModule(this.application))
            .contextModule(ContextModule(this))
            .mainActivityModule(MainActivityModule(this))
            .build()
            .apply { injectMainActivity(this@MainActivity) }

        mavm = createViewModel { mainActivityComponent.getViewModel() }

        launch {
            val pastApods = mavm.getPastAPODs().await()
            if (!isItToday() || pastApods.isEmpty()) {
                loadTodayAPOD()
            } else {
                progress.isVisible = false
                if (pastApods.isNotEmpty())
                    picasso.load(File(pastApods[0].path)).into(ivCurrentApod)
            }
            ivCurrentApod.setOnClickListener {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity,
                    ivCurrentApod,
                    "imageTransit"
                )
                startActivity(
                    Intent(this@MainActivity, ViewerActivity::class.java)
                        .apply { putExtra("image_no", 0) },
                    options.toBundle()
                )
            }
            loadPastAPODs()
        }
    }

    private fun loadTodayAPOD() {
        launch {
            progress.isVisible = true
            if (isConnected()) {
                val apodResult = mavm.getTodayApod().await()
                when (apodResult) {
                    is Result.Success -> {
                        setTodaysImage(apodResult.data)
                        sharedPrefs.edit().putLong("time", System.currentTimeMillis()).apply()
                    }
                    is Result.Error -> {
                        ivCurrentApod.setImageDrawable(getDrawable(R.drawable.ic_error))
                    }
                }
            } else {
                ivCurrentApod.setImageDrawable(getDrawable(R.drawable.ic_offline))
                Snackbar.make(root, "You are offline", Snackbar.LENGTH_LONG).show()
            }
            progress.isVisible = false
        }
    }

    private fun loadPastAPODs() {
        launch {
            val pastApods = mavm.getPastAPODs().await()
            if (pastApods.isEmpty()) {
                tvNoPastPhotos.isVisible = true
                rvImages.isVisible = false
            } else {
                tvNoPastPhotos.isVisible = false
                val imageAdapter = mainActivityComponent.getImageAdapter().apply {
                    setItems(pastApods)
                }
                rvImages.apply {
                    isNestedScrollingEnabled = false
                    isVisible = true
                    adapter = imageAdapter
                    layoutManager = GridLayoutManager(this@MainActivity, 3)
                }
            }
        }
    }

    private fun setTodaysImage(apod: Apod?) {
        picasso.load(apod?.url).error(R.drawable.ic_error).into(ivCurrentApod)
    }


    private fun isItToday(): Boolean {
        val oldTimeInMillis = sharedPrefs.getLong("time", -1L)
        if (oldTimeInMillis == -1L) return false
        val calender = Calendar.getInstance().apply { timeInMillis = oldTimeInMillis }
        val oldDate = calender.get(Calendar.DAY_OF_MONTH)
        val oldMonth = calender.get(Calendar.MONTH)
        val oldYear = calender.get(Calendar.YEAR)

        calender.timeInMillis = System.currentTimeMillis()
        val date = calender.get(Calendar.DAY_OF_MONTH)
        val month = calender.get(Calendar.MONTH)
        val year = calender.get(Calendar.YEAR)


        return when {
            year != oldYear -> false
            month != oldMonth -> false
            date != oldDate -> false
            else -> true
        }
    }

    override fun onDestroy() {
        coroutineContext.cancelChildren()
        super.onDestroy()
    }
}
