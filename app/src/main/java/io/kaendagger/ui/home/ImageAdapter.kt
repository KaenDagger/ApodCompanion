package io.kaendagger.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.kaendagger.apodcompanion.R
import io.kaendagger.apodcompanion.data.model.ApodOffline
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
        holder.bind(list[position])
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bind(apodOffline: ApodOffline){
            with(itemView){
                tvTitle.text = apodOffline.date
                picasso.load(File(apodOffline.path)).into(ivImage)
            }
        }
    }
}