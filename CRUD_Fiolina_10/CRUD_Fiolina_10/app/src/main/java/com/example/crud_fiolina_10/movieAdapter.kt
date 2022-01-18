package com.example.crud_fiolina_10

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crud_fiolina_10.room.Movie
import kotlinx.android.synthetic.main.list_movie.view.*

class movieAdapter(private val movies: ArrayList<Movie>, private val listener: OnAdapterListener) : RecyclerView.Adapter<movieAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie  = movies[position]
        holder.view.text_title.text = movie.title
        holder.view.text_title.setOnClickListener{
            listener.OnClick( movie )
        }
        holder.view.icon_edit.setOnClickListener{
            listener.OnUpdate( movie )
        }
        holder.view.icon_delete.setOnClickListener{
            listener.OnDelete( movie )

        }
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Movie>){
        movies.clear()
        movies.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun OnClick(movie: Movie)
        fun OnUpdate(movie: Movie)
        fun OnDelete(movie: Movie)
    }
}