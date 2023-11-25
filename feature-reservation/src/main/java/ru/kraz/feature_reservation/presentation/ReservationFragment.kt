package ru.kraz.feature_reservation.presentation

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.setFragmentResult
import com.elveum.elementadapter.adapter
import com.elveum.elementadapter.addBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.common.presentation.BaseFragment
import ru.kraz.feature_reservation.R
import ru.kraz.feature_reservation.databinding.AddTouristLayoutBinding
import ru.kraz.feature_reservation.databinding.FragmentReservationBinding
import ru.kraz.feature_reservation.databinding.InfoBuyerItemBinding
import ru.kraz.feature_reservation.databinding.InfoCommonItemBinding
import ru.kraz.feature_reservation.databinding.InfoHotelItemBinding
import ru.kraz.feature_reservation.databinding.TouristLayoutBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class ReservationFragment : BaseFragment<FragmentReservationBinding>() {
    private val viewModel: ReservationViewModel by viewModel()

    private fun validateEmail(text: String) = Patterns.EMAIL_ADDRESS.matcher(text).matches()

    private val adapter = adapter<HotelUi> {
        addBinding<HotelUi.InfoHotel, InfoHotelItemBinding> {
            bind { hotel ->
                tvRating.text = hotel.horating.toString() + " " + hotel.ratingName
                tvName.text = hotel.hotelName
                btnAddress.text = hotel.hotelAddress
                tvFrom.text = hotel.departure
                tvCountry.text = hotel.arrivalCountry
                tvDate.text = hotel.tourDataStartToStop
                tvCountOfNight.text = hotel.numberOfNights.toString()
                tvNameHotel.text = hotel.hotelName
                tvRoom.text = hotel.room
                tvNutrition.text = hotel.nutrition
            }
        }

        addBinding<HotelUi.InfoBuyer, InfoBuyerItemBinding> {
            bind {
                val mask = MaskFormatWatcher(
                    MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
                )
                mask.installOn(etPhone)

                if (it.searchError) {
                    if (etPhone.text.isEmpty()) {
                        numberPhoneTextField.error = "ошибка"
                        //viewModel.searchError(Action.Error)
                    }
                    if (etEmail.text.toString().isEmpty()) {
                        emailTextField.error = "ошибка"
                        //viewModel.searchError(Action.Error)
                    }
                }
                else {
                    numberPhoneTextField.error = null
                    emailTextField.error = null
                }

                etPhone.setOnFocusChangeListener { _, hasFocus ->
                    val text = etPhone.text.toString()
                    if (!hasFocus && text.length < 16) numberPhoneTextField.error =
                        getString(R.string.field_not_filled)
                    else numberPhoneTextField.error = null
                }

                etEmail.setOnFocusChangeListener { _, hasFocus ->
                    val text = etEmail.text.toString()
                    if (!hasFocus && (text.isEmpty() || !validateEmail(text))) emailTextField.error =
                        getString(R.string.error_email)
                    else emailTextField.error = null
                }
            }
        }

        addBinding<HotelUi.Tourist, TouristLayoutBinding> {
            bind {
                val which = resources.getStringArray(R.array.tourists)[it.which]
                tvInfo.text = which + " " + resources.getString(R.string.tourist)
                val visibility = if (it.isHidden) View.GONE else View.VISIBLE
                val icon = if (it.isHidden) R.drawable.ic_expand_more else R.drawable.ic_expand_less
                container.visibility = visibility
                icExpand.setImageResource(icon)

                for (item in container.children) {
                    val inputLayout = item as TextInputLayout
                    val editText = inputLayout.editText
                    editText?.setOnFocusChangeListener { _, hasFocus ->
                        if (!hasFocus && editText.text.toString().isEmpty()) inputLayout.error =
                            resources.getString(R.string.field_not_filled)
                        else inputLayout.error = null
                    }
                }
            }
            listeners {
                icExpand.onClick {
                    viewModel.setHidden(it)
                }
            }
        }

        addBinding<HotelUi.AddTourist, AddTouristLayoutBinding> {
            listeners {
                icAddTourist.onClick {
                    viewModel.add(it)
                }
            }
        }
        addBinding<HotelUi.InfoCommon, InfoCommonItemBinding> {
            bind {
                tvTour.text = it.tourPrice.toString() + " ₽"
                tvFuelSurcharge.text = it.fuelCharge.toString() + " ₽"
                tvServiceFee.text = it.serviceCharge.toString() + " ₽"
                val pay = it.toPay.toString()
                tvToPay.text = pay + " ₽"
                btnPay.text = ""
                btnPay.text =
                    resources.getString(R.string.pay) + btnPay.text.toString() + " " + pay + " ₽"
            }
            listeners {
                btnPay.onClick {
                    var i = 0
                    var isError = false
                    while (i < binding.rvList.childCount) {
                        val holder =
                            binding.rvList.getChildViewHolder(binding.rvList.getChildAt(i++))
                        val tourist = holder.itemView.findViewById<LinearLayout>(R.id.container)
                        val infoBuyer =
                            holder.itemView.findViewById<LinearLayout>(R.id.containerInfoBuyer)
                        println("attadag $infoBuyer ${holder.itemView}")
                        val errorTourist = false //searchError(tourist)
                        val errorBuyer = false //searchError(infoBuyer)
                        if (errorTourist || errorBuyer) isError = true
                    }
                    if (isError) {
                        Snackbar.make(
                            binding.root,
                            resources.getString(R.string.not_filled),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        viewModel.searchError(Action.Search)
                    }
                    else viewModel.openPaid()
                }
            }
        }

    }

    private fun searchError(layout: LinearLayout?): Boolean {
        var error = false
        if (layout != null) {
            for (item in layout.children) {
                val inputLayout = item as TextInputLayout
                val editText = inputLayout.editText
                if (editText?.text.toString().isEmpty()) {
                    inputLayout.error =
                        resources.getString(R.string.field_not_filled)
                    error = true
                }
            }
        }
        return error
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentReservationBinding =
        FragmentReservationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentResult(true, resources.getString(R.string.reservation))
        settingViewModel()
        binding.rvList.adapter = adapter
    }

    private fun settingViewModel() {
        viewModel.fetchInfoHotel()
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.containerError.visibility =
                if (it is HotelUiState.Error) View.VISIBLE else View.GONE
            binding.loading.visibility = if (it is HotelUiState.Loading) View.VISIBLE else View.GONE
            binding.rvList.visibility = if (it is HotelUiState.Success) View.VISIBLE else View.GONE
            if (it is HotelUiState.Success) adapter.submitList(it.list)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.coup()
        viewModel.searchError(Action.Coup)
    }
}