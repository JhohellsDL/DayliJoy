package com.example.daylijoy.ui.screens

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.daylijoy.DailyJoyApplication
import com.example.daylijoy.R
import com.example.daylijoy.data.entities.SentenceEntity
import com.example.daylijoy.databinding.FragmentNewSentenceBinding
import com.example.daylijoy.ui.viewmodels.SentenceViewModel
import com.example.daylijoy.ui.viewmodels.SentenceViewModelFactory

class NewSentenceFragment : Fragment() {

    private lateinit var binding: FragmentNewSentenceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewSentenceBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val sentenceViewModel: SentenceViewModel by viewModels {
            SentenceViewModelFactory((application as DailyJoyApplication).repository)
        }

        val sentenceText = binding.editSentence.text

        binding.buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(sentenceText)) {
                Toast.makeText(requireContext(), "vacio!", Toast.LENGTH_SHORT).show()
            } else {
                sentenceViewModel.insert(
                    SentenceEntity(
                        sentence = sentenceText.toString()
                    )
                )
                Toast.makeText(requireContext(), "Sentence save!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}