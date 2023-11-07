package com.chaidar.storyappsubmis.frontend.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaidar.storyappsubmis.R
import com.chaidar.storyappsubmis.backend.response.ListStoryItem
import com.chaidar.storyappsubmis.databinding.ItemRowBinding

class MainAdapter(private val listItem: List<ListStoryItem>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowBinding.bind(itemView)
        fun bindItem(item: ListStoryItem) {
            with(binding) {
                Glide.with(itemView).load(item.photoUrl).into(imageViewCard)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        )
    }

    override fun getItemCount(): Int = listItem.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listItem[position])
    }
}