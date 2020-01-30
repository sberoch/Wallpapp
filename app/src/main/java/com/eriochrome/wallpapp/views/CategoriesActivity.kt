package com.eriochrome.wallpapp.views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.eriochrome.wallpapp.R
import com.eriochrome.wallpapp.adapters.CategoriesAdapter
import com.eriochrome.wallpapp.adapters.CategoriesViewHolder
import com.eriochrome.wallpapp.adapters.ImageAdapter
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity(), CategoriesViewHolder.CategoryListener {

    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        categoriesAdapter = CategoriesAdapter(this, this)
        cat_rv.layoutManager = GridLayoutManager(this, 2)
        cat_rv.adapter = categoriesAdapter

        cat_back.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }

    override fun onClick(catName: String) {
        val returnIntent = Intent()
        returnIntent.putExtra("catName", catName)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
