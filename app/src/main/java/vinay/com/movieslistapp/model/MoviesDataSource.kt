package vinay.com.movieslistapp.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import vinay.com.movieslistapp.util.Constants.Companion.API_KEY
import vinay.com.movieslistapp.util.Constants.Companion.DEFAULT_LANGUAGE
import vinay.com.movieslistapp.util.State

class MoviesDataSource(
        private val apiService: ApiService,
        private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Results>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Results>) {
        updateState(State.LOADING)

        compositeDisposable.add(
                apiService.getMovies(API_KEY, DEFAULT_LANGUAGE,1)
                        .subscribe(
                                { response -> updateState(State.DONE)
                                    callback.onResult(response.results,
                                            null,
                                            2
                                    )
                                },
                                {
                                    updateState(State.ERROR)
                                    setRetry(Action { loadInitial(params, callback) })
                                }
                        )
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Results>) {
        updateState(State.LOADING)
        apiService.getMovies(API_KEY, DEFAULT_LANGUAGE,params.key)
                .subscribe(
                        { response -> updateState(State.DONE)
                            callback.onResult(response.results,
                                    params.key + 1
                            )
                        },
                        {
                            updateState(State.ERROR)
                            setRetry(Action { loadAfter(params, callback) })
                        }
                )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Results>) {

    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}