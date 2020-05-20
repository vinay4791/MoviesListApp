package vinay.com.movieslistapp.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

        @GET("authentication/token/new")
        fun getApiKey() : Single<Object>

        @FormUrlEncoded
        @POST("now-playing")
        fun getMovies(@Field("key") key : String) : Single<List<Object>>

}