package vinay.com.vinaydemoproject.di

import dagger.Component
import vinay.com.movieslistapp.model.ApiService

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject (service : ApiService)
}