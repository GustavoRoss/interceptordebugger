package com.ross.theinterceptor.ui.interceptorDebugDetail

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel
import com.ross.theinterceptor.DebugInfo

class InterceptorDebugDetailViewModel(private val context: Application) :
    AndroidViewModel(context) {

    fun addBugReportToClipBoard(debugInfo: DebugInfo) {
        val log = buildString {
            append("------------------- Request Info -----------------------")
            appendLine()
            appendLine()
            append("HTTP METHOD: ")
            append(debugInfo.method.ifEmpty { "-" })
            append("\n")
            append("URL: ")
            append(debugInfo.url.ifEmpty { "-" })
            append("\n")
            append("RESPONSE CODE: ")
            append(debugInfo.responseCode)
            append("\n")
            append("REQUEST DATE: ")
            append(debugInfo.date.ifEmpty { "-" })
            appendLine()
            appendLine()
            append("--------------------------------------------------------")
            appendLine()
            appendLine()
            append("------------------- Headers ----------------------------")
            appendLine()
            appendLine()
            append(debugInfo.headers.ifEmpty { "REQUEST WITHOUT HEADERS" })
            appendLine()
            appendLine()
            append("--------------------------------------------------------")
            appendLine()
            appendLine()
            append("------------------- Query Parameters -------------------")
            appendLine()
            appendLine()
            append(debugInfo.params.ifEmpty { "REQUEST WITHOUT QUERY PARAMETERS" })
            appendLine()
            appendLine()
            append("--------------------------------------------------------")
            appendLine()
            appendLine()
            appendLine()
            appendLine()
            append("------------------- Request Body -----------------------")
            appendLine()
            appendLine()
            append(debugInfo.requestBody.ifEmpty { "REQUEST WITHOUT REQUEST BODY" })
            appendLine()
            appendLine()
            append("--------------------------------------------------------")
            appendLine()
            appendLine()
            appendLine()
            appendLine()
            append("------------------- Response Body ----------------------")
            appendLine()
            appendLine()
            append(debugInfo.responseBody.ifEmpty { "REQUEST WITHOUT RESPONSE BODY" })
            appendLine()
            appendLine()
            append("--------------------------------------------------------")
            appendLine()
            appendLine()
            appendLine()
            appendLine()
            append("------------------- Authentication ---------------------")
            appendLine()
            appendLine()
            append(debugInfo.authentication.ifEmpty { "REQUEST WITHOUT AUTHENTICATION" })
            appendLine()
            appendLine()
            append("--------------------------------------------------------")
        }

        val clipManager = context.getSystemService<ClipboardManager>()
        clipManager?.setPrimaryClip(ClipData.newPlainText("Log Details", log))
    }
}