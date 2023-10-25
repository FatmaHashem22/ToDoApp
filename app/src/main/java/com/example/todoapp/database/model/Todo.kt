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
    var todoName :String? = null,

    @ColumnInfo
    var todoDescription : String? = null,

    @ColumnInfo
    var date : Date? = null,

    @ColumnInfo
    var isDone : Boolean? = null

):java.io.Serializable
