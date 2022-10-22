package com.example.lostandfound.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.lostandfound.ui.models.Report
import com.example.lostandfound.ui.repositories.ReportRepo


class MyReportsViewModelState() {
    var _reportList = mutableStateListOf<Report>()
    val reportList: List<Report> get() = _reportList


    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
}

class MyReportsViewModel(application: Application) : AndroidViewModel(application) {
    var state = MyReportsViewModelState()
    suspend fun getReport() {
        state.loading = true
        state.errorMessage = ""
        try {
            val reports = ReportRepo.getMyReports()
            state._reportList.clear()
            state._reportList.addAll(reports)
        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
        } finally {
            state.loading = false
        }
    }
}