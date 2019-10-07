package io.kaendagger.apodcompanion.ui.viewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import io.kaendagger.apodcompanion.R
import io.kaendagger.apodcompanion.createViewModel
import io.kaendagger.apodcompanion.data.model.ApodOffline
import io.kaendagger.apodcompanion.di.*
import kotlinx.android.synthetic.main.activity_viewer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ViewerActivity : AppCompatActivity(),CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    @Inject
    lateinit var imagePagerAdapter: ImagePagerAdapter

    lateinit var viewerComponent: ViewerComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)


        viewerComponent = DaggerViewerComponent.builder()
            .aPODRoomModule(APODRoomModule(this.application))
            .contextModule(ContextModule(this))
            .viewerActivityModule(ViewerActivityModule(this))
            .build()
            .apply {injectViewerActivity(this@ViewerActivity)}

        val  viewModel =  createViewModel { viewerComponent.getViewModel() }

        setUpToolBar()

        launch {
            val pastApods = viewModel.getPastAPODs().await()
            imagePagerAdapter.setApods(pastApods)
            setImageViewer(pastApods)
        }
    }

    private fun setUpToolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "APOD Viewer"
            titleColor = getColor(android.R.color.white)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setImageViewer(pastApods:List<ApodOffline>){
        val itemNo = intent.getIntExtra("image_no",-1)
        var currApod = pastApods[itemNo]

        tvTitle.text = currApod.title
        tvTitle.setOnClickListener {
            ImageDetailsFrag.newInstance(currApod).show(supportFragmentManager,"IDFrag")
        }

        viewPager.apply {
            adapter = imagePagerAdapter
            if (itemNo != -1) {
                currentItem = itemNo
            }
            addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    tvTitle.text = pastApods[position].title
                    currApod = pastApods[position]
                }
            })
        }
    }

    override fun onDestroy() {
        coroutineContext.cancelChildren()
        super.onDestroy()
    }
}
