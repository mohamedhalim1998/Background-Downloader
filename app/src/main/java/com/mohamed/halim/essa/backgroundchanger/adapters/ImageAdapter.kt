package com.mohamed.halim.essa.backgroundchanger.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.halim.essa.backgroundchanger.R
import com.mohamed.halim.essa.backgroundchanger.data.Image
import com.mohamed.halim.essa.backgroundchanger.databinding.ImagesListItemBinding

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){
    private var images : List<Image>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
       val item : ImagesListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.images_list_item, parent, false)
        return ImageViewHolder(item)

    }

    override fun getItemCount(): Int {
      return images?.size ?:0
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images!![position])
    }
    fun swapList(newImages : List<Image>){
        images = newImages
        notifyDataSetChanged()
    }
    class ImageViewHolder(val item: ImagesListItemBinding): RecyclerView.ViewHolder(item.root) {
        fun bind(image: Image) {
            item.image = image
        }

    }
}
