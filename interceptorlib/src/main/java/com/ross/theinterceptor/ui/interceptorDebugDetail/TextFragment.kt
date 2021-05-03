package com.ross.theinterceptor.ui.interceptorDebugDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ross.theinterceptor.databinding.FragmentTextBinding

class TextFragment : Fragment() {

    private val content by lazy {
        arguments?.getString(TEXT_EXTRA)
    }

    private lateinit var binding: FragmentTextBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTextBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.content.text = content
    }

    companion object {
        private const val TEXT_EXTRA =
            "com.ross.theinterceptor.ui.interceptorDebugDetail.TEXT_EXTRA"

        @JvmStatic
        fun newInstance(text: String) =
            TextFragment().apply {
                arguments = Bundle().apply {
                    putString(TEXT_EXTRA, text)
                }
            }
    }
}