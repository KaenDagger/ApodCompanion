package io.kaendagger.apodcompanion.ui.viewer


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import io.kaendagger.apodcompanion.R
import io.kaendagger.apodcompanion.data.model.ApodOffline
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class ImageFragment : Fragment() {


    companion object{

        private val apodKey = "APOD_Object"
        fun newInstance(apodOffline: ApodOffline):ImageFragment{
            return ImageFragment().apply {
                val bundle = Bundle().apply {
                    putSerializable(apodKey,apodOffline)
                }
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apod = arguments?.get(apodKey) as? ApodOffline
        apod?.let {
            Picasso.get().load(File(apod.path)).into(ivImage)
        }
    }
}
