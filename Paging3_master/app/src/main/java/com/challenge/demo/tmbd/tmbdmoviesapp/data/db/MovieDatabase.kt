package com.challenge.demo.tmbd.tmbdmoviesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.challenge.demo.tmbd.tmbdmoviesapp.data.converters.Converters
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.Movies
import com.challenge.demo.tmbd.tmbdmoviesapp.data.dao.MovieDao
import com.challenge.demo.tmbd.tmbdmoviesapp.data.dao.MovieKeysDao

@Database(
    entities = [Movies.Movie::class, Movies.MovieRemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun moviesRxDao(): MovieDao
    abstract fun movieRemoteKeysRxDao(): MovieKeysDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java, "TmBD.db"
            ).allowMainThreadQueries()
                .build()
    }
}