package com.example.crud_fiolina_10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.crud_fiolina_10.room.Constant
import com.example.crud_fiolina_10.room.Movie
import com.example.crud_fiolina_10.room.MovieDB
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy { MovieDB(this) }
    private var MovieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setuplistener()
        setupView()
        MovieId = intent.getIntExtra("intent_id", 0)
        //Toast.makeText(this, MovieId.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_Update.visibility = View.GONE

            }
            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_Update.visibility = View.GONE
                getMovie()

            }
            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getMovie()

            }
        }
    }

    fun setuplistener() {
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.MovieDao().addMovie(
                    Movie(
                        0, et_title.text.toString(),
                        et_deskripsi.text.toString()
                    )
                )
                finish()
            }
        }

        btn_Update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.MovieDao().updateMovie(
                    Movie(
                        MovieId, et_title.text.toString(),
                        et_deskripsi.text.toString()
                    )
                )
                finish()
            }
        }
    }

    fun getMovie() {
        MovieId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val movies = db.MovieDao().getMovie(MovieId)[0]
            et_title.setText(movies.title)
            et_deskripsi.setText(movies.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}