package com.challenge.demo.tmbd.tmbdmoviesapp.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(val url: String): Parcelable {
    companion object {
        private const val PATH = "https://image.tmdb.org/t/p"
    }

    @IgnoredOnParcel
    val small: Uri = Uri.parse("$PATH/w92/$url")

    @IgnoredOnParcel
    val medium: Uri = Uri.parse("$PATH/w185/$url")

    @IgnoredOnParcel
    val large: Uri = Uri.parse("$PATH/w342/$url")

    @IgnoredOnParcel
    val original: Uri = Uri.parse("$PATH/original/$url")
}