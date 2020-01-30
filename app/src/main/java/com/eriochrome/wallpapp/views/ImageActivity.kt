package com.eriochrome.wallpapp.views

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.eriochrome.wallpapp.R
import com.eriochrome.wallpapp.contracts.ImageContract
import com.eriochrome.wallpapp.presenters.ImagePresenter
import com.eriochrome.wallpapp.utils.GlideApp
import com.eriochrome.wallpapp.utils.ImageDownloader
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_image.*


class ImageActivity : AppCompatActivity(), ImageContract.View {

    private val presenter = ImagePresenter(this)
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        presenter.getImage(intent)
        presenter.showImage()
        setupAds()

        back_btn.setOnClickListener { finish() }
        set_as_wall_btn.setOnClickListener {
            showAdIfLoaded()
            Toast.makeText(this, getString(R.string.setting_as_wall), Toast.LENGTH_SHORT).show()
            presenter.setAsWallpaper()
        }
        download_btn.setOnClickListener{
            showAdIfLoaded()
            Toast.makeText(this, getString(R.string.downloading), Toast.LENGTH_SHORT).show()
            presenter.downloadImage()
        }
    }

    private fun showAdIfLoaded() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
    }

    private fun setupAds() {
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
    }

    override fun setAsWallpaperWithRef(child: StorageReference) {
        GlideApp.with(this)
            .asBitmap()
            .load(child)
            .into(object : CustomTarget<Bitmap>(1080, 1920) {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val wpm = WallpaperManager.getInstance(this@ImageActivity)
                    wpm.setBitmap(resource)
                    Toast.makeText(this@ImageActivity, getString(R.string.success), Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun downloadWithRef(child: StorageReference) {
        GlideApp.with(this)
            .asBitmap()
            .load(child)
            .into(object : CustomTarget<Bitmap>(1080, 1920) {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    ImageDownloader.saveImage(resource, this@ImageActivity)
                    Toast.makeText(this@ImageActivity, getString(R.string.success), Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun showImageFrom(child: StorageReference) {
        GlideApp.with(this)
            .load(child)
            .placeholder(R.drawable.item_placeholder)
            .into(image)
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }
}
