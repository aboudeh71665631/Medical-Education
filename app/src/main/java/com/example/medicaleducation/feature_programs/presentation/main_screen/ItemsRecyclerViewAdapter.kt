package com.example.medicaleducation.feature_programs.presentation.main_screen

import android.content.ClipData
import android.graphics.drawable.shapes.RoundRectShape
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.medicaleducation.common.Constants.TAG
import com.example.medicaleducation.databinding.ProgramItemBinding
import com.example.medicaleducation.databinding.ProgramsListItemBinding
import com.example.medicaleducation.feature_programs.domain.model.Media
import com.example.medicaleducation.feature_programs.domain.model.Program

class ItemsRecyclerViewAdapter(var items: List<Media>) :
    RecyclerView.Adapter<ItemsRecyclerViewAdapter.ItemViewHolder>() {

    private lateinit var mListener: onItemClickedListener
    private var itemList = emptyList<Media>()

    init {
        itemList = items
    }

    inner class ItemViewHolder(val binding: ProgramItemBinding, listener: onItemClickedListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    interface onItemClickedListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickedListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ProgramItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),mListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            articleTitle.text = itemList[position].title
            articleImage.load(
                itemList[position].icon
            ) {
                transformations(RoundedCornersTransformation(15f, 15f, 15f, 15f))
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}