package com.jdl.daylijoy

import android.app.Application
import com.jdl.daylijoy.data.SentenceRoomDatabase
import com.jdl.daylijoy.data.repositories.SentenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DailyJoyApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy {
        SentenceRoomDatabase.getDatabase(this, applicationScope)
    }

    val repository by lazy {
        SentenceRepository(database.sentenceDao())
    }
}