package com.example.lostandfound.ui.models

enum class ReportStatus {
    LOST,
    FOUND
}

data class Report(
    val _id: String = "",
    val reportStats: ReportStats = ReportStats(),
    val userId: String = ""
)

data class ReportStats(
    val title: String = "",
    val description: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val date: String = "",
    val image: String = "",
    val status: ReportStatus = ReportStatus.LOST
)
