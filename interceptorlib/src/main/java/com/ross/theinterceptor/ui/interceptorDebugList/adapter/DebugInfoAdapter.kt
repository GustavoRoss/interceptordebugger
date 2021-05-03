package com.ross.theinterceptor.ui.interceptorDebugList.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ross.theinterceptor.DebugInfo

class DebugInfoAdapter(private val onDebugItemClick: (DebugInfo) -> Unit) : RecyclerView.Adapter<DebugViewHolder>() {
    var items: List<DebugInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DebugViewHolder.inflate(parent)

    override fun onBindViewHolder(holder: DebugViewHolder, position: Int) = holder(items[position], onDebugItemClick)
    override fun getItemCount() = items.count()
}