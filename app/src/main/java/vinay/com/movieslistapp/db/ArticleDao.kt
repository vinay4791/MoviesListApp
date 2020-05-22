package vinay.com.newsapidemoapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import vinay.com.movieslistapp.model.Results


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(movieResult: Results)

    @Query("SELECT * FROM movie_table")
    fun fetchAllTasks(): LiveData<List<Results>>

    /*@Query("SELECT * FROM movie_table WHERE id =:title")
    fun fetchArticle(title : String): LiveData<Results>

    @Query("SELECT * FROM movie_table WHERE title =:title")
    fun fetchArticleData(title : String): Results*/

    @Update
    fun updateArticle(article: Results)

    @Delete
    fun deleteArticle(article: Results)

    @Query("DELETE FROM movie_table")
    fun deleteAllArticles()

    @Query("SELECT count(*) FROM movie_table")
    fun getArticlesCount(): Int
}