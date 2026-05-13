
package mungarro.carlos.thecheezery_mungarrocarlos.data.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ComboEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductComboEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductEntity

data class ComboWithProducts(
    @Embedded
    val combo: ComboEntity,

    @Relation(
        parentColumn = "idCombo",
        entityColumn = "idProduct",
        associateBy = Junction(
            value = ProductComboEntity::class,
            parentColumn = "idCombo",
            entityColumn = "idProduct"
        )
    )
    val products: List<ProductEntity>
)