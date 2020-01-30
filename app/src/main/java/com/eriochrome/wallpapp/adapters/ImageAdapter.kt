package com.eriochrome.wallpapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eriochrome.wallpapp.R
import com.eriochrome.wallpapp.models.Wallpaper
import com.eriochrome.wallpapp.utils.GlideApp
import com.eriochrome.wallpapp.views.ImageActivity
import com.google.android.gms.ads.InterstitialAd
import com.google.firebase.storage.FirebaseStorage
import com.rishabhharit.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.image_layout.view.*
import java.util.ArrayList

class ImageAdapter(private var context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private var wallpapers: ArrayList<Wallpaper?> = ArrayList()

    fun setItems(imList: ArrayList<Wallpaper?>) {
        wallpapers.clear()
        wallpapers.addAll(imList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_layout, parent, false)
        return ViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return wallpapers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wallpapers[position])
    }
}


class ViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val imageView: RoundedImageView = view.imageView
    private var wallpaper: Wallpaper? = null
    private val ref = FirebaseStorage.getInstance().reference
    init {
        view.setOnClickListener(this)
    }

    fun bind(wallpaper: Wallpaper?) {
        this.wallpaper = wallpaper
        val thumbnailName = "thumbnails/thumb_${wallpaper!!.name}"
        GlideApp.with(context)
            .load(ref.child(thumbnailName))
            .placeholder(R.drawable.item_placeholder)
            .into(imageView)
    }

    override fun onClick(v: View?) {
        val i = Intent(context, ImageActivity::class.java)
        i.putExtra("image", wallpaper)
        context.startActivity(i)
    }

}