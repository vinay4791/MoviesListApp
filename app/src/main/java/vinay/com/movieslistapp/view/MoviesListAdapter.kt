package vinay.com.movieslistapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import vinay.com.movieslistapp.R
import vinay.com.movieslistapp.model.Data
import vinay.com.movieslistapp.model.Results

class MovieListAdapter (
        val data: Data,
        val mContext: Context,
        val onMovieListAdapterItemClickListener: OnMovieListAdapterItemClickListener
) :
        RecyclerView.Adapter<MovieListAdapter.CustomViewHolder>() {

    interface OnMovieListAdapterItemClickListener {
        fun onMovieListAdapterItemClick(title: String?)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MovieListAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movies_item_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = data.results.size

    override fun onBindViewHolder(
            holder: MovieListAdapter.CustomViewHolder,
            position: Int
    ) {
        holder.bind(data.results[position], onMovieListAdapterItemClickListener, mContext);
    }

    class CustomViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val sourceTextView: TextView = itemView.findViewById(R.id.source_tv)
        val dateTextView: TextView = itemView.findViewById(R.id.date_tv)
        val headlinesTextView: TextView = itemView.findViewById(R.id.movies_tv)
        val headlinesImageView: ImageView = itemView.findViewById(R.id.movies_iv)

        fun bind(
                resultData: Results,
                onHeadlinesListCustomAdapterItemClickListener: OnMovieListAdapterItemClickListener,
                mContext: Context
        ) {
            sourceTextView.text = resultData.original_title
           // dateTextView.text = DataUtil.getDateString(article.publishedAt)
            headlinesTextView.text = resultData.title

            val requestOptions = RequestOptions().fitCenter()
            Glide.with(mContext).load("https://image.tmdb.org/t/p/w780" + resultData.backdrop_path).apply(requestOptions)
                    .into(headlinesImageView)

            itemView.setOnClickListener {
                onHeadlinesListCustomAdapterItemClickListener.onMovieListAdapterItemClick(resultData.title)
            }
        }
    }
}