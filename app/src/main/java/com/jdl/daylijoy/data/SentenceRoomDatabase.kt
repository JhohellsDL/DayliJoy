package com.jdl.daylijoy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jdl.daylijoy.data.daos.SentenceDao
import com.jdl.daylijoy.data.entities.SentenceEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [SentenceEntity::class], version = 1, exportSchema = false)
abstract class SentenceRoomDatabase : RoomDatabase() {

    abstract fun sentenceDao(): SentenceDao

    companion object {

        @Volatile
        private var INSTANCE: SentenceRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): SentenceRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SentenceRoomDatabase::class.java,
                    "sentence_database"
                )
                    .addCallback(SentenceDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class SentenceDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    populateDatabase(it.sentenceDao())
                }
            }
        }

        suspend fun populateDatabase(sentenceDao: SentenceDao) {
            //Delete all content here
            sentenceDao.deleteAll()

            //Add sample sentences
            var sentence = SentenceEntity(
                sentence = "My firs sentence",
                date = "ayer"
            )
            sentenceDao.insert(sentence)

            sentence = SentenceEntity(
                sentence = "My second sentence",
                date = "hoy"
            )
            sentenceDao.insert(sentence)

        }
    }
}