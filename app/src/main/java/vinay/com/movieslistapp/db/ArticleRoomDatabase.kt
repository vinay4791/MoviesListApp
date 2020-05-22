package vinay.com.newsapidemoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vinay.com.movieslistapp.model.Results

@Database(entities = arrayOf(Results::class), version = 1, exportSchema = false)
abstract class ArticleRoomDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        // Singleton prevents multiple instances owedf database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ArticleRoomDatabase? = null

        fun getDatabase(context: Context): ArticleRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ArticleRoomDatabase::class.java,
                        "article_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}