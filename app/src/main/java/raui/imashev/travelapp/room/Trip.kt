package raui.imashev.travelapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "trip")
data class Trip(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @SerializedName("DATA_ID")
    @Expose
    val dataId: Int? = null,

    @SerializedName("FROM")
    @Expose
    val from: String? = null,

    @SerializedName("TO")
    @Expose
    val to: String? = null
)
