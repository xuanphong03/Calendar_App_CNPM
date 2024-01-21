package com.example.calendarmanager.models.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Task_tb")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "isdone")
    var isDone: Boolean
): Serializable
