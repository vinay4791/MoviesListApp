package vinay.com.movieslistapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import vinay.com.movieslistapp.R
import vinay.com.movieslistapp.databinding.MoviesItemBinding
import vinay.com.movieslistapp.model.Data

class MovieListAdapter (
        val data: Data,
        val mContext: Context,
        val onMovieListAdapterItemClickListener: OnMovieListAdapterItemClickListener
) :
        RecyclerView.Adapter<MovieListAdapter.MoviesViewHolder>() {

    interface OnMovieListAdapterItemClickListener {
        fun onMovieListAdapterItemClick(title: String?)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MovieListAdapter.MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<MoviesItemBinding>(layoutInflater,R.layout.movies_item,parent,false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int = data.results.size

    override fun onBindViewHolder(
            holder: MovieListAdapter.MoviesViewHolder,
            position: Int
    ) {
        holder.view.movie = data.results[position]
        holder.view.movieLayout.setOnClickListener {
            /*val action = .actionGoToDetail(animalList[position])
            Navigation.findNavController(holder.view.animalItemLayout).navigate(action)*/
        }
    }

    class MoviesViewHolder(var view: MoviesItemBinding) : RecyclerView.ViewHolder(view.root)

    //https://image.tmdb.org/t/p/w780
    //https://image.tmdb.org/t/p/w500/
}