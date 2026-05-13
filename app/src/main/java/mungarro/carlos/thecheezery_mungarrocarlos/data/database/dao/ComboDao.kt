
package mungarro.carlos.thecheezery_mungarrocarlos.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.entity.ComboEntity
import mungarro.carlos.thecheezery_mungarrocarlos.data.database.relation.ComboWithProducts

@Dao
interface ComboDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCombo(combo: ComboEntity): Long

    @Delete
    suspend fun deleteCombo(combo: ComboEntity)

    @Transaction
    @Query("SELECT * FROM Combos")
    fun getAllCombosWithProducts(): Flow<List<ComboWithProducts>>
}