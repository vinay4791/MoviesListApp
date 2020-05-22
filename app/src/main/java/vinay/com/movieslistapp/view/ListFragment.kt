package vinay.com.movieslistapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.item_list_footer.*

import vinay.com.movieslistapp.R
import vinay.com.movieslistapp.model.Data
import vinay.com.movieslistapp.util.State
import vinay.com.movieslistapp.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private lateinit var moviesItemAdapter: MoviesItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }


    fun initialize() {
        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        initAdapter()
        initState()
    }

    private fun initAdapter() {
        moviesItemAdapter = MoviesItemAdapter { listViewModel.retry() }
        moviesList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        moviesList.adapter = moviesItemAdapter
        listViewModel.resultData.observe(this, Observer {
            moviesItemAdapter.submitList(it)
        })
    }

    private fun initState() {
        list_error.setOnClickListener { listViewModel.retry() }
        listViewModel.getState().observe(this, Observer { state ->
           /* list_progress_bar.visibility = if (listViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE*/
            list_error.visibility = if (listViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!listViewModel.listIsEmpty()) {
                moviesItemAdapter.setState(state ?: State.DONE)
            }
        })
    }

}

/* fun processResponse(data: Data) {
     moviesList.layoutManager = LinearLayoutManager(activity)
     moviesList?.adapter =
             activity?.let { MovieListAdapter(data) }
 }*/

