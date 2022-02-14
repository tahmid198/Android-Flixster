package com.example.flixster

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movie>()
    private lateinit var  rvMovies : RecyclerView
    // 1. Define a data model class as a data source - DONE this is our movie class
    // 2. Add a RecyclerView to the layout - DONE
    // 3. Create a custom row layout xml file to visualize the item - DONE
    // 4. Create an adapter and viewholder to render the item - DONE
    // 5. Bind the adapter to the data source to populate the recycler view - DONE
    // 6. Bind a layout manger to the recycler view - DONE

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)

        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter // bind adapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()

        // 2nd param = response handler
        client.get(NOW_PLAYING_URL, object:JsonHttpResponseHandler(){
            // members needed for JsonHttpResponseHandler
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) {
                // log error
                Log.e(TAG,"onFailure: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                // log info
                Log.i(TAG, "onSuccess: JSON data $json")
                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged() // notify adapter data has changed
                    Log.i(TAG, "movie list: $movies")
                } catch (e: JSONException) {
                    Log.e(TAG, "Encountered exception: $e") // error
                }
            }
            // For SocketException when running debugger, try wiping the emulator/cold reboot to see if that fixes it.
            //AVD Manager -> Choose your emulator -> Wipe data/Cold Reboot

        } )

    }
}