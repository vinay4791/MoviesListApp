package vinay.com.movieslistapp.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vinay.com.movieslistapp.model.ApiInterface
import vinay.com.movieslistapp.model.ApiService
import vinay.com.movieslistapp.util.Constants.Companion.BASE_URL
import java.util.concurrent.TimeUnit

@Module
class ApiModule {

    @Provides
    fun provideOkHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
    }

    @Provides
    fun provideAnimalApi(okHttpClient: OkHttpClient): ApiInterface {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(ApiInterface::class.java)
    }

    @Provides
    fun provideApiService(): ApiService {
        return ApiService()
    }
}