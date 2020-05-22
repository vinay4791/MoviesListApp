package vinay.com.movieslistapp.model

import io.reactivex.Single
import vinay.com.vinaydemoproject.di.DaggerApiComponent
import javax.inject.Inject

class ApiService {
    @Inject
    lateinit var api: ApiInterface

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getMovies(api_key: String, language: String, page: String): Single<Data> {
        return api.getMovies(api_key, language, page)
    }
}