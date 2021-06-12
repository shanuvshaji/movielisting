package com.challenge.demo.tmbd.tmbdmoviesapp.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.challenge.demo.tmbd.tmbdmoviesapp.api.ApiService
import com.challenge.demo.tmbd.tmbdmoviesapp.data.db.MovieDatabase
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.Movies
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.MoviesMapper
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import java.lang.Exception
import java.util.*

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val service: ApiService,
    private val database: MovieDatabase,
    private val mapper: MoviesMapper,
    private val locale: Locale
) : RemoteMediator<Int, Movies.Movie>() {

    companion object {
        const val INVALID_PAGE = -1
    }

    val keysDao = database.movieRemoteKeysRxDao()
    val movieDao = database.moviesRxDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movies.Movie>
    ): MediatorResult {

        return try {
            val loadedPage =
                when (loadType) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }

            if (loadedPage == INVALID_PAGE) {
                return MediatorResult.Success(endOfPaginationReached = true)
            } else {
                val response = service.fetchNowPlayingMovies1(
                    page = loadedPage,
                    language = locale.language
                ).body()

                val data = mapper.transform(response!!, locale)
                database.withTransaction {
                    try {
                        if (loadType == LoadType.REFRESH) {
                            database.movieRemoteKeysRxDao().clearRemoteKeys()
                            database.moviesRxDao().clearMovies()
                        }

                        val prevKey = if (loadedPage == 1) null else loadedPage - 1
                        val nextKey = if (response.endOfPage) null else loadedPage + 1
                        val keys = data.movies.map {
                            Movies.MovieRemoteKeys(
                                movieId = it.movieId,
                                prevKey = prevKey,
                                nextKey = nextKey
                            )
                        }
                        keysDao.insertAll(keys)
                        movieDao.insertAll(data.movies)
                        return@withTransaction MediatorResult.Success(endOfPaginationReached = data.endOfPage)

                    } catch (ex: Exception) {
                        return@withTransaction MediatorResult.Error(ex)
                    }

                }


            }


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


    private fun getRemoteKeyForLastItem(state: PagingState<Int, Movies.Movie>): Movies.MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            keysDao.remoteKeysByMovieId(repo.movieId)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Movies.Movie>): Movies.MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            keysDao.remoteKeysByMovieId(movie.movieId)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movies.Movie>): Movies.MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { id ->
                keysDao.remoteKeysByMovieId(id)
            }
        }
    }
}