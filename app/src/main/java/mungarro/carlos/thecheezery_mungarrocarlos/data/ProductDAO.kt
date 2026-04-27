package mungarro.carlos.thecheezery_mungarrocarlos.data

import android.content.ContentValues
import mungarro.carlos.thecheezery_mungarrocarlos.domain.Product
import mungarro.carlos.thecheezery_mungarrocarlos.data.CheezeryContract.ProductsEntry
import kotlin.text.insert

class ProductsDAO(private val dbHelper: DatabaseHelper) {
    fun insertProduct(product: Product): Long{
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(ProductsEntry.COLUMN_NAME, product.name)
            put(ProductsEntry.COLUMN_PRICE, product.price)
            put(ProductsEntry.COLUMN_DESCRIPTION, product.description)
            put(ProductsEntry.COLUMN_IMAGE, product.image)
            put(ProductsEntry.COLUMN_TYPE, product.type)
        }

        return db.insert(ProductsEntry.TABLE_NAME, null, values)
    }

    fun getAllProducts(): List<Product> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(
                ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE,
                ProductsEntry.COLUMN_TYPE
            ),
            null,
            null,
            null,
            null,
            null
        )

        val products = mutableListOf<Product>()

        with (cursor) {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = getFloat(getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val image = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                val description = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val type = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_TYPE))
                products.add(Product(id, name, price, image, description, type))
            }
        }

        cursor.close()

        return products
    }

    fun getProductsByType(type: String): List<Product> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(
                ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE,
                ProductsEntry.COLUMN_TYPE
            ),
            "${ProductsEntry.COLUMN_TYPE} = ?",
            arrayOf(type),
            null,
            null,
            null
        )

        val products = mutableListOf<Product>()

        with (cursor) {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = getFloat(getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val image = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                val description = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val productType = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_TYPE))
                products.add(Product(id, name, price, image, description, productType))
            }
        }

        cursor.close()

        return products
    }

    fun getProductById(productId: Int) : Product? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(
                ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE,
                ProductsEntry.COLUMN_TYPE
            ),
            "${ProductsEntry.COLUMN_ID} = ?",
            arrayOf(productId.toString()),
            null,
            null,
            null
        )

        val product: Product? = cursor.use {

            if (it.moveToFirst()){
                val id = it.getInt(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = it.getFloat(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val image = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                val description = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val type = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_TYPE))
                Product(id, name, price, image, description, type)
            } else {
                null
            }
        }

        return product
    }
}