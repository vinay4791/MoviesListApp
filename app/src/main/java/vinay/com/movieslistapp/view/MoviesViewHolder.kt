package vinay.com.movieslistapp.view

import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import vinay.com.movieslistapp.databinding.MoviesItemBinding
import vinay.com.movieslistapp.model.Results

class MoviesViewHolder(var view: MoviesItemBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(movie: Results?) {
        view.movie = movie
        view.movieLayout.setOnClickListener {
            movie?.let {
                val action = ListFragmentDirections.actionDetail(it)
                Navigation.findNavController(view.movieLayout).navigate(action)
            }
        }
    }
}
