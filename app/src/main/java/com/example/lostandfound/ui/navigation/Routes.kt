package com.example.lostandfound.ui.navigation

object Routes {
    const val CreateAccount = "create_account"
    const val SignIn = "sign_in"

    const val Home = "home"
    const val MyReports = "my_reports"
    const val Profile = "profile"

    const val Conversations = "conversations"

    const val CreateLostReport = "create_lost_report"

    const val LostReportView = "lost_report_view/{reportId}"
    const val LostReportEdit = "lost_report_edit/{reportId}"

    val getLostReportView = { reportId: String -> "lost_report_view/$reportId" }
    val getReportEdit = { reportId: String -> "lost_report_edit/$reportId" }
}

object GraphRoutes {
    const val Root = "root"
    const val Unauth = "unauth"
    const val Auth = "auth"
}