package com.example.flixster

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import org.json.JSONArray

// data class used to parse data from json file
@Parcelize
data class Movie(
    // attributes that we want to parse out and display on ui
    val movieId: Int,
    val voteAverage: Double,
    private val posterPath: String,
    val title: String,
    val overview: String,
) : Parcelable { // have movie data class extend parcel
    @IgnoredOnParcel // added bc posterImageUrl cannot be serialized on parcel
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath" // url for different sizes

    companion object {
        fun fromJsonArray(movieJSONArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>() // list of our movies
            for (i in 0 until movieJSONArray.length()) {
                val movieJson = movieJSONArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getDouble("vote_average"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")
                    )
                )
            }
            return movies
        }
    }
}