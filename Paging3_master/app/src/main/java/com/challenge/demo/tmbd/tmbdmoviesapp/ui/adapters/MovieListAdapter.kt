package com.challenge.demo.tmbd.tmbdmoviesapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.demo.tmbd.tmbdmoviesapp.R
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.Movies
import com.challenge.demo.tmbd.tmbdmoviesapp.listeners.OnRecyclerViewItemClick
import kotlinx.android.synthetic.main.item_movies.view.*


class MovieListAdapter(
    val context: Context,
    val listener: OnRecyclerViewItemClick
) :
    PagingDataAdapter<Movies.Movie, MovieListAdapter.ViewHolder>(COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_movies, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Movies.Movie? = getItem(position)

        Glide.with(context)
            .load(item?.poster?.medium)
            .placeholder(R.drawable.placeholder)
            .into(holder.mThumbnail)
        item.let {
            ViewCompat.setTransitionName(holder.mThumbnail, "image_transition")
            holder.itemView.setOnClickListener {
                if (item != null) {
                    listener.onItemClick(
                        holder.adapterPosition,
                        item,
                        holder.mThumbnail
                    )
                }
            }
            holder.mTitle.text = item?.originalTitle
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mThumbnail = view.movie_poster
        val mTitle = view.movie_title

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movies.Movie>() {
            override fun areItemsTheSame(oldItem: Movies.Movie, newItem: Movies.Movie): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: Movies.Movie, newItem: Movies.Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}