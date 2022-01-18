package com.example.crud_fiolina_10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.withContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud_fiolina_10.room.Constant
import com.example.crud_fiolina_10.room.Movie
import com.example.crud_fiolina_10.room.MovieDB
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { MovieDB(this) }
    lateinit var movieAdapter: movieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SetupListener()
        SetupRecylerView()
    }

    override fun onStart() {
        super.onStart()
        loadMovie()
    }

    fun loadMovie(){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = db.MovieDao().getMovies()
            Log.d("MainActivity", "dbresponse: $movies")
            withContext(Dispatchers.Main){
                movieAdapter.setData(movies)
            }
        }
    }

        fun SetupListener() {
            add_movie.setOnClickListener {
                intentEdit(0,Constant.TYPE_CREATE)
            }
        }

        fun intentEdit(MovieId:Int, intentType: Int) {
            startActivity(
                Intent(applicationContext, AddActivity::class.java)
                    .putExtra("intent_id", MovieId)
                    .putExtra("intent_type", intentType)
            )

        }

        private fun SetupRecylerView(){
            movieAdapter = movieAdapter(ArrayList(), object : movieAdapter.OnAdapterListener{
                override fun OnClick(movie: Movie) {
                    //Toast.makeText(applicationContext, movie.title, Toast.LENGTH_SHORT).show()
                    //read detail note
                    intentEdit(movie.id, Constant.TYPE_READ)

                }

                override fun OnUpdate(movie: Movie) {
                    intentEdit(movie.id, Constant.TYPE_UPDATE)
                }

                override fun OnDelete(movie: Movie) {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.MovieDao().deleteMovie( movie)
                        loadMovie()
                    }
                }


            })
            rv_movie.apply{
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = movieAdapter

            }
        }
    }
