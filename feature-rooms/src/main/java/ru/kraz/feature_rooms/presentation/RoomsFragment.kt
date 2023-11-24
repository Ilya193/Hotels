package ru.kraz.feature_rooms.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.common.presentation.BaseFragment
import ru.kraz.feature_rooms.databinding.FragmentRoomsBinding

class RoomsFragment : BaseFragment<FragmentRoomsBinding>() {

    private val viewModel: RoomsViewModel by viewModel()
    private var title = ""

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentRoomsBinding =
        FragmentRoomsBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(TOOLBAR_TITLE_KEY, "") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResult(ACTION_BACK_REQUEST_KEY, bundleOf(ACTION_BACK_KEY to true))
        setFragmentResult(
            TOOLBAR_TITLE_REQUEST_KEY,
            bundleOf(
                TOOLBAR_TITLE_KEY to
                        title
            )
        )
        settingViewModel()
        settingListeners()
    }

    private fun settingViewModel() {
        viewModel.fetchRooms()
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.containerError.visibility = if (it is RoomUiState.Error) View.VISIBLE else View.GONE
            binding.tvError.text = if (it is RoomUiState.Error) it.msg else ""
            binding.loading.visibility = if (it is RoomUiState.Loading) View.VISIBLE else View.GONE
            binding.rvRooms.visibility = if (it is RoomUiState.Success) View.VISIBLE else View.GONE

            if (it is RoomUiState.Success) {
                val roomsAdapter = RoomsAdapter(requireContext()) {
                    viewModel.openReservation()
                }
                binding.rvRooms.adapter = roomsAdapter
                binding.rvRooms.setHasFixedSize(true)
                roomsAdapter.submitList(it.rooms)
            }
        }
    }

    private fun settingListeners() {
        binding.btnRetry.setOnClickListener {
            viewModel.fetchRooms()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.coup()
    }

    companion object {
        fun newInstance(title: String) =
            RoomsFragment().apply {
                arguments = Bundle().apply {
                    putString(TOOLBAR_TITLE_KEY, title)
                }
            }
    }
}