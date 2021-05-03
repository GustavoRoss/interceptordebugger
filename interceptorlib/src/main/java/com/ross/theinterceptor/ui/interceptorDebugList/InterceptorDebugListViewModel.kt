package com.ross.theinterceptor.ui.interceptorDebugList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ross.theinterceptor.DebugInfo
import com.ross.theinterceptor.InterceptorDebugStorage

class InterceptorDebugListViewModel(private val debugger: InterceptorDebugStorage) : ViewModel() {
    val loading: LiveData<Boolean> get() = _loading
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()

    val requests: LiveData<List<DebugInfo>> get() = _requests
    private val _requests: MutableLiveData<List<DebugInfo>> = MutableLiveData()

    init {
        getRequests()
    }

    fun clearCache() {
        debugger.clearRequests()
        _requests.postValue(debugger.getRequests())
    }

    private fun getRequests() {
        _loading.postValue(true)
        _requests.postValue(debugger.getRequests())
        _loading.postValue(false)
    }
}