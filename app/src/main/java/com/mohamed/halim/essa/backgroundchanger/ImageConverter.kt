package com.mohamed.halim.essa.backgroundchanger

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("image")
fun loadImage(imageView : ImageView , url : String){
    Picasso.get().load(url).into(imageView)
}