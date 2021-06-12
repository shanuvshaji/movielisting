package com.challenge.demo.tmbd.tmbdmoviesapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

data class MoviesResponse(
    @SerializedName("total_pages")
    val total: Int = 0,
    val page: Int = 0,
    val results: List<Movie>
) {

    @IgnoredOnParcel
    val endOfPage = total == page

    @Entity(tableName = "popular_movies")
    data class Movie(
        val movieId: Long,
        val popularity: Double,
        @SerializedName("vote_count") val voteCount: Int,
        val video: Boolean,
        @SerializedName("poster_path") val posterPath: String?,
        @PrimaryKey
        val id: Long,
        val poster: Image?,
        val adult: Boolean,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val originalTitle: String,
        val title: String,
        @SerializedName("vote_average") val voteAverage: Double,
        val overview: String,
        @SerializedName("release_date") val releaseDate: String?
    )


    @Parcelize
    @Entity(tableName = "popular_keys")
    data class RemoteKeys(
        @PrimaryKey
        val movieId: Long = 0,
        val prevKey: Int? = 0,
        val nextKey: Int? = 0,
        val lastUpdated : Long = System.currentTimeMillis()
    ) : Parcelable


}