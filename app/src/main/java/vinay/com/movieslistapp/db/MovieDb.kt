package vinay.com.movieslistapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vinay.com.movieslistapp.model.Results

@Database(
        entities = [Results::class],
        version = 1,
        exportSchema = false
)
abstract class MovieDb : RoomDatabase() {

    companion object {
        fun create(context: Context): MovieDb {
            val databaseBuilder = Room.databaseBuilder(context, MovieDb::class.java, "movies.db")
            return databaseBuilder.build()
        }
    }

    abstract fun moviesDao(): MovieDao
}