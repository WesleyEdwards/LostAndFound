package com.example.lostandfound.ui.models

enum class ReportStatus {
    LOST,
    FOUND
}

data class Report(
    val _id: String,
    val reportStats: ReportStats,
    val userId: String
)

data class ReportStats(
    val title: String = "Default Title",
    val description: String = "no description",
    val location: String = "No Location",
    val date: String = "Today",
    val image: String = "No image",
    val status: ReportStatus = ReportStatus.LOST
)
