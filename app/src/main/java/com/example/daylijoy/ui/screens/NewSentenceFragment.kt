package com.example.daylijoy.ui.screens

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.daylijoy.DailyJoyApplication
import com.example.daylijoy.data.entities.SentenceEntity
import com.example.daylijoy.databinding.FragmentNewSentenceBinding
import com.example.daylijoy.ui.viewmodels.SentenceViewModel
import com.example.daylijoy.ui.viewmodels.SentenceViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewSentenceFragment : Fragment() {

    private lateinit var binding: FragmentNewSentenceBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewSentenceBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val sentenceViewModel: SentenceViewModel by viewModels {
            SentenceViewModelFactory((application as DailyJoyApplication).repository)
        }

        val dateFormat = getDate()

        val sentenceText = binding.editSentence.text

        binding.buttonSave.setOnClickListener {
            if (sentenceText.isNullOrBlank()) {
                Toast.makeText(requireContext(), "vacio!", Toast.LENGTH_SHORT).show()
            } else {
                sentenceViewModel.insert(
                    SentenceEntity(
                        sentence = sentenceText.toString(),
                        date = dateFormat
                    )
                )
                Toast.makeText(requireContext(), dateFormat, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(): String {
        val date = LocalDate.now()
        val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return date.format(format)
    }
}