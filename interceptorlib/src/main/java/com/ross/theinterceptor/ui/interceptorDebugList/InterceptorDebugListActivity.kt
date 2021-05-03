package com.ross.theinterceptor.ui.interceptorDebugList

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ross.theinterceptor.DebugInfo
import com.ross.theinterceptor.InterceptorDebugBaseActivity
import com.ross.theinterceptor.R
import com.ross.theinterceptor.databinding.ActivityInterceptorDebuggerBinding
import com.ross.theinterceptor.ui.interceptorDebugDetail.InterceptorDebugDetailActivity
import com.ross.theinterceptor.ui.interceptorDebugList.adapter.DebugInfoAdapter

class InterceptorDebugListActivity : InterceptorDebugBaseActivity() {

    private val adapter by lazy { DebugInfoAdapter(::onDebugItemClick) }
    private val viewModel by viewModels<InterceptorDebugListViewModel> { InterceptorDebugListViewModelFactory() }

    private lateinit var binding: ActivityInterceptorDebuggerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterceptorDebuggerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        performHapticFeedback()
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        setupRecyclerView()
        observeViewModel()
        setupMenuAction()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.interceptor_debug_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupMenuAction() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            return@setOnMenuItemClickListener when (item.itemId) {
                R.id.delete -> {
                    viewModel.clearCache()
                    true
                }
                else -> false
            }
        }
    }

    private fun performHapticFeedback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.performHapticFeedback(
                HapticFeedbackConstants.CONFIRM,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )
        } else {
            window.decorView.performHapticFeedback(
                HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            )
        }
    }

    private fun onDebugItemClick(debugInfo: DebugInfo) {
        InterceptorDebugDetailActivity.start(this, debugInfo)
    }

    private fun observeViewModel() {
        viewModel.requests.observe(this, { adapter.items = it })
        viewModel.loading.observe(this, { binding.progress.isVisible = it })
    }

    private fun setupRecyclerView() {
        binding.interceptorDebugRequests.adapter = adapter
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, InterceptorDebugListActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(starter)
        }
    }
}