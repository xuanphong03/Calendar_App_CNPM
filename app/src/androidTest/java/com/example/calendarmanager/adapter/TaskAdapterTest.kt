import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.calendarmanager.adapter.TaskAdapter
import com.example.calendarmanager.models.dto.Task
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskAdapterTest {

    private lateinit var context: Context
    private lateinit var taskAdapter: TaskAdapter

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        taskAdapter = TaskAdapter(context, object : TaskAdapter.TaskOnClickListener {
            override fun onItemClick(task: Task) {
                // Handle item click for testing if needed
            }

            override fun onDeleteItemClick(task: Task) {
                // Handle delete item click for testing if needed
            }
        })
    }

    @Test
    fun getItemCount_shouldReturnZeroInitially() {
        assertEquals(0, taskAdapter.itemCount)
    }

    @Test
    fun updateList_shouldUpdateItemCount() {
        val task = Task(3516513, "To-do", "Categorize your tasks based on urgency and importance", "2019-01-04", true)
        val newList = listOf(task)
        taskAdapter.updateList(newList)
        assertEquals(newList.size, taskAdapter.itemCount)
    }

    @Test
    fun searchFilter_shouldFilterList() {
        val task1 = Task(3516513, "To-do", "Categorize your tasks based on urgency and importance", "2019-01-04", true)
        val task2 = Task(316513, "ugh", "Categorize your tasks based on urgency and importance", "2019-01-04", true)
        val task3 = Task(351613, "nah", "Categorize your tasks based on urgency and importance", "2019-01-04", true)

        val originalList = listOf(task1, task2, task3)
        taskAdapter.updateList(originalList)

        taskAdapter.searchFilter("To-do")

        // Assert that only matching items are in the list
        assertEquals(1, taskAdapter.itemCount)
    }

    @Test
    fun dayofTasks_shouldReturnCurrentList() {
        val task1 = Task(1, "Task 1", "Content 1", "2022-01-20", true)
        val task2 = Task(2, "Task 2", "Content 2", "2022-01-21", false)

        val originalList = listOf(task1, task2)
        taskAdapter.updateList(originalList)

        val result = taskAdapter.dayofTasks()

        assertEquals(originalList, result)
    }
}
