package mungarro.carlos.thecheezery_mungarrocarlos.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import mungarro.carlos.thecheezery_mungarrocarlos.data.CheezeryContract.ProductsEntry
class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cheezery.db"
        private const val DATABASE_VERSION = 3
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON")

        db.execSQL(
            """
                CREATE TABLE ${ProductsEntry.TABLE_NAME} (
                ${ProductsEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ProductsEntry.COLUMN_NAME} TEXT NOT NULL,
                ${ProductsEntry.COLUMN_IMAGE} TEXT,
                ${ProductsEntry.COLUMN_PRICE} REAL NOT NULL,
                ${ProductsEntry.COLUMN_DESCRIPTION} TEXT,
                ${ProductsEntry.COLUMN_TYPE} TEXT NOT NULL
                )
            """.trimIndent()
        )

        db.execSQL(
            """
                CREATE TABLE ${CheezeryContract.CombosEntry.TABLE_NAME} (
                ${CheezeryContract.CombosEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${CheezeryContract.CombosEntry.COLUMN_NAME} TEXT NOT NULL,
                ${CheezeryContract.CombosEntry.COLUMN_PRICE} REAL NOT NULL
                )
            """.trimIndent()
        )

        db.execSQL(
            """
                CREATE TABLE ${CheezeryContract.ProductsComboEntry.TABLE_NAME} (
                ${CheezeryContract.ProductsComboEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${CheezeryContract.ProductsComboEntry.COLUMN_COMBO_ID} INTEGER NOT NULL,
                ${CheezeryContract.ProductsComboEntry.COLUMN_PRODUCT_ID} INTEGER NOT NULL,
                FOREIGN KEY(${CheezeryContract.ProductsComboEntry.COLUMN_COMBO_ID}) REFERENCES ${CheezeryContract.CombosEntry.TABLE_NAME}(${CheezeryContract.CombosEntry.COLUMN_ID}) ON DELETE CASCADE,
                FOREIGN KEY(${CheezeryContract.ProductsComboEntry.COLUMN_PRODUCT_ID}) REFERENCES ${ProductsEntry.TABLE_NAME}(${ProductsEntry.COLUMN_ID}) ON DELETE CASCADE
                )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${CheezeryContract.ProductsComboEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${CheezeryContract.CombosEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${ProductsEntry.TABLE_NAME}")
        onCreate(db)
    }
}