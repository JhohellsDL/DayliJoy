package com.jdl.daylijoy.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jdl.daylijoy.databinding.SentenceItemBinding
import com.jdl.daylijoy.data.entities.SentenceEntity

class SentencesListAdapter :
    ListAdapter<SentenceEntity, SentencesListAdapter.SentenceViewHolder>(SentencesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentenceViewHolder {
        return SentenceViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SentenceViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class SentenceViewHolder private constructor(val binding: SentenceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SentenceEntity) {
            binding.textSentence.text = item.sentence
            binding.textDate.text = item.date
        }

        companion object {
            fun create(parent: ViewGroup): SentenceViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SentenceItemBinding.inflate(layoutInflater, parent, false)
                return SentenceViewHolder(binding)
            }
        }
    }

    class SentencesComparator : DiffUtil.ItemCallback<SentenceEntity>() {
        override fun areItemsTheSame(oldItem: SentenceEntity, newItem: SentenceEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SentenceEntity, newItem: SentenceEntity): Boolean {
            return oldItem.sentence == newItem.sentence
        }
    }
}