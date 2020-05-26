package vinay.com.movieslistapp.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import vinay.com.movieslistapp.db.MovieDb
import vinay.com.movieslistapp.util.Constants.Companion.API_KEY
import vinay.com.movieslistapp.util.Constants.Companion.DEFAULT_LANGUAGE
import vinay.com.movieslistapp.util.Constants.Companion.INITIAL_PAGE
import vinay.com.movieslistapp.util.State
import java.util.concurrent.Executors

class MoviesBoundaryCallback(private val db: MovieDb,
                             val apiService: ApiService,
                             val compositeDisposable: CompositeDisposable) : PagedList.BoundaryCallback<Results>() {

    private val executor = Executors.newSingleThreadExecutor()
    private var totalPagesCount = 0;
    private var currentPageCount = 1;
    var state: MutableLiveData<State> = MutableLiveData()

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        //1
        updateState(State.LOADING)
        compositeDisposable.add(
                apiService.getMovies(API_KEY, DEFAULT_LANGUAGE, INITIAL_PAGE)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { response ->
                                    totalPagesCount = response.total_pages.toInt()
                                    executor.execute {
                                        db.moviesDao().insert(response.results ?: listOf())
                                    }
                                },
                                {
                                    Log.e("MoviesBoundaryCallback", "Failed to load data!")
                                    updateState(State.ERROR)
                                }
                        )
        )
    }


    override fun onItemAtEndLoaded(itemAtEnd: Results) {
        super.onItemAtEndLoaded(itemAtEnd)
        updateState(State.LOADING)
        if (totalPagesCount >= currentPageCount) {
            compositeDisposable.add(
                    apiService.getMovies(API_KEY, DEFAULT_LANGUAGE, currentPageCount++)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { response ->
                                        executor.execute {
                                            db.moviesDao().insert(response.results ?: listOf())
                                        }
                                    },
                                    {
                                        Log.e("MoviesBoundaryCallback", "Failed to load data!")
                                        updateState(State.ERROR)
                                    }
                            )
            )
        } else {
            updateState(State.DONE)
        }
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}
