
package mungarro.carlos.thecheezery_mungarrocarlos.data.database.dao

import androidx.room.*
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductComboEntity

@Dao
interface ProductComboDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductCombo(productCombo: ProductComboEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(relations: List<ProductComboEntity>)
}