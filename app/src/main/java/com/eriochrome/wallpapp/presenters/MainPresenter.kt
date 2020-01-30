package com.eriochrome.wallpapp.presenters

import com.eriochrome.wallpapp.contracts.MainContract
import com.eriochrome.wallpapp.models.Wallpaper
import com.eriochrome.wallpapp.models.MainInteraction

class MainPresenter(private var view: MainContract.View?) : MainContract.Listener {

    private val interaction: MainContract.Interaction

    init {
        interaction = MainInteraction(this)
    }

    fun unbind() {
        view = null
    }

    override fun loaded(imList: ArrayList<Wallpaper?>) {
        view?.loaded(imList)
    }

    fun loadTrending() {
        interaction.loadTrending()
    }

    fun loadPopular() {
        interaction.loadPopular()
    }

    fun loadLatest() {
        interaction.loadLatest()
    }

    fun loadCategory(result: String) {
        interaction.loadCategory(result)
    }
}