package vinay.com.movieslistapp.di

import dagger.Component
import vinay.com.movieslistapp.model.ApiService

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject (service : ApiService)
}