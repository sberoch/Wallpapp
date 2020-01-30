package com.eriochrome.wallpapp.presenters

import android.content.Intent
import com.eriochrome.wallpapp.contracts.ImageContract
import com.eriochrome.wallpapp.models.Wallpaper
import com.eriochrome.wallpapp.models.ImageInteraction
import com.google.firebase.storage.StorageReference

class ImagePresenter(private var view: ImageContract.View?) : ImageContract.Listener {

    private val interaction: ImageContract.Interaction

    init {
        interaction = ImageInteraction(this)
    }

    fun unbind() {
        view = null
    }

    fun getImage(intent: Intent?) {
        val wallpaper: Wallpaper = intent?.getSerializableExtra("image") as Wallpaper
        interaction.setImage(wallpaper)
    }

    fun showImage() {
        interaction.getImageToShow()
    }

    override fun showImageFrom(child: StorageReference) {
        view?.showImageFrom(child)
    }


    override fun downloadWithRef(child: StorageReference) {
        view?.downloadWithRef(child)
    }

    override fun downloadImage() {
        interaction.downloadImage()
    }

    fun setAsWallpaper() {
        interaction.setAsWallpaper()
    }

    override fun setAsWallpaperWithRef(child: StorageReference) {
        view?.setAsWallpaperWithRef(child)
    }

}