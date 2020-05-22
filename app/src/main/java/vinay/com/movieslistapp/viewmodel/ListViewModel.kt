package vinay.com.movieslistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import vinay.com.movieslistapp.model.ApiService
import vinay.com.movieslistapp.model.Data
import vinay.com.movieslistapp.model.Results
import vinay.com.movieslistapp.util.Constants.Companion.API_KEY
import vinay.com.movieslistapp.util.Constants.Companion.DEFAULT_LANGUAGE
import vinay.com.vinaydemoproject.di.AppModule
import vinay.com.vinaydemoproject.di.DaggerViewModelComponent
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val disposable = CompositeDisposable()
    val data by lazy { MutableLiveData<Data>() }
    val resultData by lazy { MutableLiveData<PagedList<Results>>() }

    @Inject
    lateinit var apiService: ApiService

    init {
        DaggerViewModelComponent
                .builder()
                .appModule(AppModule(getApplication()))
                .build().inject(this)
    }

    fun getData() {
        disposable.add(
                apiService.getMovies(API_KEY, DEFAULT_LANGUAGE, "1")
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

}