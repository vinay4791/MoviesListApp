package vinay.com.movieslistapp.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val apiService: ApiService) : DataSource.Factory<Int, Results>() {

    val moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Results> {
        val moviesDataSource = MoviesDataSource(apiService, compositeDisposable)
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }
}