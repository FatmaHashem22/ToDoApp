package com.example.todoapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "TodoTable")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,

    @ColumnInfo
    val todoName :String? = null,

    @ColumnInfo
    val todoDescription : String? = null,

    @ColumnInfo
    val date : Date? = null,

    @ColumnInfo
    val isDone : Boolean? = null

)
