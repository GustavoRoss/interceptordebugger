package com.ross.theinterceptor.ui.interceptorDebugList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ross.theinterceptor.InterceptorDebugStorage

@Suppress("UNCHECKED_CAST")
class InterceptorDebugListViewModelFactory : ViewModelProvider.Factory {

    private val storage = InterceptorDebugStorage.INSTANCE

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (storage == null) {
            throw RuntimeException("No Instance for InterceptorDebugStorage, use createInterceptorDebugger extension function helper")
        }
        return InterceptorDebugListViewModel(storage) as T
    }
}