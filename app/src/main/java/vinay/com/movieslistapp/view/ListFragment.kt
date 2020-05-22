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
import kotlinx.android.synthetic.main.fragment_list.*

import vinay.com.movieslistapp.R
import vinay.com.movieslistapp.model.Data
import vinay.com.movieslistapp.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private val moviesDataObserver = Observer<Data> { list ->
        list?.let {
            processResponse(it)
        }
    }

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

        listViewModel.data.observe(this, moviesDataObserver)
        listViewModel.getData()

        /*moviesList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = movieListCustomAdapter
        }*/
    }

    fun processResponse(data: Data) {
        moviesList.layoutManager = LinearLayoutManager(activity)
        moviesList?.adapter =
                activity?.let { MovieListAdapter(data) }
    }
}
