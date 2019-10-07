package io.kaendagger.apodcompanion.ui.viewer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import io.kaendagger.apodcompanion.data.model.ApodOffline
import javax.inject.Inject

class ImagePagerAdapter @Inject constructor(fm:FragmentManager) :FragmentStatePagerAdapter(fm){

    private lateinit var apods:List<ApodOffline>

    fun setApods(list:List<ApodOffline>){
        apods = list
    }
    override fun getItem(position: Int): Fragment {
        return ImageFragment.newInstance(apods[position])
    }

    override fun getCount() = apods.size
}