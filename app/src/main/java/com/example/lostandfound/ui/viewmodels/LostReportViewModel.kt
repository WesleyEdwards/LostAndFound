package com.example.lostandfound.ui.viewmodels


import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.lostandfound.ui.models.Report
import com.example.lostandfound.ui.repositories.ReportRepo

class LostReportScreenState() {
    var _report by mutableStateOf<Report?>(null)
    val report: Report? get() = _report

    var menuExpanded by mutableStateOf(false)
    var deleteDialogue by mutableStateOf(false)
    var confirmDelete by mutableStateOf(false)

    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
}

class LostReportViewModel(application: Application) : AndroidViewModel(application) {
    var state = LostReportScreenState()


    suspend fun getReport(reportId: String) {
        state.loading = true
        state.errorMessage = ""
        state._report = null
        try {
            val report = ReportRepo.getReport(reportId)
            state._report = report
        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
        } finally {
            state.loading = false
        }
    }

    suspend fun deleteReport(reportId: String) {
        state.loading = true
        state.errorMessage = ""
        try {
            ReportRepo.deleteReport(reportId)
        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
        } finally {
            state.loading = false
            state.confirmDelete = true
        }
    }
}