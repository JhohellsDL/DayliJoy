package com.jdl.daylijoy.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jdl.daylijoy.R
import com.jdl.daylijoy.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater)

        // Initialize the Mobile Ads SDK with an AdMob App ID.
        MobileAds.initialize(this.requireContext()) {}

        // Create an ad request.
        val adRequest = AdRequest.Builder().build()

        // Start loading the ad in the background.
        binding.adView.loadAd(adRequest)


        binding.btnStart.setOnClickListener {
            val action = R.id.action_startFragment_to_sentencesFragment
            it.findNavController().navigate(action)
        }

        return binding.root
    }

}