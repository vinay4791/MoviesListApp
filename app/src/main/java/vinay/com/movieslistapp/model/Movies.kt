package vinay.com.movieslistapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Dates(
        val maximum: String?,
        val minimum: String?
)

@Entity(tableName = "movie_table")
data class Results(
        val popularity: Double?,
        val vote_count: Int?,
        val video: Boolean?,
        val poster_path: String?,
        @PrimaryKey(autoGenerate = true) @ColumnInfo @NonNull val id: Int?,
        val adult: Boolean?,
        val backdrop_path: String?,
        val original_language: String?,
        val original_title: String?,
        /*val genre_ids: List<Integer>?,*/
         val title: String?,
        val vote_average: Double?,
        val overview: String?,
        val release_date: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(popularity)
        parcel.writeValue(vote_count)
        parcel.writeValue(video)
        parcel.writeString(poster_path)
        parcel.writeValue(id)
        parcel.writeValue(adult)
        parcel.writeString(backdrop_path)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(title)
        parcel.writeValue(vote_average)
        parcel.writeString(overview)
        parcel.writeString(release_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Results> {
        override fun createFromParcel(parcel: Parcel): Results {
            return Results(parcel)
        }

        override fun newArray(size: Int): Array<Results?> {
            return arrayOfNulls(size)
        }
    }
}


data class Data(val results: List<Results>,
                val page: Integer,
                val total_results: Integer,
                val dates: Dates,
                val total_pages: Integer)