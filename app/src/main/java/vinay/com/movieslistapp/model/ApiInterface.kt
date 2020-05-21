package vinay.com.movieslistapp.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {


        @GET("authentication/token/new?api_key=6302547a96c85ce957a1b35104e9d5bc")
        fun getApiKey() : Single<ApiKey>

        @FormUrlEncoded
        @POST("discover/movie?primary_release_date.gte=2014-09-15&primary_release_date.lte=2014-10-22")
        fun getMovies(@Field("key") key : String) : Single<List<Object>>

}