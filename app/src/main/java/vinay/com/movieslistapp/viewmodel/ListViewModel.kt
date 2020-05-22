package vinay.com.movieslistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import vinay.com.movieslistapp.model.*
import vinay.com.movieslistapp.util.Constants.Companion.API_KEY
import vinay.com.movieslistapp.util.Constants.Companion.DEFAULT_LANGUAGE
import vinay.com.movieslistapp.util.State
import vinay.com.vinaydemoproject.di.AppModule
import vinay.com.vinaydemoproject.di.DaggerViewModelComponent
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    val data by lazy { MutableLiveData<Data>() }
    var resultData: LiveData<PagedList<Results>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val moviesDataSourceFactory: MoviesDataSourceFactory

    @Inject
    lateinit var apiService: ApiService

    init {
        DaggerViewModelComponent
                .builder()
                .appModule(AppModule(getApplication()))
                .build().inject(this)

        moviesDataSourceFactory = MoviesDataSourceFactory(compositeDisposable, apiService)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        resultData = LivePagedListBuilder<Int, Results>(moviesDataSourceFactory, config).build()
    }

    fun getData() {
        compositeDisposable.add(
                apiService.getMovies(API_KEY, DEFAULT_LANGUAGE, 1)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Data>() {
                            override fun onSuccess(dataList: Data) {
                                data.postValue(dataList)
                                //     resultData.postValue(dataList.results as PagedList<Results>?)
                            }

                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                            }
                        })
        )
    }

    fun getState(): LiveData<State> = Transformations.switchMap<MoviesDataSource,
            State>(moviesDataSourceFactory.newsDataSourceLiveData, MoviesDataSource::state)

    fun retry() {
        moviesDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return resultData.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}