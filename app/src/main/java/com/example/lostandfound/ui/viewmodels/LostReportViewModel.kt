package com.example.lostandfound.ui.viewmodels


import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.lostandfound.ui.models.Report
import com.example.lostandfound.ui.repositories.ImagesRepo
import com.example.lostandfound.ui.repositories.ReportRepo
import com.example.lostandfound.ui.repositories.UserRepo
import java.io.File

class LostReportScreenState() {
    var _report by mutableStateOf<Report?>(null)
    val report: Report? get() = _report

    var confirmDelete by mutableStateOf(false)
    var myReport by mutableStateOf(false)
    var loadingImage by mutableStateOf(false)

    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")


    var file: File = File.createTempFile("temp", "jpg")
    var bitmap: Bitmap? = null
}

class LostReportViewModel(application: Application) : AndroidViewModel(application) {
    var state = LostReportScreenState()


    private fun fetchImage(imageId: String) {
        if (imageId == "") return
        state.loadingImage = true
        ImagesRepo.getImagesRef(imageId, state.file)
            .addOnSuccessListener {
                state.bitmap = BitmapFactory.decodeFile(state.file.absolutePath)
                state.loadingImage = false
            }
    }

    suspend fun getReport(reportId: String) {

        state.loading = true
        state.errorMessage = ""
        state._report = null
        try {
            val report = ReportRepo.getReport(reportId)
            report?.reportStats?.image?.let {
                fetchImage(it)
            }
            state._report = report
            state.myReport = report?.userId == UserRepo.getUser()?.uid

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