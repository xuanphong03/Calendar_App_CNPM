package com.example.calendarmanager.dbase

import android.content.Context
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.calendarmanager.models.dto.Task
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class TaskdaoTest: TestCase() {
    private lateinit var taskDB: TaskDB
    private lateinit var taskDao: TaskDao
    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        taskDB = Room.inMemoryDatabaseBuilder(context,(TaskDB::class.java)).allowMainThreadQueries().build()
        taskDao = taskDB.getTaskDao()
    }
    @After
    fun fearDown(){
        taskDB.close()
    }
    @Test
    fun test_insert() = runBlocking {
        var task = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
        taskDao.insert(task)
        val all = taskDao.getAllTaskTest()
        assertThat(all.contains(task)).isTrue()
    }
    @Test
    fun test_update() = runBlocking {
        var task2 = Task(35165,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
        taskDao.insert(task2)
        task2.title = "Nothing";
        taskDao.update(task2.id,task2.title,task2.content,task2.date,task2.isDone)
        val all = taskDao.getAllTaskTest()
        assertThat(all.any { it.title == task2.title && it.title == "Nothing" }).isTrue()
    }
    @Test
    fun test_delete() = runBlocking {
        var task = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
        taskDao.insert(task)
        taskDao.delete(task)
        val all = taskDao.getAllTaskTest()
        assertThat(all.contains(task)).isFalse()
    }

    @Test
    fun test_getall() = runBlocking {
        var task = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
        var task_1 = Task(3516516,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
        var task_2 = Task(3516515,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
        taskDao.insert(task)
        taskDao.insert(task_1)
        taskDao.insert(task_2)
        val all = taskDao.getAllTaskTest()
        assertThat(all.contains(task) && all.contains(task_1) && all.contains(task_2)).isTrue()
    }

//    @Test
    suspend fun testGetAllTask() {
        // Insert some test tasks into the database
        var task1 = Task(1, "Task 1", "Description 1", "2024-01-20", false)
        var task2 = Task(2, "Task 2", "Description 2", "2024-01-19", true)
        var task3 = Task(3, "Task 3", "Description 3", "2024-01-18", false)

        taskDao.insert(task1)
        taskDao.insert(task2)
        taskDao.insert(task3)

        // Observe the LiveData returned by getAllTask
        val observer = Observer<List<Task>> { tasks ->
            // Verify that the observed list of tasks matches the expected list
            assert(tasks != null)
            assert(tasks!!.size == 3)
            assert(tasks[0].id == 1)
            assert(tasks[1].id == 2)
            assert(tasks[2].id == 3)

            // You can add more assertions based on your specific requirements
        }

        // Observe the LiveData and trigger onChanged manually
        taskDao.getAllTask().observeForever(observer)

        // Wait for LiveData to be updated (using a CountDownLatch)
        val latch = CountDownLatch(1)
        latch.await(2, TimeUnit.SECONDS)

        // Clean up and remove the observer
        taskDao.getAllTask().removeObserver(observer)
    }
}