package com.challenge.demo.tmbd.tmbdmoviesapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.Movies

@Dao
interface MovieKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<Movies.MovieRemoteKeys>)

    @Query("SELECT * FROM movie_remote_keys WHERE movieId = :movieId")
    fun remoteKeysByMovieId(movieId: Long): Movies.MovieRemoteKeys?

    @Query("DELETE FROM movie_remote_keys")
    fun clearRemoteKeys()

}