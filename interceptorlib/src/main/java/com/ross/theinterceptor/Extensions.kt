package com.ross.theinterceptor

fun createInterceptorDebugger(
    preferencesProvider: InterceptorDebuggerPreferencesProvider,
    builder: InterceptorDebugger.Builder.() -> Unit
): InterceptorDebugger {
    val interceptorBuilder = InterceptorDebugger.Builder(preferencesProvider)
    builder(interceptorBuilder)
    return interceptorBuilder.build()
}