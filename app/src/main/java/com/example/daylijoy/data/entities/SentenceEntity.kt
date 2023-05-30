package com.example.daylijoy.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sentence_table")
data class SentenceEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sentence: String
)