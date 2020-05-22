package vinay.com.movieslistapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import vinay.com.movieslistapp.R
import vinay.com.movieslistapp.databinding.MoviesItemBinding
import vinay.com.movieslistapp.model.Data

class MovieListAdapter (
        val data: Data
) : RecyclerView.Adapter<MovieListAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<MoviesItemBinding>(layoutInflater,R.layout.movies_item,parent,false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int = data.results.size

    override fun onBindViewHolder(
            holder: MoviesViewHolder,
            position: Int
    ) {
        holder.view.movie = data.results[position]
        holder.view.movieLayout.setOnClickListener {
            val action = ListFragmentDirections.actionDetail(data.results[position])
            Navigation.findNavController(holder.view.movieLayout).navigate(action)
        }
    }

    class MoviesViewHolder(var view: MoviesItemBinding) : RecyclerView.ViewHolder(view.root)
}