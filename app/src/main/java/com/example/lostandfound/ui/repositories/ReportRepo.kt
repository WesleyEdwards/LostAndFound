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

    suspend fun createReport(createReport: ReportStats) {
        UserRepo.getUser().let {
            val formattedReport = Report(
                _id = UUID.randomUUID().toString(),
                userId = it?.uid ?: "",
                reportStats = createReport
            )
            val doc = Firebase.firestore.collection(collectionPath).document()
            doc.set(formattedReport).await()
        }
    }

    suspend fun getMyReports(): List<Report> {
        println("getMyReports")
        return Firebase.firestore.collection(collectionPath)
            .whereEqualTo("userId", UserRepo.getUser()?.uid).get().await()
            .toObjects()

    }

    suspend fun getAllReports(): List<Report> {
        println("Getting all reports")
        val reports = Firebase.firestore.collection(collectionPath)
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    println("${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                println("Failure, Error getting documents: ")
            }
        return reports.await().toObjects<Report>()
    }

    suspend fun getReport(reportId: String): Report? {
        return getAllReports().firstOrNull() { it._id == reportId }.let {
            Firebase.firestore.collection(collectionPath)
                .get().await()
                .toObjects<Report>()
                .firstOrNull { it._id == reportId }
        }
    }

    suspend fun deleteReport(reportId: String) {
        val doc = Firebase.firestore.collection(collectionPath).whereEqualTo("_id", reportId)
        doc.get().await().documents.firstOrNull()?.reference?.delete()?.await()
    }

    suspend fun updateReport(report: Report) {
        val doc = Firebase.firestore.collection(collectionPath).whereEqualTo("_id", report._id)
        doc.get().await()
            .documents.firstOrNull()?.reference?.update(
                "reportStats",
                report.reportStats
            )?.await()
    }

}