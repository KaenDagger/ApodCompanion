package io.kaendagger.apodcompanion.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import io.kaendagger.apodcompanion.R
import io.kaendagger.apodcompanion.checkPermissions
import io.kaendagger.apodcompanion.fadeIn
import io.kaendagger.apodcompanion.permissions
import io.kaendagger.apodcompanion.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashActivity : AppCompatActivity() ,CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val PERM_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (checkPermissions(permissions)){
            goToMainActivity(750)
        }else{
            launch {
                delay(1000)
                permContainer.fadeIn()
            }
        }

        btnPerm.setOnClickListener {
            ActivityCompat.requestPermissions(this, permissions,PERM_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERM_CODE){
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
            goToMainActivity(500)
        }
    }

    private fun goToMainActivity(delay: Long){
        launch {
            delay(delay)
            startActivity(Intent(this@SplashActivity,MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }
}
