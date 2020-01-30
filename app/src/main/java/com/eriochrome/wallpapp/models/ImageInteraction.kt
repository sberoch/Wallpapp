package com.eriochrome.wallpapp.models

import com.eriochrome.wallpapp.contracts.ImageContract
import com.google.firebase.storage.FirebaseStorage

class ImageInteraction(private val listener: ImageContract.Listener) : ImageContract.Interaction {

    private lateinit var wallpaper: Wallpaper
    private val storageRef = FirebaseStorage.getInstance().reference

    override fun setImage(wallpaper: Wallpaper) {
        this.wallpaper = wallpaper
    }

    override fun getImageToShow() {
        listener.showImageFrom(storageRef.child(wallpaper.name))
    }

    override fun downloadImage() {
        listener.downloadWithRef(storageRef.child(wallpaper.name))
    }

    override fun setAsWallpaper() {
        listener.setAsWallpaperWithRef(storageRef.child(wallpaper.name))
    }
}