package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.database.Dao.TodoDao
import com.example.todoapp.database.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getTodoDao() : TodoDao

    companion object{
        private val DATABASE_NAME = "Todos-Database"
        private var INSTANCE : MyDatabase? = null
        fun getInstance(context : Context) : MyDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    DATABASE_NAME
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE!!
        }
    }
}