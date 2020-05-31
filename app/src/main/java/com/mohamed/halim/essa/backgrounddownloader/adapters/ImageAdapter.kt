package com.mohamed.halim.essa.backgrounddownloader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import com.mohamed.halim.essa.backgrounddownloader.R
import com.mohamed.halim.essa.backgrounddownloader.data.Image
import com.mohamed.halim.essa.backgrounddownloader.databinding.ImagesListItemBinding
class ImageAdapter(var imageClickListener: ImageClickListener) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private lateinit var images: List<Image>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val item: ImagesListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.images_list_item,
            parent,
            false
        )
        return ImageViewHolder(item)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun swapList(newImages: List<Image>) {
        images = newImages
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(val item: ImagesListItemBinding) :
        RecyclerView.ViewHolder(item.root), View.OnClickListener {
        init {
            item.root.setOnClickListener(this)
        }

        fun bind(image: Image) {
            item.image = image
        }

        override fun onClick(v: View?) {
            imageClickListener.onImageClickListener(images[adapterPosition])
        }

    }

    interface ImageClickListener {
        fun onImageClickListener(image: Image)
    }
}
