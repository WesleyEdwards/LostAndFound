package com.example.lostandfound.ui.repositories

import com.example.lostandfound.ui.models.Report
import com.example.lostandfound.ui.models.ReportStats
import com.example.lostandfound.ui.models.ReportStatus
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*

object ReportRepo {

    private var collectionPath = "reports"

    private var reportsCache: MutableList<Report>? = null

    suspend fun createReport(createReport: ReportStats) {
        UserRepo.getUser().let {
            val formattedReport = Report(
                _id = UUID.randomUUID().toString(),
                userId = it?.uid ?: "",
                userEmail = it?.email ?: "",
                userName = it?.displayName ?: "",
                reportStats = createReport
            )
            reportsCache?.add(formattedReport)
            val doc = Firebase.firestore.collection(collectionPath).document()
            doc.set(formattedReport).await()
        }
    }

    suspend fun getMyReports(): List<Report> {
        if (reportsCache == null) {
            return Firebase.firestore.collection(collectionPath)
                .whereEqualTo("userId", UserRepo.getUser()?.uid).get().await()
                .toObjects<Report>()
        }
        return reportsCache?.filter { it.userId == UserRepo.getUser()?.uid } ?: mutableListOf()
    }

    suspend fun getAllActiveReports(): List<Report> {
        if (reportsCache == null) {
            return Firebase.firestore.collection(collectionPath)
                .get().await().toObjects<Report>().apply { reportsCache = this.toMutableList() }
                .filter { it.reportStats.status == ReportStatus.LOST }
        }
        return reportsCache
            ?: mutableListOf<Report>().filter { it.reportStats.status == ReportStatus.LOST }
    }

    suspend fun getReport(reportId: String): Report? {
        return reportsCache?.find { it._id == reportId }.let {
            Firebase.firestore.collection(collectionPath)
                .get().await()
                .toObjects<Report>()
                .firstOrNull { it._id == reportId }
        }
    }

    suspend fun deleteReport(reportId: String) {
        reportsCache?.removeAll { it._id == reportId }
        Firebase.firestore.collection(collectionPath).whereEqualTo("_id", reportId).apply {
            this.get().await().documents.firstOrNull()?.reference?.delete()?.await()
        }
    }

    suspend fun updateReport(report: Report) {
        if (reportsCache != null) {
            Collections.replaceAll(
                reportsCache!!.toList(),
                reportsCache?.first { it._id == report._id },
                report
            )
        }
        Firebase.firestore.collection(collectionPath).whereEqualTo("_id", report._id).apply {
            this.get().await()
                .documents.firstOrNull()?.reference?.update(
                    "reportStats",
                    report.reportStats
                )?.await()
        }
        return pullAndRecache()
    }

    private suspend fun pullAndRecache() {
        return Firebase.firestore.collection(collectionPath)
            .get().await().toObjects<Report>().toMutableList().let { reportsCache = it }
    }

    fun clearCache() {
        reportsCache?.clear()
        reportsCache = null
    }

    fun isMyReport(reportId: String): Boolean {
        return reportsCache?.firstOrNull { it._id == reportId }?.userId == UserRepo.getUser()?.uid
    }
}