//////import android.app.Application
//////import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//////import androidx.lifecycle.Observer
//////import com.example.calendarmanager.dbase.TaskRepossitory
//////import com.example.calendarmanager.models.TaskViewModel
//////import com.example.calendarmanager.models.dto.Task
//////import io.mockk.MockKAnnotations
//////import io.mockk.coEvery
//////import io.mockk.coVerify
//////import io.mockk.mockk
//////import kotlinx.coroutines.ExperimentalCoroutinesApi
//////import kotlinx.coroutines.test.TestCoroutineDispatcher
//////import kotlinx.coroutines.test.TestCoroutineScope
//////import kotlinx.coroutines.test.runBlockingTest
//////import org.junit.Before
//////import org.junit.Rule
//////import org.junit.Test
//////
//////@ExperimentalCoroutinesApi
//////class TaskViewModelTest {
//////
//////    @get:Rule
//////    val rule = InstantTaskExecutorRule()
//////
//////    private val testDispatcher = TestCoroutineDispatcher()
//////    private val testScope = TestCoroutineScope(testDispatcher)
//////
//////    private lateinit var viewModel: TaskViewModel
//////    private lateinit var repository: TaskRepossitory
//////    private lateinit var observer: Observer<List<Task>>
//////
//////    @Before
//////    fun setup() {
//////        MockKAnnotations.init(this)
//////        repository = mockk(relaxed = true)
//////        viewModel = TaskViewModel(application = Application())
//////        observer = mockk(relaxed = true)
//////    }
//////
//////    @Test
//////    fun testdeleteTask() = testScope.runBlockingTest {
//////        // Given
//////        val task = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
//////
//////        // When
//////        viewModel.deleteTask(task)
//////
//////        // Then
//////        coVerify { repository.delete(task) }
//////    }
//////
//////    @Test
//////    fun testinsert() = testScope.runBlockingTest {
//////        // Given
//////        val task = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
//////
//////        // When
//////        viewModel.insert(task)
//////
//////        // Then
//////        coVerify { repository.insert(task) }
//////    }
//////
//////    @Test
//////    fun testupdateTask() = testScope.runBlockingTest {
//////        // Given
//////        val task = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
//////
//////        // When
//////        viewModel.updateTask(task)
//////
//////        // Then
//////        coVerify { repository.update(task) }
//////    }
//////
//////    @Test
//////    fun testgetAllTask() = testScope.runBlockingTest {
//////        // Given
//////        val tasks = listOf(
//////            Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true),
//////                    Task(351653,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
//////        )
//////        coEvery { repository.allTask } returns mockk(relaxed = true)
//////
//////        // When
//////        viewModel.allTask.observeForever(observer)
//////
//////        // Then
//////        coVerify { repository.allTask }
//////        viewModel.allTask.removeObserver(observer)
//////    }
//////------------------------------------------------------------------------------------------------------------------
//////import android.app.Application
//////import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//////import androidx.lifecycle.Observer
//////import androidx.lifecycle.ViewModelProvider
//////import com.example.calendarmanager.dbase.TaskDao
//////import com.example.calendarmanager.dbase.TaskRepossitory
//////import com.example.calendarmanager.models.TaskViewModel
//////import com.example.calendarmanager.models.dto.Task
//////import kotlinx.coroutines.Dispatchers
//////import kotlinx.coroutines.ExperimentalCoroutinesApi
//////import kotlinx.coroutines.test.TestCoroutineDispatcher
//////import kotlinx.coroutines.test.resetMain
//////import kotlinx.coroutines.test.setMain
//////import org.junit.After
//////import org.junit.Before
//////import org.junit.Rule
//////import org.junit.Test
//////import org.mockito.Mock
//////import org.mockito.Mockito
//////import org.mockito.Mockito.verify
//////import org.mockito.MockitoAnnotations
//////
//////@ExperimentalCoroutinesApi
//////class TaskViewModelTest {
//////
//////    @get:Rule
//////    val rule = InstantTaskExecutorRule()
//////
//////    @Mock
//////    private lateinit var application: Application
//////
//////    @Mock
//////    private lateinit var observer: Observer<List<Task>>
//////
//////    private lateinit var viewModel: TaskViewModel
//////
//////    private val testDispatcher = TestCoroutineDispatcher()
//////
//////    @Before
//////    fun setUp() {
//////        MockitoAnnotations.initMocks(this)
//////
//////        Dispatchers.setMain(testDispatcher)
//////
//////        val dao = Mockito.mock(TaskDao::class.java)
//////        val repository = TaskRepossitory(dao)
////////        viewModel = TaskViewModel(application = Application())
//////        viewModel = ViewModelProvider(this,
//////            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TaskViewModel::class.java)
//////
//////        viewModel.allTask.observeForever(observer)
//////    }
//////
//////    @Test
//////    fun `testinsert`() {
//////        val task = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
//////        viewModel.insert(task)
//////
//////        verify(observer).onChanged(Mockito.any()) // Verify that onChanged was called
//////        // You can add more assertions based on your requirements
//////    }
//////
////////    @Test
////////    fun `testupdateTask`() {
////////        val task = Task("Test Task", false)
////////        viewModel.updateTask(task)
////////
////////        verify(observer).onChanged(Mockito.any()) // Verify that onChanged was called
////////        // You can add more assertions based on your requirements
////////    }
////////
////////    @Test
////////    fun `testdeleteTask`() {
////////        val task = Task("Test Task", false)
////////        viewModel.deleteTask(task)
////////
////////        verify(observer).onChanged(Mockito.any()) // Verify that onChanged was called
////////        // You can add more assertions based on your requirements
////////    }
//////
//////    @After
//////    fun tearDown() {
//////        viewModel.allTask.removeObserver(observer)
//////        Dispatchers.resetMain()
//////        testDispatcher.cleanupTestCoroutines()
//////    }
//////}
//////-----------------------------------------------------------------------------------------------------------
//////import android.app.Application
//////import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//////import androidx.lifecycle.Observer
//////import com.example.calendarmanager.dbase.TaskDB
//////import com.example.calendarmanager.dbase.TaskDao
//////import com.example.calendarmanager.dbase.TaskRepossitory
//////import com.example.calendarmanager.models.TaskViewModel
//////import com.example.calendarmanager.models.dto.Task
//////import kotlinx.coroutines.Dispatchers
//////import kotlinx.coroutines.ExperimentalCoroutinesApi
//////import kotlinx.coroutines.test.TestCoroutineDispatcher
//////import kotlinx.coroutines.test.resetMain
//////import kotlinx.coroutines.test.runBlockingTest
//////import kotlinx.coroutines.test.setMain
//////import org.junit.After
//////import org.junit.Before
//////import org.junit.Rule
//////import org.junit.Test
//////import org.junit.runner.RunWith
//////import org.mockito.Mock
//////import org.mockito.Mockito.*
//////import org.mockito.MockitoAnnotations
//////import org.mockito.junit.MockitoJUnitRunner
//////
//////@ExperimentalCoroutinesApi
//////@RunWith(MockitoJUnitRunner::class)
//////class TaskViewModelTest {
//////
//////    @get:Rule
//////    val rule = InstantTaskExecutorRule()
//////
//////    private val testDispatcher = TestCoroutineDispatcher()
//////
//////    @Mock
//////    private lateinit var application: Application
//////
//////    @Mock
//////    private lateinit var observer: Observer<List<Task>>
//////
//////    @Mock
//////    private lateinit var dao: TaskDao
//////
//////    @Mock
//////    private lateinit var repository: TaskRepossitory
//////
//////    @Mock
//////    private lateinit var viewModel: TaskViewModel
//////
//////    @Before
//////    fun setup() {
//////        MockitoAnnotations.initMocks(this)
//////        Dispatchers.setMain(testDispatcher)
//////
//////        // Mock your database and repository
//////        `when`(TaskDB.getDB(application).getTaskDao()).thenReturn(dao)
//////        `when`(TaskRepossitory(dao)).thenReturn(repository)
//////
//////        viewModel = TaskViewModel(application)
//////        viewModel.allTask.observeForever(observer)
//////    }
//////
//////    @After
//////    fun cleanup() {
//////        viewModel.allTask.removeObserver(observer)
//////        Dispatchers.resetMain()
//////        testDispatcher.cleanupTestCoroutines()
//////    }
//////
//////    @Test
//////    fun `testinsert`() = testDispatcher.runBlockingTest {
//////        val task = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
//////        viewModel.insert(task)
//////        verify(repository, times(1)).insert(task)
//////    }
//////
////////    @Test
////////    fun `test delete`() = testDispatcher.runBlockingTest {
////////        val task = Task("Test Task")
////////        viewModel.deleteTask(task)
////////        verify(repository, times(1)).delete(task)
////////    }
////////
////////    @Test
////////    fun `test update`() = testDispatcher.runBlockingTest {
////////        val task = Task("Test Task")
////////        viewModel.updateTask(task)
////////        verify(repository, times(1)).update(task)
////////    }
//////}
////
////
////
////import android.app.Application
////import androidx.arch.core.executor.testing.InstantTaskExecutorRule
////import androidx.lifecycle.Observer
////import androidx.test.core.app.ApplicationProvider
////import com.example.calendarmanager.dbase.TaskDB
////import com.example.calendarmanager.dbase.TaskRepossitory
////import com.example.calendarmanager.models.TaskViewModel
////import com.example.calendarmanager.models.dto.Task
////import kotlinx.coroutines.Dispatchers
////import kotlinx.coroutines.ExperimentalCoroutinesApi
////import kotlinx.coroutines.test.*
////import org.junit.After
////import org.junit.Before
////import org.junit.Rule
////import org.junit.Test
////import org.mockito.Mock
////import org.mockito.Mockito
////import org.mockito.MockitoAnnotations
////
////@ExperimentalCoroutinesApi
////class TaskViewModelTest {
////
////    @get:Rule
////    var instantTaskExecutorRule = InstantTaskExecutorRule()
////
//////    @get:Rule
//////    val coroutineTestRule = CoroutineTestRule()
////
////    @Mock
////    private lateinit var observer: Observer<List<Task>>
////
////    private lateinit var viewModel: TaskViewModel
////    private lateinit var repository: TaskRepossitory
////
////    @Before
////    fun setup() {
////        try {
////            MockitoAnnotations.initMocks(this)
////            val context = ApplicationProvider.getApplicationContext<Application>()
////            val application = ApplicationProvider.getApplicationContext() as Application
//////            val testViewModel = MyViewModelThatExtendsFromAndroidViewModel(application)
//////            testViewModel.foo()
////            val dao = TaskDB.getDB(application).getTaskDao()
////            repository = TaskRepossitory(dao)
////            viewModel = TaskViewModel(application)
////            viewModel.allTask.observeForever(observer)
////        } catch (e: Exception) {
////            e.printStackTrace()
////        }
////    }
////
////
////
////    @Test
////    fun testInsert() = runBlockingTest {
////        // Given a new task
////        val newTask = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
////
////        // When inserting the task
////        viewModel.insert(newTask)
////
////        // Then the observer should be notified with the new task
////        Mockito.verify(observer).onChanged(listOf(newTask))
////    }
//////
//////    @Test
//////    fun testDelete() = coroutineTestRule.runBlockingTest {
//////        // Given a task
//////        val taskToDelete = Task("Task to Delete", false)
//////        viewModel.insert(taskToDelete)
//////
//////        // When deleting the task
//////        viewModel.deleteTask(taskToDelete)
//////
//////        // Then the observer should be notified with an empty list
//////        Mockito.verify(observer).onChanged(emptyList())
//////    }
//////
//////    @Test
//////    fun testUpdate() = coroutineTestRule.runBlockingTest {
//////        // Given a task
//////        val taskToUpdate = Task("Task to Update", false)
//////        viewModel.insert(taskToUpdate)
//////
//////        // When updating the task
//////        val updatedTask = taskToUpdate.copy(completed = true)
//////        viewModel.updateTask(updatedTask)
//////
//////        // Then the observer should be notified with the updated task
//////        Mockito.verify(observer).onChanged(listOf(updatedTask))
//////    }
////
////    @After
////    fun tearDown() {
////        if (::viewModel.isInitialized) {
////            viewModel.allTask.removeObserver(observer)
////        }
////    }
////}
//
//import android.app.Application
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import com.example.calendarmanager.dbase.TaskDao
//import com.example.calendarmanager.models.TaskViewModel
//import com.example.calendarmanager.models.dto.Task
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.TestCoroutineScope
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.Mockito.`when`
//import org.mockito.MockitoAnnotations
//
//@ExperimentalCoroutinesApi
//class TaskViewModelTest {
//
//    // This rule swaps the background executor used by the Architecture Components with a different one that executes each task synchronously.
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private val testDispatcher = TestCoroutineDispatcher()
//    private val testScope = TestCoroutineScope(testDispatcher)
//
//    @Mock
//    private lateinit var application: Application
//
//    @Mock
//    private lateinit var observer: Observer<List<Task>>
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//    }
//
//    @Test
//    fun testInsert() = testScope.runBlockingTest {
//        // Mock dependencies
//        val dao = mock(TaskDao::class.java)
//        `when`(dao.insert(any(Task::class.java))).thenReturn(Unit)
//
//        val viewModel = TaskViewModel(application)
//        viewModel.addObserver(observer)
//
//        // Perform action
//        viewModel.insert(Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true))
//
//        // Verify the result
//        Mockito.verify(dao).insert(any(Task::class.java))
//        Mockito.verify(observer).onChanged(anyList())
//    }
//
//    @Test
//    fun testDelete() = testScope.runBlockingTest {
//        // Mock dependencies
//        val dao = mock(TaskDao::class.java)
//        `when`(dao.delete(any(Task::class.java))).thenReturn(Unit)
//
//        val viewModel = TaskViewModel(application)
//        viewModel.addObserver(observer)
//
//        // Perform action
//        viewModel.deleteTask(Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true))
//
//        // Verify the result
//        Mockito.verify(dao).delete(any(Task::class.java))
//        Mockito.verify(observer).onChanged(anyList())
//    }
//
//    @Test
//    fun testUpdate() = testScope.runBlockingTest {
//        // Mock dependencies
//        val dao = mock(TaskDao::class.java)
//        `when`(dao.update(any(Task::class.java))).thenReturn(Unit)
//
//        val viewModel = TaskViewModel(application)
//        viewModel.addObserver(observer)
//
//        // Perform action
//        viewModel.updateTask(Task("Test Task"))
//
//        // Verify the result
//        Mockito.verify(dao).update(any(Task::class.java))
//        Mockito.verify(observer).onChanged(anyList())
//    }
//
//    // Helper functions for Mockito
//    private fun <T> any(type: Class<T>): T = Mockito.any(type)
//
//    private fun <T> anyList(): List<T> = anyList()
//
//    private fun <T> anyList(): List<T> = Mockito.anyList<T>()
//
//    private fun <T> mock(type: Class<T>): T = Mockito.mock(type)
//}
//
//
//import android.app.Application
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import com.example.calendarmanager.models.TaskViewModel
//import com.example.calendarmanager.models.dto.Task
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.Mockito.verify
//import org.mockito.MockitoAnnotations
//
//@ExperimentalCoroutinesApi
//class TaskViewModelTest {
//
//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//
//    private val testDispatcher = TestCoroutineDispatcher()
//
//    @Mock
//    lateinit var observer: Observer<List<Task>>
//
//    @Mock
//    lateinit var application: Application
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        Dispatchers.setMain(testDispatcher)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }
//
//    @Test
//    fun `testinsert`() {
//        val viewModel = TaskViewModel(application)
//        val task = Task(3516513,"To-do","Categorize your tasks based on urgency and importance","2019-01-04",true)
//
//        viewModel.insert(task)
//
//        // Verify that the insert function in the repository is called
//        verify(viewModel).insert(task)
//    }
////
////    @Test
////    fun `test delete`() {
////        val viewModel = TaskViewModel(application)
////        val task = Task(/* provide task details */)
////
////        viewModel.deleteTask(task)
////
////        // Verify that the delete function in the repository is called
////        verify(viewModel.repo).delete(task)
////    }
////
////    @Test
////    fun `test update`() {
////        val viewModel = TaskViewModel(application)
////        val task = Task(/* provide task details */)
////
////        viewModel.updateTask(task)
////
////        // Verify that the update function in the repository is called
////        verify(viewModel.repo).update(task)
////    }
//
//}
//
import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.example.calendarmanager.models.TaskViewModel
import com.example.calendarmanager.models.dto.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
//import org.robolectric.Robolectric

@ExperimentalCoroutinesApi
class TaskViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    // Replace with the appropriate testing framework annotations if using a different one.
    @Mock
    lateinit var observer: Observer<List<Task>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun testDeleteAndObserveTasks() = runBlockingTest {
        // Given
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = TaskViewModel(application)
        viewModel.addObserver(observer)

        val task = Task(3516513, "To-do", "Categorize your tasks based on urgency and importance", "2019-01-04", true)
        viewModel.insert(task)

        // When
        viewModel.deleteTask(task)

        // Then
        Mockito.verify(observer).onChanged(emptyList())
    }

    @Test
    fun testInsertAndObserveTasks_() = runBlockingTest {
        // Given
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = TaskViewModel(application)
        viewModel.addObserver(observer)

        val task = Task(3516513, "To-do", "Categorize your tasks based on urgency and importance", "2019-01-04", true)

        // When
        viewModel.insert(task)

        // Then
        Mockito.verify(observer).onChanged(listOf(task))
    }


    @Test
    fun testUpdateAndObserveTasks() = runBlockingTest {
        // Given
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = TaskViewModel(application)
        viewModel.addObserver(observer)

        val task = Task(3516513, "To-do", "Categorize your tasks based on urgency and importance", "2019-01-04", true)
        viewModel.insert(task)

        // When
        val updatedTask = task.copy(title = "Updated Task")
        viewModel.updateTask(updatedTask)

        // Then
        Mockito.verify(observer).onChanged(listOf(updatedTask))
    }
}
