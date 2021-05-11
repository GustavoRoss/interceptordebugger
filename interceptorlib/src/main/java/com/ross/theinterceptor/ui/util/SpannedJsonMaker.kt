package com.ross.theinterceptor.ui.util

import android.graphics.Color
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import androidx.core.text.buildSpannedString
import com.binaryfork.spanny.Spanny
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SpannedJsonMaker {

    companion object {
        val nullRegex = "( null,)".toRegex()
        val numberRegex = "([ 0-9.]+,)".toRegex()
        val boolRegex = "( true,| false,)".toRegex()
    }

    fun make(json: String, indentSpaces: Int): SpannedString {
        if (json.isEmpty()) return SpannedString("Emtpy")

        val startIsObject = json.startsWith("{")

        val jsonLines = if (startIsObject) {
            try {
                JSONObject(json).toString(indentSpaces).splitToSequence("\n")
            } catch (e: JSONException) {
                return SpannedString("Error parsing this json: ${e.message}\n\n\n $json")
            }
        } else {
            try {
                JSONArray(json).toString(indentSpaces).splitToSequence("\n")
            } catch (e: JSONException) {
                return SpannedString("Error parsing this json: ${e.message}\n\n\n $json")
            }
        }

        return buildSpannedString {
            jsonLines.forEach { line ->
                val splitJson = line.split(":", limit = 2)
                val key = splitJson.first()
                val value = splitJson.last()

                val lineSpanny = Spanny(line)
                lineSpanny.findAndSpan(key) { ForegroundColorSpan(Color.parseColor("#b37400")) }
                lineSpanny.findAndSpan(value) {
                    when {
                        value.matches(boolRegex) -> ForegroundColorSpan(Color.parseColor("#0080ff"))
                        value.matches(numberRegex) -> ForegroundColorSpan(Color.parseColor("#00b300"))
                        value.matches(nullRegex) -> ForegroundColorSpan(Color.RED)
                        else -> ForegroundColorSpan(Color.parseColor("#a6a6a6"))
                    }
                }
                append(lineSpanny)
                append("\n")
            }
        }
    }
}