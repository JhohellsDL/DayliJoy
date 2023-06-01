package com.example.daylijoy.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daylijoy.data.repositories.SentenceRepository

@Suppress("UNCHECKED_CAST")
class SentenceViewModelFactory(
    private val repository: SentenceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SentenceViewModel::class.java)) {
            return SentenceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}