package com.ross.theinterceptor

import com.ross.theinterceptor.InterceptorDebugStorage.Companion.DEFAULT_CACHE_LIMIT
import com.ross.theinterceptor.InterceptorDebugStorage.Companion.MAX_JSON_BYTES_ALLOWED

class InterceptorDebugger private constructor(
    private val interceptorStorage: InterceptorDebugStorage,
    private val configuration: InterceptorDebugConfiguration
) {

    /**
     * If the json size is greater than [InterceptorDebugConfiguration].jsonByteLimit or [InterceptorDebugStorage.MAX_JSON_BYTES_ALLOWED]
     * the json will not be stored, otherwise it can throw a [OutOfMemoryError] Exception.
     */
    fun interceptRequest(debugInfo: DebugInfo) {
        val requestJsonBytes = debugInfo.requestBody.toByteArray()
        val responseJsonBytes = debugInfo.responseBody.toByteArray()

        if (requestJsonBytes.size >= configuration.jsonByteLimit) {
            debugInfo.requestBody = "JSON SIZE ALLOWED EXCEEDED"
        }

        if (responseJsonBytes.size >= configuration.jsonByteLimit) {
            debugInfo.responseBody = "JSON SIZE ALLOWED EXCEEDED"
        }

        interceptorStorage.saveRequest(debugInfo, configuration)
    }

    class Builder(private val provider: InterceptorDebuggerPreferencesProvider) {

        private var configuration = InterceptorDebugConfiguration()

        fun setCacheLimit(cacheLimit: Int = DEFAULT_CACHE_LIMIT) {
            configuration.limitOfRecords = cacheLimit
        }

        /**
         * Values greater than [InterceptorDebugStorage.MAX_JSON_BYTES_ALLOWED] will not be allowed
         */
        fun setJsonMaxBytesAllowed(bytesLimit: Long = MAX_JSON_BYTES_ALLOWED) {
            configuration.jsonByteLimit = bytesLimit
        }

        fun build(): InterceptorDebugger {
            return InterceptorDebugger(
                InterceptorDebugStorage.getInstance(provider.provide()),
                configuration
            )
        }
    }
}