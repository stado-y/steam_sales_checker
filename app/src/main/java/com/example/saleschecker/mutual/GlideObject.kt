package com.example.saleschecker.mutual

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.saleschecker.R

@GlideModule
class GlideObject : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.apply {
            setDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
        }
    }

    companion object {

//        fun fillImageViewFromURI(view: ImageView, uri: String?, size: String) {
//            GlideApp.with(view)
//                .load("${BuildConfig.IMAGE_BASE_URL}${size}${uri}")
//                .placeholder(R.drawable.ic_image_placeholder)
//                .dontAnimate()
//                .dontTransform()
//                //.centerInside()
//                .into(view)
//        }

        fun ImageView.loadPicture(view: ImageView, url: String) {
            GlideApp.with(view)
                .load(url)
                .placeholder(R.drawable.ic_image_placeholder)
                .dontAnimate()
                .dontTransform()
                .into(view)
        }
    }
}