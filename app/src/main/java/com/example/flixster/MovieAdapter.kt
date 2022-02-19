package com.example.flixster

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // Expensive operation: creating a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    // Cheap operation: simply bind data to existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener { // we need to add interface for onClick
        private val imageViewPoster = itemView.findViewById<ImageView>(R.id.imageViewPoster)
        private val textViewTitle = itemView.findViewById<TextView>(R.id.textViewTitle)
        private val textViewOverview = itemView.findViewById<TextView>(R.id.textViewOverview)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            textViewTitle.text = movie.title
            textViewOverview.text = movie.overview
            Glide.with(context).load(movie.posterImageUrl).into(imageViewPoster)
        }

        override fun onClick(v: View?) {
            // 1. Get notified of the particular movie which was clicked
            val movie = movies[adapterPosition]
            //Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show() // toast message to check if click works
            // 2. Use intent system to navigate to the new screen
           val intent = Intent(context, DetailActivity::class.java) // use intent constructor to create intent, 2nd param = destination activity
           intent.putExtra("MOVIE_EXTRA", movie) // data to be passed to detail activity
            context.startActivity(intent)
        }
    }

}
