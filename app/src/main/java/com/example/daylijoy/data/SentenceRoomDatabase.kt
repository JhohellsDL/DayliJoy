package com.example.daylijoy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.daylijoy.data.daos.SentenceDao
import com.example.daylijoy.data.entities.SentenceEntity

@Database(entities = [SentenceEntity::class], version = 1, exportSchema = false)
abstract class SentenceRoomDatabase: RoomDatabase() {

    abstract fun sentenceDao(): SentenceDao

    companion object {

        @Volatile
        private var INSTANCE: SentenceRoomDatabase? = null

        fun getDatabase(context: Context): SentenceRoomDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SentenceRoomDatabase::class.java,
                    "sentence_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}