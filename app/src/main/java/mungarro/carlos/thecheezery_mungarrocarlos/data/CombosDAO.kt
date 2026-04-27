package mungarro.carlos.thecheezery_mungarrocarlos.data

import android.content.ContentValues
import mungarro.carlos.thecheezery_mungarrocarlos.domain.Combo
import mungarro.carlos.thecheezery_mungarrocarlos.domain.Product
import kotlin.text.insert

class CombosDAO(private val dbHelper: DatabaseHelper) {

    fun insertCombo(name: String, price: Float, productIds: List<Int>): Long {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        try {
            val values = ContentValues().apply {
                put(CheezeryContract.CombosEntry.COLUMN_NAME, name)
                put(CheezeryContract.CombosEntry.COLUMN_PRICE, price)
            }
            val comboId = db.insert(CheezeryContract.CombosEntry.TABLE_NAME, null, values)

            if (comboId != -1L) {
                productIds.forEach { productId ->
                    val relValues = ContentValues().apply {
                        put(CheezeryContract.ProductsComboEntry.COLUMN_COMBO_ID, comboId)
                        put(CheezeryContract.ProductsComboEntry.COLUMN_PRODUCT_ID, productId)
                    }
                    db.insert(CheezeryContract.ProductsComboEntry.TABLE_NAME, null, relValues)
                }
                db.setTransactionSuccessful()
            }
            return comboId
        } finally {
            db.endTransaction()
        }
    }

    fun getAllCombos(): List<Combo> {
        val db = dbHelper.readableDatabase
        val combos = mutableListOf<Combo>()

        val cursor = db.query(CheezeryContract.CombosEntry.TABLE_NAME, null, null, null, null, null, null)

        cursor.use { c ->
            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndexOrThrow(CheezeryContract.CombosEntry.COLUMN_ID))
                val name = c.getString(c.getColumnIndexOrThrow(CheezeryContract.CombosEntry.COLUMN_NAME))
                val price = c.getFloat(c.getColumnIndexOrThrow(CheezeryContract.CombosEntry.COLUMN_PRICE))

                val products = getProductsForCombo(id)
                combos.add(Combo(id, name, price, products))
            }
        }
        return combos
    }

    private fun getProductsForCombo(comboId: Int): List<Product> {
        val db = dbHelper.readableDatabase
        val products = mutableListOf<Product>()

        val query = """
            SELECT p.* FROM ${CheezeryContract.ProductsEntry.TABLE_NAME} p
            INNER JOIN ${CheezeryContract.ProductsComboEntry.TABLE_NAME} pc ON p.${CheezeryContract.ProductsEntry.COLUMN_ID} = pc.${CheezeryContract.ProductsComboEntry.COLUMN_PRODUCT_ID}
            WHERE pc.${CheezeryContract.ProductsComboEntry.COLUMN_COMBO_ID} = ?
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(comboId.toString()))

        cursor.use { c ->
            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndexOrThrow(CheezeryContract.ProductsEntry.COLUMN_ID))
                val name = c.getString(c.getColumnIndexOrThrow(CheezeryContract.ProductsEntry.COLUMN_NAME))
                val price = c.getFloat(c.getColumnIndexOrThrow(CheezeryContract.ProductsEntry.COLUMN_PRICE))
                val image = c.getString(c.getColumnIndexOrThrow(CheezeryContract.ProductsEntry.COLUMN_IMAGE))
                val desc = c.getString(c.getColumnIndexOrThrow(CheezeryContract.ProductsEntry.COLUMN_DESCRIPTION))
                val type = c.getString(c.getColumnIndexOrThrow(CheezeryContract.ProductsEntry.COLUMN_TYPE))
                products.add(Product(id, name, price, image, desc, type))
            }
        }
        return products
    }
}