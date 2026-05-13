
package mungarro.carlos.thecheezery_mungarrocarlos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.dao.ComboDao
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.dao.ProductComboDao
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.dao.ProductDao
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ComboEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductComboEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, ComboEntity::class, ProductComboEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun comboDao(): ComboDao
    abstract fun productComboDao(): ProductComboDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cheezery.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}