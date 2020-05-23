package vinay.com.vinaydemoproject.di

import android.app.Application
import dagger.Module
import dagger.Provides
import vinay.com.newsapidemoapp.db.MovieDb

@Module
class AppModule(val app: Application) {
    @Provides
    fun providesApp(): Application = app

    @Provides
    fun providesDB(): MovieDb = MovieDb.create(app)
}