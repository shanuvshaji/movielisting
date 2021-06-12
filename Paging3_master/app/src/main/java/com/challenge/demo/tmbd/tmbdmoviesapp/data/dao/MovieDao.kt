package com.challenge.demo.tmbd.tmbdmoviesapp.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.Movies

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movies.Movie>)

    @Query("SELECT * FROM movies ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, Movies.Movie>

    @Query("DELETE FROM movies")
    fun clearMovies()

}