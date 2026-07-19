package com.pratheeksha.scamshield.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ScamNumberDao {

    @Query("SELECT * FROM scam_numbers ORDER BY reportCount DESC")
    fun getAllScamNumbers(): Flow<List<ScamNumberEntity>>

    @Query("SELECT * FROM scam_numbers WHERE phoneNumber = :number LIMIT 1")
    suspend fun findByNumber(number: String): ScamNumberEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entity: ScamNumberEntity)

    @Update
    suspend fun update(entity: ScamNumberEntity)

    @Query("SELECT * FROM scam_numbers WHERE isSynced = 0")
    suspend fun getUnsyncedReports(): List<ScamNumberEntity>

    @Query("UPDATE scam_numbers SET isSynced = 1 WHERE phoneNumber = :number")
    suspend fun markAsSynced(number: String)
}