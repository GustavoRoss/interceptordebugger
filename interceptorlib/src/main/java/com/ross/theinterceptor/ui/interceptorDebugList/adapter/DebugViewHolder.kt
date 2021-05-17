package com.ross.theinterceptor.ui.interceptorDebugList.adapter

import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ross.theinterceptor.DebugInfo
import com.ross.theinterceptor.R
import com.ross.theinterceptor.databinding.ItemInterceptorDebugInfoBinding
import com.ross.theinterceptor.util.Spanny

class DebugViewHolder(private val binding: ItemInterceptorDebugInfoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun inflate(parent: ViewGroup) = DebugViewHolder(
            ItemInterceptorDebugInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    operator fun invoke(debugInfo: DebugInfo, onDebugItemClick: (DebugInfo) -> Unit) {
        binding.code.text = debugInfo.responseCode.toString()
        binding.date.text = debugInfo.date
        binding.method.text = debugInfo.method
        binding.root.setOnClickListener {
            onDebugItemClick(debugInfo)
        }
        resolveHttpMethodColor(debugInfo)
        applyLinkAppearanceToHttpUrl(debugInfo)
    }

    private fun applyLinkAppearanceToHttpUrl(debugInfo: DebugInfo) {
        binding.url.text = Spanny(
            debugInfo.url,
            UnderlineSpan(),
            ForegroundColorSpan(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.post_method_color
                )
            )
        )
    }

    private fun resolveHttpMethodColor(debugInfo: DebugInfo) {
        val color = ContextCompat.getColor(
            binding.root.context, when {
                debugInfo.method.equals("GET", true) -> R.color.get_method_color
                debugInfo.method.equals("POST", true) -> R.color.post_method_color
                debugInfo.method.equals("PUT", true) -> R.color.put_method_color
                debugInfo.method.equals("DELETE", true) -> R.color.delete_method_color
                debugInfo.method.equals("PATCH", true) -> R.color.patch_method_color
                else -> R.color.defaultTextColor
            }
        )
        binding.method.setTextColor(color)
    }
}