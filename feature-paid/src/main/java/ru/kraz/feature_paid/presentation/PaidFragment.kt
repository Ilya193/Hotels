package ru.kraz.feature_paid.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.common.presentation.BaseFragment
import ru.kraz.feature_paid.R
import ru.kraz.feature_paid.databinding.FragmentPaidBinding

class PaidFragment : BaseFragment<FragmentPaidBinding>() {

    private val viewModel: PaidViewModel by viewModel()

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentPaidBinding =
        FragmentPaidBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentResult(true, resources.getString(R.string.paid))
        binding.infoOrder.text =
            String.format(resources.getString(R.string.information), (0..10000).random())
        binding.btnGreat.setOnClickListener {
            viewModel.openHotel()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.coup()
    }
}