package com.example.tugasmvvm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class TaskViewModel(
) : ViewModel() {
    private val repository: TaskRepository = TaskRepository()
    // Private mutable state: only the ViewModel can change it
    private val
    _
    tasks = MutableStateFlow(repository.loadInitialTasks())
    // Public read-only state: the UI can observe it but not modify it
    val tasks: StateFlow<List<Task>> =
        _
    tasks.asStateFlow()
    // Events from the UI come in as functions
    fun toggleDone(taskId: Int) {
        _
        tasks.update { list ->
            list.map { task ->
                if (task.id == taskId) task.copy(done = !task.done) else task
            }
        }
    }
    fun addTask(title: String) {
        if (title.isBlank()) return
        _
        tasks.update { list ->
            val nextId = (list.maxOfOrNull { it.id } ?: 0) + 1
            list + Task(id = nextId, title = title.trim())
        }
    }
}