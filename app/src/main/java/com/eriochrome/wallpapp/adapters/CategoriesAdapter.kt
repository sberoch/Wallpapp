package com.eriochrome.wallpapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eriochrome.wallpapp.R
import com.eriochrome.wallpapp.utils.GlideApp
import kotlinx.android.synthetic.main.category_layout.view.*
import java.util.ArrayList

val catMap = mapOf(
    "artistic" to R.drawable.artistic,
    "black-and-white" to R.drawable.blackandwhite,
    "nature" to R.drawable.nature,
    "texture" to R.drawable.texture
)

class CategoriesAdapter(private var context: Context, val listener: CategoriesViewHolder.CategoryListener) : RecyclerView.Adapter<CategoriesViewHolder>() {

    private var categories: ArrayList<String> = arrayListOf("artistic", "black-and-white", "nature", "texture")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return CategoriesViewHolder(context, view, listener)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categories[position])
    }
}


class CategoriesViewHolder(val context: Context, view: View, val listener: CategoryListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

    interface CategoryListener {
        fun onClick(catName: String)
    }


    private var displayText = view.cat_text_view
    private lateinit var text: String
    private var imageView = view.cat_image_view
    val displayNamesMap = mapOf(
        "artistic" to R.string.artistic,
        "black-and-white" to R.string.blackandwhite,
        "nature" to R.string.natural,
        "texture" to R.string.textures
    )
    init {
        view.setOnClickListener(this)

    }

    fun bind(catName: String) {
        text = catName
        displayText.text = context.getString(displayNamesMap[catName]!!)
        GlideApp.with(context)
            .load(catMap[catName])
            .into(imageView)
    }

    override fun onClick(v: View?) {
        listener.onClick(text)
    }

}