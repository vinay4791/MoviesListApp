package vinay.com.movieslistapp.model

data class ApiKey(
        val request_token: String?,
        val expires_at: String?,
        val success: Boolean?
)

data class Dates(
        val maximum: String?,
        val minimum: String?
)

data class Results(
        val popularity: Double?,
        val vote_count: Integer?,
        val video: Boolean?,
        val poster_path: String?,
        val id: Integer?,
        val adult: Boolean?,
        val backdrop_path: String?,
        val original_language: String?,
        val original_title: String?,
        val genre_ids: List<Integer>?,
        val title: String?,
        val vote_average: Double?,
        val overview: String?,
        val release_date: String?
)

data class Data(val results: List<Results>, val page: Integer, val total_results: Integer, val dates: Dates, val total_pages: Integer)