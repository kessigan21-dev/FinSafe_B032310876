package my.edu.utem.finsafe.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>
}