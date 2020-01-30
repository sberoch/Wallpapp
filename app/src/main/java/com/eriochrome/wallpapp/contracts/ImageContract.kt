package com.eriochrome.wallpapp.contracts

import com.eriochrome.wallpapp.models.Wallpaper
import com.google.firebase.storage.StorageReference

interface ImageContract {

    interface Interaction {
        fun setImage(wallpaper: Wallpaper)
        fun getImageToShow()
        fun downloadImage()
        fun setAsWallpaper()

    }

    interface View {
        fun showImageFrom(child: StorageReference)
        fun downloadWithRef(child: StorageReference)
        fun setAsWallpaperWithRef(child: StorageReference)

    }

    interface Listener {
        fun showImageFrom(child: StorageReference)
        fun downloadImage()
        fun downloadWithRef(child: StorageReference)
        fun setAsWallpaperWithRef(child: StorageReference)

    }
}