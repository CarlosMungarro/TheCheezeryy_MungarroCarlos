package mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Combos")
data class ComboEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idCombo")
    val id: Int = 0,

    @ColumnInfo(name = "nameCombo")
    val name: String,

    @ColumnInfo(name = "priceCombo")
    val price: Float
)