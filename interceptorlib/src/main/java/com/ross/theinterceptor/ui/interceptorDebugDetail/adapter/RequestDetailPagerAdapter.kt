package com.ross.theinterceptor.ui.interceptorDebugDetail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class RequestDetailPagerAdapter(
    private val items: List<Fragment>,
    manager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount() = items.count()
    override fun createFragment(position: Int) = items[position]
}