package com.challenge.demo.tmbd.tmbdmoviesapp.ui.screens.activities.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.demo.tmbd.tmbdmoviesapp.R
import com.challenge.demo.tmbd.tmbdmoviesapp.data.db.MovieDatabase
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.Movies
import com.challenge.demo.tmbd.tmbdmoviesapp.databinding.ActivityHomeBinding
import com.challenge.demo.tmbd.tmbdmoviesapp.listeners.OnRecyclerViewItemClick
import com.challenge.demo.tmbd.tmbdmoviesapp.ui.adapters.LoadingGridStateAdapter
import com.challenge.demo.tmbd.tmbdmoviesapp.ui.adapters.MovieListAdapter
import com.challenge.demo.tmbd.tmbdmoviesapp.ui.screens.activities.movie_datails.MovieDetailsPage
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private lateinit var mViewModel: HomeViewModel
    private lateinit var mAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewmodel = mViewModel
        binding.lifecycleOwner = this
        initViews()
    }

    /**
     *  Initialising variables in viewModel and setting up recyclerview
     */
    private fun initViews() {
        mViewModel.iniialize(MovieDatabase.getInstance(this))

        mAdapter = MovieListAdapter(this, object :OnRecyclerViewItemClick{
            override fun onItemClick(pos: Int, data: Any, view: Any) {
                /**
                 * Redirecting to Details view of Movie with transition param
                 */
                if (view is View) {
                    val intent = Intent(this@HomeActivity, MovieDetailsPage::class.java)
                    intent.putExtra(MovieDetailsPage.EXTRA_ITEM, data as Movies.Movie)
                    intent.putExtra(MovieDetailsPage.EXTRA_IMAGE_TRANSITION_NAME, view.transitionName)
                    val options: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this@HomeActivity,
                            view,
                            ViewCompat.getTransitionName(view)!!
                        )
                    startActivity(intent, options.toBundle())
                }
            }
        })


        movies_recyclerview.layoutManager = GridLayoutManager(this, 2)
        movies_recyclerview.adapter = mAdapter.withLoadStateFooter(
            footer = LoadingGridStateAdapter()

        )

        /**
         * Observing the movie API results
         */
        mViewModel.getPopularMovies().observe(this, Observer {
            if (it != null) {
                mAdapter.submitData(lifecycle, it)
            }
        })


    }


}