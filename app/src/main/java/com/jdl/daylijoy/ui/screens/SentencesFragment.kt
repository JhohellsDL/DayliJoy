package com.jdl.daylijoy.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdl.daylijoy.DailyJoyApplication
import com.jdl.daylijoy.R
import com.jdl.daylijoy.databinding.FragmentSentencesBinding
import com.jdl.daylijoy.ui.adapters.SentencesListAdapter
import com.jdl.daylijoy.ui.viewmodels.SentenceViewModel
import com.jdl.daylijoy.ui.viewmodels.SentenceViewModelFactory

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


        val application = requireNotNull(this.activity).application

        val sentenceViewModel: SentenceViewModel by viewModels {
            SentenceViewModelFactory((application as DailyJoyApplication).repository)
        }

        sentenceViewModel.allSentences.observe(viewLifecycleOwner){
            it?.let {
                adapter.submitList(it)
            }
        }

        binding.floatingButtonAdd.setOnClickListener {
            val action = R.id.action_sentencesFragment_to_newSentenceFragment
            it.findNavController().navigate(action)
        }

        return binding.root
    }

}