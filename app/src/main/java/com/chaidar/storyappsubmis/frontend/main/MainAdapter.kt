package com.chaidar.storyappsubmis.frontend.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaidar.storyappsubmis.R
import com.chaidar.storyappsubmis.backend.response.ListStoryItem
import com.chaidar.storyappsubmis.databinding.ItemRowBinding
import com.chaidar.storyappsubmis.frontend.detail.DetailActivity

class MainAdapter(private val listItem: List<ListStoryItem>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowBinding.bind(itemView)
//        private val imageViewCard: ImageView = binding.imageViewCard
//        private val titleTextViewCard: TextView = binding.titleTextViewCard
//        private val contentTextViewCard: TextView = binding.contentTextViewCard
        fun bindItem(item: ListStoryItem) {
            with(binding) {
                Glide.with(itemView).load(item.photoUrl).into(imageViewCard)
                titleTextViewCard.text = item.name
                contentTextViewCard.text = item.description
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


        val intent = Intent(holder.itemView.context, DetailActivity::class.java)
        intent.putExtra("userId", listItem[position].id)
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(intent)
        }

    }
}