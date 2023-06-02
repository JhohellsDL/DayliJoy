package com.example.daylijoy.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.daylijoy.R
import com.example.daylijoy.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater)

        binding.btnStart.setOnClickListener {
            val action = R.id.action_startFragment_to_sentencesFragment
            it.findNavController().navigate(action)
        }

        return binding.root
    }

}