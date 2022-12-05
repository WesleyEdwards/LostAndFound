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

//        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream)
//        val reducedImage: ByteArray = byteArrayOutputStream.toByteArray()
//
//        storageRef.child(imageId).putBytes(reducedImage)

    }
}