package com.jdl.daylijoy.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jdl.daylijoy.R
import com.jdl.daylijoy.databinding.FragmentStartBinding

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