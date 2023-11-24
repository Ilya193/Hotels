package ru.kraz.feature_hotel.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kraz.feature_hotel.databinding.AdvantagesLayoutBinding

class AdvantagesAdapter(
    private val advantages: List<AdvantageUi>,
) : RecyclerView.Adapter<AdvantagesAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: AdvantagesLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: AdvantageUi, visibility: Int) {
            view.icon.setImageResource(item.icon)
            view.tvTitle.text = item.title
            view.tvEssentials.text = item.subtitle
            view.divider.visibility = visibility
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdvantagesLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = advantages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val visibility = if (position == itemCount - 1) View.GONE else View.VISIBLE
        holder.bind(advantages[position], visibility)
    }
}