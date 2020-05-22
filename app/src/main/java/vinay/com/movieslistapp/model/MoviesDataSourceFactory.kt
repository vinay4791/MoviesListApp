package vinay.com.movieslistapp.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val apiService: ApiService) : DataSource.Factory<Int, Results>() {

    val newsDataSourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Results> {
        val newsDataSource = MoviesDataSource(apiService, compositeDisposable)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}