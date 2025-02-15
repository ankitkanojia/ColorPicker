package com.github.dhaval2404.colorpicker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.colorpicker.R
import com.github.dhaval2404.colorpicker.databinding.AdapterMaterialColorPickerBinding
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.util.SharedPref

/**
 * Adapter for Recent Color
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Dec 2019
 */
class RecentColorAdapter(colors: List<String>) :
    RecyclerView.Adapter<RecentColorAdapter.MaterialColorViewHolder>() {

    private val itemList = ArrayList<String>(colors)
    private var colorShape = ColorShape.CIRCLE
    private var colorListener: ColorListener? = null
    private var emptyColor = "#E0E0E0"

    fun setColorListener(listener: ColorListener) {
        this.colorListener = listener
    }

    fun setEmptyColor(color: String) {
        this.emptyColor = color
    }

    fun setColorShape(colorShape: ColorShape) {
        this.colorShape = colorShape
    }

    override fun getItemCount() = SharedPref.RECENT_COLORS_LIMIT

    fun getItem(position: Int): String {
        return if (position < itemList.size) {
            itemList[position]
        } else {
            emptyColor
        }
    }

    private fun bindAdapter(parent: ViewGroup): AdapterMaterialColorPickerBinding {
        val inflater = LayoutInflater.from(parent.context)
        return DataBindingUtil.inflate(
            inflater,
            R.layout.adapter_material_color_picker,
            parent,
            false
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialColorViewHolder {
        val binding = bindAdapter(parent)
        return MaterialColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaterialColorViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MaterialColorViewHolder(private val binding: AdapterMaterialColorPickerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.colorView.setOnClickListener {
                val index = it.tag as Int
                if (index < itemList.size) {
                    val color = getItem(index)
                    colorListener?.onColorSelected(Color.parseColor(color), color)
                }
            }
        }

        fun bind(position: Int) {
            val color = getItem(position)

            binding.isChecked = false
            binding.index = position
            binding.color = Color.parseColor(color)

            if (colorShape == ColorShape.SQAURE) {
                binding.colorView.radius =
                    binding.colorView.resources.getDimension(R.dimen.color_card_square_radius)
            }
        }
    }
}
