package com.ngedev.newsapplicationcompose.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageBinding {
    fun bind(context: Context, imageSource: String, imagePlaceHolder: Int, view: ImageView) {
        Glide.with(context).load(imageSource)
            .placeholder(imagePlaceHolder).fitCenter().dontAnimate().into(view)
    }
}