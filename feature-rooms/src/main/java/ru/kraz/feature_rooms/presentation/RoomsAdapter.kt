package ru.kraz.feature_rooms.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import ru.kraz.common.presentation.DiffUtilCallback
import ru.kraz.common.presentation.SliderAdapter
import ru.kraz.feature_rooms.R
import ru.kraz.feature_rooms.databinding.RoomLayoutBinding
import kotlin.math.abs

class RoomsAdapter(
    private val context: Context,
    private val onClick: () -> Unit,
) : ListAdapter<RoomUi, RoomsAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(private val view: RoomLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: RoomUi) {
            val itemSuccess = item as RoomUi.Success
            val adapter = SliderAdapter(itemSuccess.imageUrls)
            view.viewPager.adapter = adapter
            view.dotsIndicator.attachTo(view.viewPager)

            view.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val transform = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(30))
                addTransformer { page, position ->
                    page.scaleY = (0.85 + (1 - abs(position)) * 0.15f).toFloat()
                }
            }

            view.viewPager.setPageTransformer(transform)

            view.tvName.text = itemSuccess.name
            view.tvPeculiarities.text = ""
            itemSuccess.peculiarities.forEachIndexed { index, str ->
                if (index != 0) view.tvPeculiarities.text =
                    view.tvPeculiarities.text.toString() + "\t\t\t\t" + str
                else view.tvPeculiarities.text = view.tvPeculiarities.text.toString() + str
            }

            view.tvMore.text = context.getString(R.string.more_room)

            var mapValue = ""
            itemSuccess.price.toString().forEachIndexed { index, c ->
                if (index % 3 == 0) mapValue += " "
                mapValue += c
            }

            view.tvPrice.text =
                context?.getString(R.string.from) + mapValue + " ₽ "
            view.tvPricePer.text = itemSuccess.pricePer.lowercase()

            view.btnSelectNum.setOnClickListener {
                onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RoomLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}