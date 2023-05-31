package com.example.daylijoy.data.respositories

import com.example.daylijoy.data.daos.SentenceDao
import com.example.daylijoy.data.entities.SentenceEntity
import kotlinx.coroutines.flow.Flow

class SentenceRepository(private val sentenceDao: SentenceDao) {

    val allSentences: Flow<List<SentenceEntity>> = sentenceDao.getAllSentences()

    suspend fun insert(sentence: SentenceEntity){
        sentenceDao.insert(sentence)
    }
}