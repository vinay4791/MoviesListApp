package vinay.com.movieslistapp.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET("movie/now_playing?api_key=6302547a96c85ce957a1b35104e9d5bc&language=en-US&page=1")
    fun getMovies() : Single<Data>
}