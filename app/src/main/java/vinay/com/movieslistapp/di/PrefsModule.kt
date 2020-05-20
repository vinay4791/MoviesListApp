package vinay.com.vinaydemoproject.di

import android.app.Activity
import android.app.Application
import dagger.Module
import dagger.Provides
import vinay.com.movieslistapp.util.SharedPreferencesHelper
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
open class PrefsModule {

    @Provides
    @Singleton
    @TypeofContext(CONTEXT_APP)
    open fun provideSharedPreferences(application: Application) : SharedPreferencesHelper{
        return SharedPreferencesHelper(application)
    }

    @Provides
    @Singleton
    @TypeofContext(CONTEXT_ACTIVITY)
    fun provideActivitySharedPreferences(activity: Activity) : SharedPreferencesHelper{
        return SharedPreferencesHelper(activity)
    }
}

const val CONTEXT_APP = "Application Context"
const val CONTEXT_ACTIVITY = "Activity Context"

@Qualifier
annotation class TypeofContext(val type : String)