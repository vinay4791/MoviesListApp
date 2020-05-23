package vinay.com.movieslistapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import vinay.com.movieslistapp.R
import vinay.com.movieslistapp.databinding.MoviesItemBinding
import vinay.com.movieslistapp.model.Results
import vinay.com.movieslistapp.util.State

class MoviesItemAdapter() : PagedListAdapter<Results, RecyclerView.ViewHolder>(MoviesDiffCallback) {

    private var state = State.LOADING

    private val DATA_VIEW_TYPE = 0
    private val FOOTER_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<MoviesItemBinding>(layoutInflater, R.layout.movies_item, parent, false)

        return if (viewType == DATA_VIEW_TYPE) {
            MoviesViewHolder(view)
        }  else{
            ListFooterViewHolder.create(parent)
        }
    }

    companion object {
        val MoviesDiffCallback = object : DiffUtil.ItemCallback<Results>() {
            override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE){
            (holder as MoviesViewHolder).bind(getItem(position))
        }else{
             (holder as ListFooterViewHolder).bind(state)
        }
    }
}