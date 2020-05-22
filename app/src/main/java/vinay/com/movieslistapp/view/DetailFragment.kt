package vinay.com.movieslistapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import vinay.com.movieslistapp.R
import vinay.com.movieslistapp.databinding.FragmentDetailBinding
import vinay.com.movieslistapp.model.Results

class DetailFragment : Fragment() {

    var movie: Results? = null
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            movie = DetailFragmentArgs.fromBundle(it).movieDetail
        }

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = movie?.title

        dataBinding.movie = movie
    }
}
