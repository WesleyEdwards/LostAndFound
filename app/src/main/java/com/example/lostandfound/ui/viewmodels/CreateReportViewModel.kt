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

class CreateReportScreenState {

    var reportStats: ReportStats by mutableStateOf(ReportStats())
    var initialStats: ReportStats by mutableStateOf(ReportStats())

    var loading by mutableStateOf(false)
    var loadingImage by mutableStateOf(false)
    var creationSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    var file: File = File.createTempFile("temp", "jpg")
    var bitmap: Bitmap? = null

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

    fun setReportStats(stats: ReportStats, image: Uri? = null) {
        state.reportStats = stats
        image?.let {
            uploadImage(it)
        }
    }

    private fun uploadImage(uri: Uri): StorageTask<UploadTask.TaskSnapshot> {
        state.loadingImage = true
        return ImagesRepo.uploadImage(uri).addOnCompleteListener {
            state.loadingImage = false
            if (it.isSuccessful) {
                state.reportStats = state.reportStats.copy(image = it.result.storage.name)
                fetchImage()
            } else {
                state.errorMessage = it.exception?.message ?: "Unknown error"
            }
        }
    }

    private fun fetchImage() {
        println("getting Image ${state.reportStats.image}")
        state.loadingImage = true
        ImagesRepo.getImagesRef(imageId = state.reportStats.image, state.file)
            .addOnSuccessListener {
                state.bitmap = BitmapFactory.decodeFile(state.file.absolutePath)
                state.loadingImage = false
            }
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