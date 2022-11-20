package com.example.lostandfound.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.lostandfound.ui.models.Report
import com.example.lostandfound.ui.models.ReportStats
import com.example.lostandfound.ui.repositories.ReportRepo

class CreateReportScreenState {

    var reportStats: ReportStats by mutableStateOf(ReportStats())
    var initialStats: ReportStats by mutableStateOf(ReportStats())

    var loading by mutableStateOf(false)
    var creationSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
}

class CreateReportViewModel(application: Application) : AndroidViewModel(application) {
    var state = CreateReportScreenState()


    fun setReport(report: Report) {
        state.reportStats = report.reportStats
    }

    fun isDirty(): Boolean {
        return state.reportStats == state.initialStats ||
                state.reportStats.title.isEmpty() ||
                state.reportStats.description.isEmpty() ||
                state.reportStats.latitude == 0.0 ||
                state.reportStats.longitude == 0.0
    }

    fun setReportStats(stats: ReportStats) {
        state.reportStats = stats
    }

    suspend fun createReport() {
        state.loading = true
        state.errorMessage = ""
        if (state.reportStats.title.isEmpty()) {
            state.errorMessage = "Report name is required"
            state.loading = false
            return
        }
        try {
            ReportRepo.createReport(createReport = state.reportStats)

        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
        } finally {
            if (state.errorMessage.isEmpty()) {
                state.creationSuccess = true
            }
        }
    }
}