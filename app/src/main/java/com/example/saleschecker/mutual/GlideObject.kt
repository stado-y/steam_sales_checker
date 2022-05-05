package com.example.saleschecker.mutual

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.saleschecker.R
import com.example.saleschecker.di.OkHttpClientWithLoggingInterceptor
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.io.InputStream


@GlideModule
@Excludes(OkHttpLibraryGlideModule::class)
class GlideObject : AppGlideModule() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface AppGlideModuleEntryPoint {
        @OkHttpClientWithLoggingInterceptor
        fun getOkHttpClient(): OkHttpClient
    }
    
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val entryPoint: AppGlideModuleEntryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext, 
            AppGlideModuleEntryPoint::class.java,
        )
        val okHttpClient = entryPoint.getOkHttpClient()
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(okHttpClient),
        )
    }

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

        fun ImageView.loadPicture(url: String) {
            GlideApp.with(this)
                .load(url)
                .placeholder(R.drawable.ic_image_placeholder)
                .dontAnimate()
                .dontTransform()
                .into(this)
        }
    }
}