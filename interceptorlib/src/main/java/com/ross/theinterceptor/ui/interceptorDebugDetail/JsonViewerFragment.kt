package com.ross.theinterceptor.ui.interceptorDebugDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ross.theinterceptor.databinding.FragmentJsonViewerBinding
import com.ross.theinterceptor.ui.util.SpannedJsonMaker

class JsonViewerFragment : Fragment() {

    private val spannedJsonMaker by lazy { SpannedJsonMaker() }

    private val json by lazy {
        arguments?.getString(JSON_EXTRA)
    }

    private lateinit var binding: FragmentJsonViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentJsonViewerBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        json?.also {
            binding.jsonBody.text = spannedJsonMaker.make(it, 4)
            setupLineCount()
        }
    }

    private fun setupLineCount() {
        binding.jsonBody.viewTreeObserver.addOnGlobalLayoutListener {
            val lines = StringBuilder()
            repeat(binding.jsonBody.lineCount) { lineNumber ->
                lines.append("${lineNumber}\n")
            }
            binding.lines.text = lines
        }
    }

    companion object {

        private const val JSON_EXTRA = "com.ross.theinterceptor.ui.interceptorDebugDetail.JSON_EXTRA"

        @JvmStatic
        fun newInstance(json: String) =
            JsonViewerFragment().apply {
                arguments = Bundle().apply {
                    putString(JSON_EXTRA, json)
                }
            }
    }
}