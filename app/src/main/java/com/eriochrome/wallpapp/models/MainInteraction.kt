package com.eriochrome.wallpapp.models

import com.eriochrome.wallpapp.contracts.MainContract
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainInteraction(private val listener: MainContract.Listener) : MainContract.Interaction {

    private val ref = FirebaseDatabase.getInstance().reference
    private var imagesList: ArrayList<Wallpaper?> = ArrayList()

    override fun loadTrending() {
        ref.orderByChild("name").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                imagesList.clear()
                for (it in p0.children) {
                    val img = it.getValue<Wallpaper>(Wallpaper::class.java)
                    if (img?.mock == "trending") imagesList.add(img)
                }
                listener.loaded(imagesList)
            }
        })
    }

    override fun loadCategory(result: String) {
        ref.orderByChild("cat").equalTo(result).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                imagesList.clear()
                p0.children.mapNotNullTo(imagesList) {
                    it.getValue<Wallpaper>(Wallpaper::class.java)
                }
                listener.loaded(imagesList)
            }
        })
    }

    override fun loadPopular() {
        ref.orderByChild("name").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    imagesList.clear()
                    for (it in p0.children) {
                        val img = it.getValue<Wallpaper>(Wallpaper::class.java)
                        if (img?.mock == "popular") imagesList.add(img)
                    }
                    listener.loaded(imagesList)
                }
            })
    }

    override fun loadLatest() {
        ref.orderByChild("name").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                imagesList.clear()
                for (it in p0.children) {
                    val img = it.getValue<Wallpaper>(Wallpaper::class.java)
                    if (img?.mock == "latest") imagesList.add(img)
                }
                listener.loaded(imagesList)
            }
        })
    }
}

