package vinay.com.movieslistapp.model

import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    @GET("movie/now_playing")
    fun getMovies(
            @Query("api_key") api_key: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Single<Data>
}