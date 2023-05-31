package com.example.daylijoy.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daylijoy.data.entities.SentenceEntity
import com.example.daylijoy.data.respositories.SentenceRepository
import kotlinx.coroutines.launch

class SentenceViewModel(private val repository: SentenceRepository) : ViewModel() {

    private var _allSentences= MutableLiveData<List<SentenceEntity>>()
    val allSentences: LiveData<List<SentenceEntity>>
        get() = _allSentences


    fun insert(sentence: SentenceEntity) {
        viewModelScope.launch{
            repository.insert(sentence)
        }
    }
}