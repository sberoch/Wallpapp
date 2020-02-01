package com.eriochrome.wallpapp.views

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.eriochrome.wallpapp.R
import com.eriochrome.wallpapp.adapters.ImageAdapter
import com.eriochrome.wallpapp.contracts.MainContract
import com.eriochrome.wallpapp.models.Wallpaper
import com.eriochrome.wallpapp.presenters.MainPresenter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

private val fragMenuIndexMap = mapOf(0 to R.id.trending,
                            1 to R.id.popular,
                            2 to R.id.latest,
                            3 to R.id.categories
)

//TODO: contabilizar descargas

class MainActivity : AppCompatActivity(),
    MainContract.View {

    private val presenter: MainPresenter = MainPresenter(this)
    private lateinit var imageAdapter: ImageAdapter
    private val reqCode = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAds()

        drawer_button.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        nav_drawer.setNavigationItemSelectedListener {
            drawer_layout.closeDrawers()
            executeMenuOption(it.itemId)
            false
        }

        top_navigation.setTypeface(ResourcesCompat.getFont(this.applicationContext,
            R.font.lato
        ))
        top_navigation.setNavigationChangeListener { _, position ->
            //Remember to add to map if new tabs are inserted.
            executeMenuOption(fragMenuIndexMap[position]!!)
        }

        imageAdapter = ImageAdapter(this)
        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.adapter = imageAdapter

        presenter.loadTrending()
    }

    private fun executeMenuOption(itemId: Int) {
        when (itemId) {
            R.id.trending -> {
                presenter.loadTrending()
                top_navigation.setCurrentActiveItem(0)
            }
            R.id.popular -> {
                presenter.loadPopular()
                top_navigation.setCurrentActiveItem(1)
            }
            R.id.latest -> {
                presenter.loadLatest()
                top_navigation.setCurrentActiveItem(2)
            }
            R.id.categories -> {
                startActivityForResult(Intent(this, CategoriesActivity::class.java), reqCode)
                top_navigation.setCurrentActiveItem(3)
            }
            R.id.contact -> {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "message/rfc822"
                i.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.app_email)))
                try {
                    startActivity(Intent.createChooser(i, getString(R.string.send_email)))
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(this, getString(R.string.no_email_services), Toast.LENGTH_SHORT).show()
                }
            }
            R.id.share -> share()
            R.id.rate_us -> rateApp()
        }
    }

    override fun loaded(imList: ArrayList<Wallpaper?>) {
        imageAdapter.setItems(imList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == reqCode) {
            if (resultCode == Activity.RESULT_OK) {
                //Artistic as default, but should always be a non-null string.
                val result: String = data?.getStringExtra("catName") ?: "artistic"
                presenter.loadCategory(result)

            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Trending as default if cancelled.
                presenter.loadTrending()
                top_navigation.setCurrentActiveItem(0)
            }
        }
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    private fun setupAds() {
        MobileAds.initialize(this, "ca-app-pub-5220294257716774~6160719449")
        val adRequest = AdRequest.Builder().build()
        ad_view.loadAd(adRequest)
    }

    private fun share() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "Check out the best free HD Wallpapers in Wallpapp: https://play.google.com/store/apps/details?id=${packageName}"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "App link")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_app_via)))
    }

    private fun rateApp() {
        val uri: Uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }
}
