package com.example.daylijoy.data.repositories

import com.example.daylijoy.data.daos.SentenceDao
import com.example.daylijoy.data.entities.SentenceEntity
import com.example.daylijoy.data.providers.PhraseProvider
import kotlinx.coroutines.flow.Flow

class PhraseRepository(
    private val provider: PhraseProvider
) {
    fun getPositivePhrase(): String {
        return provider.loadListPositivePhrase()[0]
    }
}