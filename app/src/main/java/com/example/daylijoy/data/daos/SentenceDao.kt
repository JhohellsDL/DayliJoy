package com.example.daylijoy.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.daylijoy.data.entities.SentenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SentenceDao {

    @Query("SELECT * FROM sentence_table")
    fun getAllSentences(): Flow<List<SentenceEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sentence: SentenceEntity)

    @Query("DELETE FROM sentence_table")
    suspend fun deleteAll()

}