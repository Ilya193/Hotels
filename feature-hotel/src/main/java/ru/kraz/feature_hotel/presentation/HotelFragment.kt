package ru.kraz.feature_hotel.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.common.presentation.BaseFragment
import ru.kraz.common.presentation.SliderAdapter
import ru.kraz.feature_hotel.R
import ru.kraz.feature_hotel.databinding.FragmentHotelBinding
import kotlin.math.abs

class HotelFragment : BaseFragment<FragmentHotelBinding>() {

    private val viewModel: HotelViewModel by viewModel()
    private var title = ""

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentHotelBinding =
        FragmentHotelBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResult(ACTION_BACK_REQUEST_KEY, bundleOf(ACTION_BACK_KEY to false))
        settingViewModel()
        settingListeners()
    }

    private fun settingViewModel() {
        viewModel.fetchHotel()
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.loading.visibility = if (it is HotelUiState.Loading) View.VISIBLE else View.GONE
            binding.content.visibility = if (it is HotelUiState.Success) View.VISIBLE else View.GONE
            setFragmentResult(
                TOOLBAR_TITLE_REQUEST_KEY,
                bundleOf(
                    TOOLBAR_TITLE_KEY to
                    if (it is HotelUiState.Success) resources.getString(R.string.hotel) else ""
                )
            )
            binding.containerError.visibility =
                if (it is HotelUiState.Error) View.VISIBLE else View.GONE
            binding.tvError.text = if (it is HotelUiState.Error) it.msg else ""

            if (it is HotelUiState.Success) {
                settingSlider(it.hotel)
                binding.ratingContainer.visibility = View.VISIBLE
                binding.tvAboutHotel.visibility = View.VISIBLE
                binding.btnSelectNum.visibility = View.VISIBLE
                binding.tvRating.text = it.hotel.rating.toString() + " " + it.hotel.ratingName
                binding.tvName.text = it.hotel.name
                binding.btnAddress.text = it.hotel.address

                title = it.hotel.name

                var mapValue = ""
                it.hotel.minimalPrice.toString().forEachIndexed { index, c ->
                    if (index % 3 == 0) mapValue += " "
                    mapValue += c
                }

                binding.tvMinimalPrice.text =
                    resources.getString(R.string.from) + mapValue + " ₽ "
                binding.tvPriceForIt.text = it.hotel.priceForIt.lowercase()

                binding.peculiarities.text = ""
                it.hotel.peculiarities.forEachIndexed { index, str ->
                    if (index != 0) binding.peculiarities.text =
                        binding.peculiarities.text.toString() + "\t\t\t\t" + str
                    else binding.peculiarities.text = binding.peculiarities.text.toString() + str
                }

                binding.tvDescription.text = it.hotel.description
                settingAdvatages()
            }
        }
    }

    private fun settingListeners() {
        binding.btnRetry.setOnClickListener {
            viewModel.fetchHotel()
        }

        binding.btnSelectNum.setOnClickListener {
            viewModel.openRoom(title)
        }
    }

    private fun settingAdvatages() {
        val list = listOf<AdvantageUi>(
            AdvantageUi(
                R.drawable.ic_happy,
                resources.getString(R.string.facilities),
                resources.getString(R.string.essentials)
            ),
            AdvantageUi(
                R.drawable.ic_include,
                resources.getString(R.string.include),
                resources.getString(R.string.essentials)
            ),
            AdvantageUi(
                R.drawable.ic_not_include,
                resources.getString(R.string.not_include),
                resources.getString(R.string.essentials)
            )
        )
        val adapter = AdvantagesAdapter(list)
        binding.rvAdvantages.adapter = adapter
        binding.rvAdvantages.setHasFixedSize(true)
    }

    private fun settingSlider(hotel: HotelUi.Success) {
        val adapter = SliderAdapter(hotel.imageUrls)
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)

        binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val transform = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(30))
            addTransformer { page, position ->
                page.scaleY = (0.85 + (1 - abs(position)) * 0.15f).toFloat()
            }
        }

        binding.viewPager.setPageTransformer(transform)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.coup()
    }
}