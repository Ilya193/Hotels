package ru.kraz.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V : ViewBinding> : Fragment() {
    private var _binding: V? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bind(inflater, container)
        return binding.root
    }

    protected fun fragmentResult(action: Boolean, title: String) {
        setFragmentResult(ACTION_BACK_REQUEST_KEY, bundleOf(ACTION_BACK_KEY to action))
        setFragmentResult(
            TOOLBAR_TITLE_REQUEST_KEY,
            bundleOf(
                TOOLBAR_TITLE_KEY to
                        title
            )
        )
    }

    protected abstract fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): V

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ACTION_BACK_REQUEST_KEY = "ACTION_BACK_REQUEST_KEY"
        const val ACTION_BACK_KEY = "ACTION_BACK_KEY"
        const val TOOLBAR_TITLE_REQUEST_KEY = "TOOLBAR_TITLE_REQUEST_KEY"
        const val TOOLBAR_TITLE_KEY = "TOOLBAR_TITLE_KEY"
    }
}