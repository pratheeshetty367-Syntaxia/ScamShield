package com.pratheeksha.scamshield.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.pratheeksha.scamshield.data.local.ScamNumberDao
import com.pratheeksha.scamshield.data.local.ScamNumberEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScamNumberRepository @Inject constructor(
    private val dao: ScamNumberDao,
    private val firestore: FirebaseFirestore
) {
    fun getAllScamNumbers(): Flow<List<ScamNumberEntity>> = dao.getAllScamNumbers()

    suspend fun reportNumber(phoneNumber: String, category: String) {
        val existing = dao.findByNumber(phoneNumber)
        val updated = existing?.copy(
            reportCount = existing.reportCount + 1,
            isSynced = false
        ) ?: ScamNumberEntity(phoneNumber = phoneNumber, category = category)

        dao.insertOrUpdate(updated)
        syncToFirestore(updated)
    }

    private suspend fun syncToFirestore(entity: ScamNumberEntity) {
        try {
            firestore.collection("scam_reports")
                .document(entity.phoneNumber)
                .set(entity)
                .await()
            dao.markAsSynced(entity.phoneNumber)
        } catch (e: Exception) {
            // Offline or network failure — stays isSynced = false, retried later
        }
    }

    suspend fun syncPendingReports() {
        dao.getUnsyncedReports().forEach { syncToFirestore(it) }
    }
}