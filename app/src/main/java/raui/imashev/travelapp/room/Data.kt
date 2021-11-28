package raui.imashev.travelapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "full_data")
data class Data(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,

    @SerializedName("CURRENCY")
    @Expose
    val currency: String? = null,
)
