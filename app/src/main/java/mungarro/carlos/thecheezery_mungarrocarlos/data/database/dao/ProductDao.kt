
package mungarro.carlos.thecheezery_mungarrocarlos.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity): Long

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM Products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Products WHERE idProduct = :productId")
    suspend fun getProductById(productId: Int): ProductEntity?

    @Query("SELECT * FROM Products WHERE typeProduct = :type")
    fun getProductsByType(type: String): Flow<List<ProductEntity>>
}