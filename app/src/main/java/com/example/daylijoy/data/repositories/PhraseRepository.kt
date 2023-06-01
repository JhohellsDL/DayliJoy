package com.example.daylijoy.data.repositories

import com.example.daylijoy.data.daos.SentenceDao
import com.example.daylijoy.data.entities.SentenceEntity
import com.example.daylijoy.data.providers.PhraseProvider
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

class PhraseRepository(
    private val provider: PhraseProvider
) {
    fun getPositivePhrase(): String {
        return provider.loadListPositivePhrase()[0]
    }

    fun getRandomPositivePhrase(): String {

        val random = Random
        val size = provider.loadListPositivePhrase().size - 1
        val randomNumber = random.nextInt(size)

        return provider.loadListPositivePhrase()[randomNumber]

    }
}