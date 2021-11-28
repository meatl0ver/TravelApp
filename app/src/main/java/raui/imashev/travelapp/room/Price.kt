package raui.imashev.travelapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "price")
data class Price(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @SerializedName("DATA_ID")
    @Expose
    val dataId: Int? = null,

    @SerializedName("AMOUNT")
    @Expose
    val amount: Int? = null,

    @SerializedName("TYPE")
    @Expose
    val type: String? = null
)
