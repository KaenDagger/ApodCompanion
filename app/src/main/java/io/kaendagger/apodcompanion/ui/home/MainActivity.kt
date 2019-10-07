package io.kaendagger.apodcompanion.ui.home

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
import io.kaendagger.apodcompanion.di.APODComponent
import io.kaendagger.apodcompanion.di.APODRoomModule
import io.kaendagger.apodcompanion.di.ContextModule
import io.kaendagger.apodcompanion.di.DaggerAPODComponent
import io.kaendagger.apodcompanion.ui.APODViewModel
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

    lateinit var apodComponent: APODComponent

    private val PERM_REQUEST = 1

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    @Inject
    lateinit var picasso: Picasso


    private lateinit var mavm: APODViewModel
    private var permStatus by Delegates.observable(false) { _, _, _ ->
        loadPastAPODs()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!checkPermissions(permissions)
        ) {
            ActivityCompat.requestPermissions(this, permissions, PERM_REQUEST)
        } else {
            permStatus = true
        }

        btnPermission.setOnClickListener {
            ActivityCompat.requestPermissions(this, permissions, PERM_REQUEST)
        }

        apodComponent = DaggerAPODComponent.builder()
            .aPODRoomModule(APODRoomModule(this.application))
            .contextModule(ContextModule(this))
            .build()
            .apply { injectMainActivity(this@MainActivity) }

        mavm = createViewModel { apodComponent.getViewModel() }

        launch {
            val pastApods = mavm.getPastAPODs().await()
            if (!isItToday() || pastApods.isEmpty()) {
                loadTodayAPOD()
            } else {
                Log.i("PUI", "its Today")
                progress.isVisible = false
                if (pastApods.isNotEmpty())
                    picasso.load(File(pastApods[0].path)).into(ivCurrentApod)
            }
            loadPastAPODs()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERM_REQUEST) {
            for (idx in permissions.indices) {
                if (grantResults[idx] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(
                        this,
                        "Please allow ${permissions[idx]} permission",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
            }
            permStatus = true
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
                        Log.e("PUI", "Error fetching")
                        ivCurrentApod.setImageDrawable(getDrawable(R.drawable.ic_error))
                    }
                }
            } else {
                Log.i("PUI", "offline")
                ivCurrentApod.setImageDrawable(getDrawable(R.drawable.ic_offline))
                Snackbar.make(root, "You are offline", Snackbar.LENGTH_LONG).show()
            }
            progress.isVisible = false
        }
    }

    private fun loadPastAPODs() {
        launch {
            if (permStatus) {
                tvPermissionMessage.isVisible = false
                btnPermission.isVisible = false
                val pastApods = mavm.getPastAPODs().await()
                if (pastApods.isEmpty()) {
                    tvNoPastPhotos.isVisible = true
                    rvImages.isVisible = false
                } else {
                    tvNoPastPhotos.isVisible = false
                    val imageAdapter = apodComponent.getImageAdapter().apply {
                        setItems(pastApods)
                    }
                    rvImages.apply {
                        isVisible = true
                        adapter = imageAdapter
                        layoutManager = GridLayoutManager(this@MainActivity, 3)
                    }
                }
            } else {
                tvNoPastPhotos.isVisible = false
                rvImages.isVisible = false
                tvPermissionMessage.isVisible = true
                btnPermission.isVisible = true
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
