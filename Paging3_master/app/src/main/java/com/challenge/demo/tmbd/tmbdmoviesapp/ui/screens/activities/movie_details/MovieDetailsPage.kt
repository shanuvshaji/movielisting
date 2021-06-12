package com.challenge.demo.tmbd.tmbdmoviesapp.ui.screens.activities.movie_details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.challenge.demo.tmbd.tmbdmoviesapp.R
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.Movies
import kotlinx.android.synthetic.main.gallery_details_page.*


class MovieDetailsPage : AppCompatActivity() {

    companion object {
        const val EXTRA_ITEM = "data"
        const val EXTRA_IMAGE_TRANSITION_NAME = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_details_page)
        postponeEnterTransition()
        val extras = intent.extras
        val mMovieData: Movies.Movie = extras?.getParcelable(EXTRA_ITEM)!!

        setTransition(extras)
        setInfo(mMovieData)
    }

    /**
     *  setting transparent status bar
     */
    private fun setTransition(extras: Bundle) {
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.image_shared_element_transition);
        val imageTransitionName =
            extras.getString(EXTRA_IMAGE_TRANSITION_NAME)
        detail_imageview.transitionName = imageTransitionName
    }

    /**
     * Setting up the image and details of the movie
     */
    private fun setInfo(movie: Movies.Movie?) {

        Glide.with(this)
            .load(movie?.poster?.medium)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition();
                    return false
                }


                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition();
                    return false

                }
            })
            .into(detail_imageview)


        movie_title.text = movie?.originalTitle
        description.text = movie?.overview
        back_button.setOnClickListener {
            onBackPressed()
        }


    }
}