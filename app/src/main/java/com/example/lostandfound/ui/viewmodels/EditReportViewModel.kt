package com.example.lostandfound.ui.viewmodels


import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.lostandfound.ui.models.Report
import com.example.lostandfound.ui.models.ReportStats
import com.example.lostandfound.ui.repositories.ImagesRepo
import com.example.lostandfound.ui.repositories.ReportRepo
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.io.File

class EditReportScreenState {

    var loading by mutableStateOf(false)
    var creationSuccess by mutableStateOf(false)
    var loadingImage by mutableStateOf(true)
    var errorMessage by mutableStateOf("")
    var initialReport by mutableStateOf<Report?>(null)
    var image by mutableStateOf<Bitmap?>(null)

    var file: File = File.createTempFile("temp", "jpg")
    var imageBitmap: Bitmap? = null

}

class EditReportViewModel(application: Application) : AndroidViewModel(application) {
    var state = EditReportScreenState()


    suspend fun getReport(reportId: String) {
        val report = ReportRepo.getReport(reportId)
        state.initialReport = report
        fetchImage(report?.reportStats?.image)

    }

    private fun fetchImage(image: String?) {
        if (image == null || image == "") {
            state.loadingImage = false
            return
        }
        state.loadingImage = true
        ImagesRepo.getImagesRef(imageId = image, state.file)
            .addOnSuccessListener {
                println("Image fetched")
                state.imageBitmap = BitmapFactory.decodeFile(state.file.absolutePath)
                state.loadingImage = false
            }
    }

    suspend fun editReport(reportStats: ReportStats) {
        if (state.initialReport == null) return
        state.loading = true
        state.errorMessage = ""
        try {
            ReportRepo.updateReport(
                state.initialReport!!.copy(reportStats = reportStats)
            )

        } catch (e: Exception) {
            state.errorMessage = e.message ?: "Unknown error"
        } finally {
            if (state.errorMessage.isEmpty()) {
                state.creationSuccess = true
            }
        }
    }
}