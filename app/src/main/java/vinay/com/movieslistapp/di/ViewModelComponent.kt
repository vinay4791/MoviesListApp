package vinay.com.vinaydemoproject.di

import dagger.Component
import vinay.com.movieslistapp.viewmodel.ListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class,  AppModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewModel)
}