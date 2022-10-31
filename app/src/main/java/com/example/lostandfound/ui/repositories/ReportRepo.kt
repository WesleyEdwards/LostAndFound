package com.example.lostandfound.ui.repositories

import com.example.lostandfound.ui.models.Report
import com.example.lostandfound.ui.models.ReportStats
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*

object ReportRepo {

    private var collectionPath = "reports"

    private var reportsCache = mutableListOf<Report>()
    private var cacheInitialized = false

    suspend fun createReport(createReport: ReportStats) {
        UserRepo.getUser().let {
            val formattedReport = Report(
                _id = UUID.randomUUID().toString(),
                userId = it?.uid ?: "",
                reportStats = createReport
            )
            reportsCache.add(formattedReport)
            val doc = Firebase.firestore.collection(collectionPath).document()
            doc.set(formattedReport).await()
        }
    }

    suspend fun getMyReports(): List<Report> {
        if (!cacheInitialized) {
            return Firebase.firestore.collection(collectionPath)
                .whereEqualTo("userId", UserRepo.getUser()?.uid).get().await()
                .toObjects<Report>()
        }
        return reportsCache.filter { it.userId == UserRepo.getUser()?.uid }
    }

    suspend fun getAllReports(): List<Report> {
        if (!cacheInitialized) {
            return Firebase.firestore.collection(collectionPath)
                .get().await().toObjects()
        }
        cacheInitialized = true
        return reportsCache
    }

    suspend fun getReport(reportId: String): Report? {
        return reportsCache.firstOrNull() { it._id == reportId }.let {
            Firebase.firestore.collection(collectionPath)
                .get().await()
                .toObjects<Report>()
                .firstOrNull { it._id == reportId }
        }
    }

    suspend fun deleteReport(reportId: String) {
        reportsCache.removeAll { it._id == reportId }
        val doc = Firebase.firestore.collection(collectionPath).whereEqualTo("_id", reportId)
        doc.get().await().documents.firstOrNull()?.reference?.delete()?.await()
    }

    suspend fun updateReport(report: Report) {
        Collections.replaceAll(
            reportsCache,
            reportsCache.first { it._id == report._id },
            report
        )
        val doc = Firebase.firestore.collection(collectionPath).whereEqualTo("_id", report._id)
        doc.get().await()
            .documents.firstOrNull()?.reference?.update(
                "reportStats",
                report.reportStats
            )?.await()
    }
    fun clearCache() {
        reportsCache.clear()
        cacheInitialized = false
    }

    fun isMyReport(reportId: String): Boolean {
        return reportsCache.firstOrNull() { it._id == reportId }?.userId == UserRepo.getUser()?.uid
    }
}