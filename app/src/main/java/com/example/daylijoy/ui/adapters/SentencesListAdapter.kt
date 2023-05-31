package com.example.daylijoy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daylijoy.R
import com.example.daylijoy.data.entities.SentenceEntity

class SentencesListAdapter :
    ListAdapter<SentenceEntity, SentencesListAdapter.SentenceViewHolder>(SentencesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentenceViewHolder {
        return SentenceViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SentenceViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.sentence)
    }

    class SentenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.text_sentence)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): SentenceViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.sentence_item, parent, false)
                return SentenceViewHolder(view)
            }
        }
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sentenceItemView: TextView = itemView.findViewById(R.id.text_sentence)

        fun bind(text: String?) {
            sentenceItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): SentenceViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.sentence_item, parent, false)
                return SentenceViewHolder(view)
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