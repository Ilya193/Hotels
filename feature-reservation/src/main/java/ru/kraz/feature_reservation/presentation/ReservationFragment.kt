package ru.kraz.feature_reservation.presentation

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.size
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.common.presentation.BaseFragment
import ru.kraz.feature_reservation.R
import ru.kraz.feature_reservation.databinding.AddTouristLayoutBinding
import ru.kraz.feature_reservation.databinding.FragmentReservationBinding
import ru.kraz.feature_reservation.databinding.TouristLayoutBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class ReservationFragment : BaseFragment<FragmentReservationBinding>() {
    private val viewModel: ReservationViewModel by viewModel()

    private fun validateEmail(text: String) = Patterns.EMAIL_ADDRESS.matcher(text).matches()
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentReservationBinding =
        FragmentReservationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentResult(true, resources.getString(R.string.reservation))
        settingForEditText()
        settingClickListener()
        settingViewModel()
    }

    private fun settingClickListener() {
        binding.icAddTourist.setOnClickListener { viewModel.add() }
        binding.btnRetry.setOnClickListener { viewModel.fetchInfoHotel() }
        binding.btnPay.setOnClickListener {
            var error = false
            var phoneError = false
            if (binding.etPhone.text.toString().length < 18) {
                phoneError = true
                binding.numberPhoneTextField.error = resources.getString(R.string.field_not_filled)
            } else binding.numberPhoneTextField.error = null
            var emailError = false
            if (!validateEmail(binding.etEmail.text.toString())) {
                emailError = true
                binding.emailTextField.error = resources.getString(R.string.field_not_filled)
            } else binding.emailTextField.error = null

            var errorTourists = false
            val containerTourists =
                binding.containerTourists.findViewById<LinearLayout>(R.id.containerTourists)
            for (tourist in containerTourists.children) {
                val container = tourist.findViewById<LinearLayout>(R.id.container)
                container?.let {
                    for (item in container.children) {
                        val inputLayout = item as TextInputLayout
                        val editText = inputLayout.editText
                        if (editText?.text.toString().isEmpty()) {
                            errorTourists = true
                            inputLayout.error = resources.getString(R.string.field_not_filled)
                        } else inputLayout.error = null
                    }
                }
            }

            if (phoneError || emailError || errorTourists) error = true

            if (error) Snackbar.make(
                binding.etPhone, getString(
                    R.string.fields_are_empty
                ), Snackbar.LENGTH_SHORT
            ).show()
            else viewModel.openPaid()
        }
    }

    private fun settingForEditText() {
        val mask = MaskFormatWatcher(
            MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        )
        mask.installOn(binding.etPhone)

        binding.etPhone.setOnFocusChangeListener { _, hasFocus ->
            val text = binding.etPhone.text.toString()
            if (!hasFocus && text.length < 18) {
                binding.numberPhoneTextField.error = resources.getString(R.string.field_not_filled)
            } else binding.numberPhoneTextField.error = null
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            val text = binding.etEmail.text.toString()
            if (!hasFocus && (text.isEmpty() || !validateEmail(text))) {
                binding.emailTextField.error = getString(R.string.error_email)
            } else binding.emailTextField.error = null
        }
    }

    private fun createTouristLayout(which: String, visibility: Int = View.GONE) {
        val touristLayout = TouristLayoutBinding.inflate(layoutInflater)
        touristLayout.tvInfo.text = which + " " + resources.getString(R.string.tourist)
        touristLayout.container.visibility = visibility
        val icon = if (visibility == View.GONE) R.drawable.ic_expand_more else R.drawable.ic_expand_less
        touristLayout.icExpand.setImageResource(icon)
        touristLayout.icExpand.setOnClickListener {
            with(touristLayout) {
                val state = if (container.visibility == View.GONE) View.VISIBLE else View.GONE
                val icon =
                    if (state == View.VISIBLE) R.drawable.ic_expand_less else R.drawable.ic_expand_more
                container.visibility = state
                icExpand.setImageResource(icon)
            }
        }

        val container = touristLayout.root.findViewById<LinearLayout>(R.id.container)
        container?.let {
            for (item in container.children) {
                val inputLayout = item as TextInputLayout
                val editText = inputLayout.editText
                editText?.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus && editText.text.toString().isEmpty())
                        inputLayout.error = resources.getString(R.string.field_not_filled)
                    else inputLayout.error = null
                }
            }
        }

        binding.containerTourists.addView(touristLayout.root)
    }

    private fun settingViewModel() {
        viewModel.fetchInfoHotel()
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.containerError.visibility = if (it is HotelUiState.Error) View.VISIBLE else View.GONE
            binding.tvError.text = if (it is HotelUiState.Error) it.msg else ""
            binding.loading.visibility = if (it is HotelUiState.Loading) View.VISIBLE else View.GONE
            binding.mainContainer.visibility = if (it is HotelUiState.Success) View.VISIBLE else View.GONE

            if (it is HotelUiState.Success) {
                binding.tvRating.text = it.data.horating.toString() + " " + it.data.ratingName
                binding.tvName.text = it.data.hotelName
                binding.btnAddress.text = it.data.hotelAddress
                binding.tvFrom.text = it.data.departure
                binding.tvCountry.text = it.data.arrivalCountry
                binding.tvDate.text = it.data.tourDataStartToStop
                binding.tvCountOfNight.text = it.data.numberOfNights.toString()
                binding.tvNameHotel.text = it.data.hotelName
                binding.tvRoom.text = it.data.room
                binding.tvNutrition.text = it.data.nutrition
                binding.tvTour.text = it.data.tourPrice.toString() + " ₽"
                binding.tvFuelSurcharge.text = it.data.fuelCharge.toString() + " ₽"
                binding.tvServiceFee.text = it.data.serviceCharge.toString() + " ₽"
                val pay = it.data.toPay.toString()
                binding.tvToPay.text = pay + " ₽"
                binding.btnPay.text = binding.btnPay.text.toString() + " " + pay + " ₽"
            }
        }
        viewModel.tourists.observe(viewLifecycleOwner) {
            it.getContentOrNot { tourists ->
                when (tourists) {
                    is TouristsState.Initial -> {
                        binding.containerTourists.removeAllViews()
                        for (index in 0 .. tourists.count) {
                            val which = resources.getStringArray(R.array.tourists)[index]
                            createTouristLayout(which, if (index == 0) View.VISIBLE else View.GONE)
                        }
                    }
                    is TouristsState.Add -> {
                        val which = resources.getStringArray(R.array.tourists)[tourists.count]
                        createTouristLayout(which)
                    }
                }

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.coup()
    }
}