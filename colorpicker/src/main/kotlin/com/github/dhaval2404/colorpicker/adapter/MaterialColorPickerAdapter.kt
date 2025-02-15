package com.github.dhaval2404.colorpicker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.colorpicker.R
import com.github.dhaval2404.colorpicker.databinding.AdapterMaterialColorPickerBinding
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.util.ColorUtil

/**
 * Material Color Listing
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Dec 2019
 */
class MaterialColorPickerAdapter(private val colors: List<String>) :
    RecyclerView.Adapter<MaterialColorPickerAdapter.MaterialColorViewHolder>() {

    private var isDarkColor = false
    private var color = ""
    private var colorShape = ColorShape.CIRCLE

    init {
        val darkColors = colors.count { ColorUtil.isDarkColor(it) }
        isDarkColor = (darkColors * 2) >= colors.size
    }

    fun setColorShape(colorShape: ColorShape) {
        this.colorShape = colorShape
    }

    fun setDefaultColor(color: String) {
        this.color = color
    }

    fun getSelectedColor() = color

    fun getItem(position: Int) = colors[position]

    override fun getItemCount() = colors.size

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
                val newIndex = it.tag as Int
                val color = getItem(newIndex)

                val oldIndex = colors.indexOf(this@MaterialColorPickerAdapter.color)
                this@MaterialColorPickerAdapter.color = color

                notifyItemChanged(oldIndex)
                notifyItemChanged(newIndex)
            }
        }

        fun bind(position: Int) {
            val color = getItem(position)

            binding.isChecked = color == this@MaterialColorPickerAdapter.color
            binding.index = position
            binding.color = Color.parseColor(color)

            if (colorShape == ColorShape.SQAURE) {
                binding.colorView.radius =
                    binding.colorView.context.resources.getDimension(R.dimen.color_card_square_radius)
            }

            binding.checkIcon.setColorFilter(if (isDarkColor) Color.WHITE else Color.BLACK)
        }
    }
}
