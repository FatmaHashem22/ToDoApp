package com.example.todoapp.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.database.model.Todo

@Dao
interface TodoDao {

    @Insert
    fun insertTodo(todo : Todo)

    @Delete
    fun deleteTodo(todo : Todo)

    @Update
    fun updateTodo(todo : Todo)

    @Query("SELECT * FROM TodoTable")
    fun getTodos() : List<Todo>
}