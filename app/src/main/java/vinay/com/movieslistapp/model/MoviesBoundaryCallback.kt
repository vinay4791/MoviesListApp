/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


package vinay.com.movieslistapp.model

import android.util.Log
import androidx.paging.PagedList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import vinay.com.movieslistapp.util.Constants.Companion.API_KEY
import vinay.com.movieslistapp.util.Constants.Companion.DEFAULT_LANGUAGE
import vinay.com.movieslistapp.util.Constants.Companion.INITIAL_PAGE
import vinay.com.movieslistapp.util.PagingRequestHelper
import vinay.com.newsapidemoapp.db.MovieDb
import java.util.concurrent.Executors

class MoviesBoundaryCallback(private val db: MovieDb,
                             val apiService: ApiService,
                             val compositeDisposable: CompositeDisposable) : PagedList.BoundaryCallback<Results>() {

    private val executor = Executors.newSingleThreadExecutor()
    private val helper = PagingRequestHelper(executor)
    private var totalPagesCount = 0;
    private var currentPageCount = 1;


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        //1
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->
            compositeDisposable.add(
                    apiService.getMovies(API_KEY, DEFAULT_LANGUAGE, INITIAL_PAGE)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { response ->
                                        totalPagesCount = response.total_pages.toInt()
                                        executor.execute {
                                            db.moviesDao().insert(response.results ?: listOf())
                                            helperCallback.recordSuccess()
                                        }
                                    },
                                    {
                                        Log.e("MoviesBoundaryCallback", "Failed to load data!")
                                        helperCallback.recordFailure(it)
                                    }
                            )
            )
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Results) {
        super.onItemAtEndLoaded(itemAtEnd)

        if (totalPagesCount >= currentPageCount) {
            helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
                compositeDisposable.add(
                        apiService.getMovies(API_KEY, DEFAULT_LANGUAGE, currentPageCount++)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { response ->
                                            executor.execute {
                                                db.moviesDao().insert(response.results ?: listOf())
                                                helperCallback.recordSuccess()
                                            }
                                        },
                                        {
                                            Log.e("MoviesBoundaryCallback", "Failed to load data!")
                                            helperCallback.recordFailure(it)
                                        }
                                )
                )
            }
        }
    }
}
