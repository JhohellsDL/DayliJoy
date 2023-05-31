package com.example.daylijoy.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daylijoy.data.entities.SentenceEntity
import com.example.daylijoy.data.respositories.SentenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SentenceViewModel(private val repository: SentenceRepository) : ViewModel() {

    private var _allSentences= MutableLiveData<List<SentenceEntity>>()
    val allSentences: LiveData<List<SentenceEntity>>
        get() = _allSentences

    //----------------------------------- for coroutines--------------------------------------------
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //----------------------------------------------------------------------------------------------

    init {
        getValorList()
    }

    private fun getValorList() {
        uiScope.launch {
            repository.allSentences.let { listFlow ->
                listFlow.collect {
                    _allSentences.value = it
                }
            }
        }
    }

    fun insert(sentence: SentenceEntity) {
        uiScope.launch{
            repository.insert(sentence)
        }
    }
}