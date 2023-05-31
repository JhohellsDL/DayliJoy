package com.example.daylijoy

import android.app.Application
import com.example.daylijoy.data.SentenceRoomDatabase
import com.example.daylijoy.data.respositories.SentenceRepository

class DailyJoyApplication : Application() {

    val database by lazy {
        SentenceRoomDatabase.getDatabase(this)
    }

    val repository by lazy {
        SentenceRepository(database.sentenceDao())
    }
}