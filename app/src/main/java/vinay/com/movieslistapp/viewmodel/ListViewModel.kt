package vinay.com.movieslistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import vinay.com.movieslistapp.model.ApiService
import vinay.com.movieslistapp.model.MoviesBoundaryCallback
import vinay.com.movieslistapp.model.Results
import vinay.com.movieslistapp.util.Constants.Companion.PAGE_SIZE_COUNT
import vinay.com.movieslistapp.util.State
import vinay.com.newsapidemoapp.db.MovieDb
import vinay.com.vinaydemoproject.di.AppModule
import vinay.com.vinaydemoproject.di.DaggerViewModelComponent
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    var resultData: LiveData<PagedList<Results>>
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var db: MovieDb

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var app: Application

    val moviesBoundaryCallback: MoviesBoundaryCallback
    val dataSourceFactory: DataSource.Factory<Int, Results>

    init {
        DaggerViewModelComponent
                .builder()
                .appModule(AppModule(getApplication()))
                .build().inject(this)

        val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE_COUNT)
                .setEnablePlaceholders(false)
                .build()

        moviesBoundaryCallback = MoviesBoundaryCallback(db, apiService, compositeDisposable)
        dataSourceFactory = db.moviesDao().getPagedMovies()

        resultData = initializedPagedListBuilder(config, moviesBoundaryCallback, dataSourceFactory).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config, moviesBoundaryCallback: MoviesBoundaryCallback, dataSourceFactory: DataSource.Factory<Int, Results>): LivePagedListBuilder<Int, Results> {
        val livePageListBuilder = LivePagedListBuilder<Int, Results>(dataSourceFactory, config)
        livePageListBuilder.setBoundaryCallback(moviesBoundaryCallback)
        return livePageListBuilder
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getState(): MutableLiveData<State> {
        return moviesBoundaryCallback.state
    }

    fun listIsEmpty(): Boolean {
        return resultData.value.isNullOrEmpty()
    }
}