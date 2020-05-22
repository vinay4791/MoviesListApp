package vinay.com.movieslistapp.model

import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    /*@GET("movie/now_playing?api_key=6302547a96c85ce957a1b35104e9d5bc&language=en-US&page=1")
    fun getMovies(): Single<Data>*/

    @GET("movie/now_playing")
    fun getMovies(
            @Query("api_key") api_key: String,
            @Query("language") language: String,
            @Query("page") page: String
    ): Single<Data>
}