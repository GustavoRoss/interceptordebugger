package com.ross.theinterceptor

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException

class InterceptorDebugStorage(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val REQUESTS_INTERCEPTOR_KEY = "REQUESTS_INTERCEPTOR_KEY"
        const val DEFAULT_CACHE_LIMIT = 100

        @JvmStatic
        var INSTANCE: InterceptorDebugStorage? = null

        @JvmStatic
        fun getInstance(sharedPreferences: SharedPreferences): InterceptorDebugStorage {
            if (INSTANCE == null) {
                INSTANCE = InterceptorDebugStorage(sharedPreferences)
            }
            return INSTANCE!!
        }
    }

    private val moshi = Moshi.Builder().build()

    private val debugInfoAdapter by lazy {
        val type =
            Types.newParameterizedType(MutableList::class.java, DebugInfo::class.java)

        moshi.adapter<MutableList<DebugInfo>>(type)
    }

    fun saveRequest(debugInfo: DebugInfo, configuration: InterceptorDebugConfiguration) {
        val storedRequestsJson = sharedPreferences.getString(REQUESTS_INTERCEPTOR_KEY, null)
        val requests = if (storedRequestsJson != null) {
            try {
                val records = debugInfoAdapter.fromJson(storedRequestsJson)!!
                if (records.size >= configuration.limitOfRecords) {
                    records.clear()
                }
                records
            } catch (e: IOException) {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }

        requests.add(debugInfo)
        sharedPreferences.edit {
            putString(REQUESTS_INTERCEPTOR_KEY, debugInfoAdapter.toJson(requests))
        }
    }

    fun clearRequests() {
        val storedJson = sharedPreferences.getString(REQUESTS_INTERCEPTOR_KEY, null)
        val requests = if (storedJson != null) {
            try {
                val records = debugInfoAdapter.fromJson(storedJson)!!
                records.clear()
                records
            } catch (e: IOException) {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }

        sharedPreferences.edit {
            putString(REQUESTS_INTERCEPTOR_KEY, debugInfoAdapter.toJson(requests))
        }
    }

    fun getRequests(): MutableList<DebugInfo> {
        val storedRequestsJson = sharedPreferences.getString(REQUESTS_INTERCEPTOR_KEY, null)
        return if (storedRequestsJson != null) {
            try {
                debugInfoAdapter.fromJson(storedRequestsJson)!!
            } catch (e: IOException) {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    }
}