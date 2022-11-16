package com.ngedev.newsapplication.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ngedev.newsapplication.R

object ImageBinding {
    fun bind(context: Context, imageSource: String, imagePlaceHolder: Int, view: ImageView) {
        Glide.with(context).load(imageSource)
            .placeholder(imagePlaceHolder).fitCenter().dontAnimate().into(view)
    }
}