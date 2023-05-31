package com.example.daylijoy.ui.screens

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.daylijoy.R
import com.example.daylijoy.databinding.FragmentNewSentenceBinding

class NewSentenceFragment : Fragment() {

    private lateinit var binding: FragmentNewSentenceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewSentenceBinding.inflate(inflater)

        val sentenceText = binding.editSentence.text
        binding.buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(sentenceText)){
                Toast.makeText(requireContext(), "vacio!", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(requireContext(), "$sentenceText", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}