package com.example.daylijoy.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daylijoy.DailyJoyApplication
import com.example.daylijoy.data.entities.SentenceEntity
import com.example.daylijoy.databinding.FragmentSentencesBinding
import com.example.daylijoy.ui.adapters.SentencesListAdapter
import com.example.daylijoy.ui.viewmodels.SentenceViewModel
import com.example.daylijoy.ui.viewmodels.SentenceViewModelFactory

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
            sentenceViewModel.insert(
                SentenceEntity(
                    sentence = "hola!!!"
                )
            )
            /*val action = R.id.action_sentencesFragment_to_newSentenceFragment
            it.findNavController().navigate(action)*/
        }

        return binding.root
    }

}