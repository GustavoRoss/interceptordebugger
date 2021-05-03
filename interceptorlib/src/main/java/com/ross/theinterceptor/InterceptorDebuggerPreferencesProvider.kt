package com.ross.theinterceptor

import android.content.SharedPreferences

interface InterceptorDebuggerPreferencesProvider {
    fun provide(): SharedPreferences
}