package com.ross.theinterceptor

class InterceptorDebugConfiguration {
    /**
     * limit of records that can be stored
     */
    var limitOfRecords: Int = InterceptorDebugStorage.DEFAULT_CACHE_LIMIT
    var jsonByteLimit: Long = InterceptorDebugStorage.MAX_JSON_BYTES_ALLOWED
        set(value) {
            field = if (value > InterceptorDebugStorage.MAX_JSON_BYTES_ALLOWED) {
                InterceptorDebugStorage.MAX_JSON_BYTES_ALLOWED
            } else {
                value
            }
        }
}