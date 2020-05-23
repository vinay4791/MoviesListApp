package vinay.com.newsapidemoapp.db

import androidx.paging.DataSource
import androidx.room.*
import vinay.com.movieslistapp.model.Results


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<Results>)

    @Query("SELECT * FROM movie_table")
    fun getPagedMovies(): DataSource.Factory<Int, Results>
}
