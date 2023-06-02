package com.jdl.daylijoy.data.repositories

import com.jdl.daylijoy.data.daos.SentenceDao
import com.jdl.daylijoy.data.entities.SentenceEntity
import kotlinx.coroutines.flow.Flow

class SentenceRepository(private val sentenceDao: SentenceDao) {

    val allSentences: Flow<List<SentenceEntity>> = sentenceDao.getAllSentences()

    suspend fun insert(sentence: SentenceEntity){
        sentenceDao.insert(sentence)
    }
}