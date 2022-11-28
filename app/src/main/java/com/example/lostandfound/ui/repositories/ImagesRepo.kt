package com.example.lostandfound.ui.repositories

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.*

object ImagesRepo {
    private val storageRef: StorageReference = Firebase.storage.getReference("images")


    fun getImagesRef(imageId: String, file: File): FileDownloadTask {
        return storageRef.child(imageId).getFile(file)
    }

    fun uploadImage(imageUri: Uri): UploadTask {
        val imageId = UUID.randomUUID().toString()
        return storageRef.child(imageId).putFile(imageUri)

    }
}