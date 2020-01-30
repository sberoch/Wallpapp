package com.eriochrome.wallpapp.models

import java.io.Serializable

data class Wallpaper (
    val name: String = "",
    val category: String = "",
    val mock: String = "trending",
    val timestamp: Long = 0,
    val downloads: Long = 0
) : Serializable