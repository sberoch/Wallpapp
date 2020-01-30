package com.eriochrome.wallpapp.contracts

import com.eriochrome.wallpapp.models.Wallpaper

interface MainContract {

    interface Interaction {
        fun loadTrending()
        fun loadPopular()
        fun loadLatest()
        fun loadCategory(result: String)
    }

    interface View {
        fun loaded(imList: ArrayList<Wallpaper?>)

    }

    interface Listener {
        fun loaded(imList: ArrayList<Wallpaper?>)

    }
}