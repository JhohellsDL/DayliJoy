package com.example.daylijoy.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daylijoy.R
import com.example.daylijoy.databinding.FragmentSentencesBinding
import com.example.daylijoy.ui.adapters.SentencesListAdapter

class SentencesFragment : Fragment() {

    private lateinit var binding: FragmentSentencesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSentencesBinding.inflate(inflater)

        val recyclerView = binding.recyclerviewSentences
        val adapter = SentencesListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.floatingButtonAdd.setOnClickListener {
            val action = R.id.action_sentencesFragment_to_newSentenceFragment
            it.findNavController().navigate(action)
        }

        return binding.root
    }

}