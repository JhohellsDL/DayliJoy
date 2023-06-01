package com.example.daylijoy.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.daylijoy.R
import com.example.daylijoy.data.providers.PhraseProvider
import com.example.daylijoy.data.repositories.PhraseRepository
import com.example.daylijoy.databinding.FragmentPositivePhraseBinding

class PositivePhraseFragment : Fragment() {

    private lateinit var binding: FragmentPositivePhraseBinding

    private val provider: PhraseRepository = PhraseRepository(PhraseProvider())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPositivePhraseBinding.inflate(inflater)

        binding.textPositivePhrase.text = provider.getRandomPositivePhrase()

        binding.botonRegresar.setOnClickListener {
            it.findNavController().navigate(R.id.action_positivePhraseFragment_to_sentencesFragment)
        }

        return binding.root
    }

}