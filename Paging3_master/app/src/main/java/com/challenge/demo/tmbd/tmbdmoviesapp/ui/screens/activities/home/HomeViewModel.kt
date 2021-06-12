package com.challenge.demo.tmbd.tmbdmoviesapp.ui.screens.activities.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.challenge.demo.tmbd.tmbdmoviesapp.api.ApiClient
import com.challenge.demo.tmbd.tmbdmoviesapp.data.datasource.MoviesRemoteMediator
import com.challenge.demo.tmbd.tmbdmoviesapp.data.db.MovieDatabase
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.Movies
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.MoviesMapper
import java.util.*

class HomeViewModel() : ViewModel() {


    lateinit var mDatabase: MovieDatabase
    lateinit var mSource: MoviesRemoteMediator

    fun getPopularMovies(): LiveData<PagingData<Movies.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = mSource,
            pagingSourceFactory = { mDatabase.moviesRxDao().selectAll() }
        ).flow.asLiveData().cachedIn(viewModelScope)
    }

    /**
     * Initializing the viewModel variables from activity
     */
    fun iniialize(mDB: MovieDatabase) {
        mDatabase = mDB
        mSource = MoviesRemoteMediator(
            service = ApiClient.create(),
            database = mDatabase,
            mapper = MoviesMapper(),
            locale = Locale.getDefault()
        )


    }
}