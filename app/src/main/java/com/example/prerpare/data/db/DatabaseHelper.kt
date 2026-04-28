package com.example.prerpare.data.db

import android.content.Context
import androidx.room.Room

object DatabaseHelper {

    // 单例模式：确保数据库只创建一次
    @Volatile
    private var instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            val tempInstance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "user_database"
            ).build()
            instance = tempInstance
            tempInstance
        }
    }
}