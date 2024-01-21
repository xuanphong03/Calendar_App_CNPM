package com.example.calendarmanager.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.calendarmanager.dbase.TaskDB
import com.example.calendarmanager.dbase.TaskRepossitory
import com.example.calendarmanager.models.dto.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application){

    private val repo: TaskRepossitory
    var allTask: LiveData<List<Task>>
    init {
        val dao = TaskDB.getDB(application).getTaskDao()
        repo = TaskRepossitory(dao)
        allTask = repo.allTask
    }
    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO){
        repo.delete(task)
    }
    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(task)
    }
    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO){
        repo.update(task)
    }

    fun addObserver(observer: Observer<List<Task>>) {
        allTask.observeForever(observer)
    }

    fun removeObserver(observer: Observer<List<Task>>) {
        allTask.removeObserver(observer)
    }
}
