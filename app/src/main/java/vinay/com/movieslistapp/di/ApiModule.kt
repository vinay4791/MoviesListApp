package vinay.com.vinaydemoproject.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vinay.com.movieslistapp.model.ApiInterface
import vinay.com.movieslistapp.model.ApiService

@Module
class ApiModule {

    private val BASE_URL = "https://www.themoviedb.org/3/"

    @Provides
    fun provideOkHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
    }


    @Provides
    fun provideAnimalApi(okHttpClient: OkHttpClient): ApiInterface {
        return Retrofit.Builder().baseUrl(BASE_URL).
                client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.
                        create()).
                build().create(ApiInterface::class.java)
    }

    @Provides
    fun provideAnimalApiService(): ApiService {
        return ApiService()
    }
}