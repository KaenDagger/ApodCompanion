package io.kaendagger.apodcompanion.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.kaendagger.apodcompanion.R
import io.kaendagger.apodcompanion.data.model.ApodOffline
import io.kaendagger.apodcompanion.ui.viewer.ViewerActivity
import kotlinx.android.synthetic.main.layout_image.view.*
import java.io.File

class ImageAdapter(private val picasso: Picasso) :RecyclerView.Adapter<ImageAdapter.ViewHolder>(){

    private var list: List<ApodOffline> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_image,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    fun setItems(newList:List<ApodOffline>){
        list = newList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bind(position: Int){
            val apodOffline = list[position]
            with(itemView){
                tvTitle.text = apodOffline.date
                picasso.load(File(apodOffline.path)).into(ivImage)
                ivImage.setOnClickListener {
                    context.startActivity(Intent(context,ViewerActivity::class.java).apply {
                        putExtra("image_no",position)
                    })
                }
            }
        }
    }
}