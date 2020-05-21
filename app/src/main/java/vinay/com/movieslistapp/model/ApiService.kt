package vinay.com.movieslistapp.model

import io.reactivex.Single
import vinay.com.vinaydemoproject.di.DaggerApiComponent
import javax.inject.Inject

class ApiService {

    @Inject
    lateinit var api : ApiInterface

    init{
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey> {
        return api.getApiKey()
    }

    fun getMovies(): Single<Data> {
        return api.getMovies()
    }

}