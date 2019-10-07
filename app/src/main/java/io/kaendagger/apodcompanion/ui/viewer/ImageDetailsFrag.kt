package io.kaendagger.apodcompanion.ui.viewer

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.kaendagger.apodcompanion.R
import io.kaendagger.apodcompanion.data.model.ApodOffline
import kotlinx.android.synthetic.main.layout_image_details.*

class ImageDetailsFrag :BottomSheetDialogFragment(){

    companion object{
        private val apodKey = "APOD_Object"
        fun newInstance(apodOffline: ApodOffline):ImageDetailsFrag{
            return ImageDetailsFrag().apply {
                val bundle = Bundle().apply {
                    putSerializable(apodKey,apodOffline)
                }
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_image_details,container,false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apod = arguments?.get(apodKey) as? ApodOffline
        apod?.let {
            tvTitle.text = it.title
            tvDate.text = it.date
            tvExplanation.text = """
            Explanation:
            ${it.explanation}
        """.trimIndent()
        }
    }


}