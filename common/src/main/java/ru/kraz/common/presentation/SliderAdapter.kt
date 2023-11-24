package ru.kraz.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.kraz.common.databinding.SliderItemBinding

class SliderAdapter(
    private val imgList: List<String>,
) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: SliderItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: String) {
            view.img.load(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SliderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = imgList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imgList[position])
    }
}