
package mungarro.carlos.thecheezery_mungarrocarlos.data.repository

import kotlinx.coroutines.flow.Flow
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.AppDatabase
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ComboEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductComboEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.relation.ComboWithProducts

class CheezeryRepository(private val database: AppDatabase) {

    private val productDao = database.productDao()
    private val comboDao = database.comboDao()
    private val productComboDao = database.productComboDao()

    suspend fun insertProduct(product: ProductEntity): Long {
        return productDao.insertProduct(product)
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    suspend fun getProductById(id: Int): ProductEntity? {
        return productDao.getProductById(id)
    }

    fun getProductsByType(type: String): Flow<List<ProductEntity>> {
        return productDao.getProductsByType(type)
    }

    suspend fun insertCombo(combo: ComboEntity, productIds: List<Int>): Long {
        val comboId = comboDao.insertCombo(combo)
        val relations = productIds.map { productId ->
            ProductComboEntity(productId = productId, comboId = comboId.toInt())
        }
        productComboDao.insertAll(relations)
        return comboId
    }

    fun getAllCombosWithProducts(): Flow<List<ComboWithProducts>> {
        return comboDao.getAllCombosWithProducts()
    }
}