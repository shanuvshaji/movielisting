package com.challenge.demo.tmbd.tmbdmoviesapp.data.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class MoviesMapper {

    @SuppressLint("WeekBasedYear")
    fun transform(response: MoviesResponse, locale: Locale): Movies {
        return with(response) {
            Movies(
                total = total,
                page = page,
                movies = results.map {
                    Movies.Movie(
                        0,
                        it.id,
                        it.popularity,
                        it.video,
                        it.posterPath?.let { path -> Image(path) },
                        it.adult,
                        it.backdropPath?.let { path -> Image(path) },
                        it.originalLanguage,
                        it.originalTitle,
                        it.title,
                        it.voteAverage,
                        it.overview,
                        it.releaseDate?.let { date ->
                            if (date.isNotEmpty()) {
                                SimpleDateFormat("YYYY-mm-dd", locale).parse(date)
                            } else {
                                null
                            }
                        }
                    )
                }
            )
        }
    }
}