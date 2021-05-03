package com.ross.theinterceptor

import com.ross.theinterceptor.InterceptorDebugStorage.Companion.DEFAULT_CACHE_LIMIT

class InterceptorDebugger private constructor(
    private val interceptorStorage: InterceptorDebugStorage,
    private val configuration: InterceptorDebugConfiguration
) {

    fun interceptRequest(debugInfo: DebugInfo) {
        interceptorStorage.saveRequest(debugInfo, configuration)
    }

    class Builder(private val provider: InterceptorDebuggerPreferencesProvider) {

        private var configuration = InterceptorDebugConfiguration()

        fun setCacheLimit(cacheLimit: Int = DEFAULT_CACHE_LIMIT) {
            configuration.limitOfRecords = cacheLimit
        }

        fun build(): InterceptorDebugger {
            return InterceptorDebugger(
                InterceptorDebugStorage.getInstance(provider.provide()), configuration
            )
        }
    }
}