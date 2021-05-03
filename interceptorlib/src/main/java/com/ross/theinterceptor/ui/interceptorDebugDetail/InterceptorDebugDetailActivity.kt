package com.ross.theinterceptor.ui.interceptorDebugDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.ross.theinterceptor.DebugInfo
import com.ross.theinterceptor.InterceptorDebugBaseActivity
import com.ross.theinterceptor.R
import com.ross.theinterceptor.databinding.ActivityInterceptorDebugDetailBinding
import com.ross.theinterceptor.ui.interceptorDebugDetail.adapter.RequestDetailPagerAdapter

class InterceptorDebugDetailActivity : InterceptorDebugBaseActivity() {

    private val detailExtra by lazy {
        intent.extras?.getParcelable<DebugInfo>(REQUEST_DETAIL_EXTRA)
    }

    private lateinit var binding: ActivityInterceptorDebugDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
        binding = ActivityInterceptorDebugDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        if (detailExtra != null) {
            handleRequestDetail()
        } else finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
    }

    private fun handleRequestDetail() {
        detailExtra?.run {
            val jsonRequestFragment = JsonViewerFragment.newInstance(requestBody)
            val queryRequestFragment = TextFragment.newInstance(params)
            val jsonResponseFragment = JsonViewerFragment.newInstance(responseBody)
            val headersFragment = TextFragment.newInstance(headers)
            val authFragment = TextFragment.newInstance(authentication)
            val fragments =
                listOf(
                    jsonRequestFragment,
                    queryRequestFragment,
                    jsonResponseFragment,
                    headersFragment,
                    authFragment
                )
            binding.pager.isUserInputEnabled = false
            binding.pager.offscreenPageLimit = fragments.size
            binding.pager.adapter =
                RequestDetailPagerAdapter(fragments, supportFragmentManager, lifecycle)
            setupTabMediator()
        }
    }

    private fun setupTabMediator() {
        val titles = listOf("Request Body", "Params", "Response Body", "Headers", "Authentication")
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = titles[position]
            binding.pager.setCurrentItem(tab.position, true)
        }.attach()
    }

    companion object {
        private const val REQUEST_DETAIL_EXTRA =
            "com.ross.theinterceptor.ui.interceptorDebugDetail.REQUEST_DETAIL_EXTRA"

        fun start(context: Context, detail: DebugInfo) {
            context.startActivity(
                Intent(
                    context,
                    InterceptorDebugDetailActivity::class.java
                ).apply {
                    putExtra(REQUEST_DETAIL_EXTRA, detail)
                })
        }
    }
}