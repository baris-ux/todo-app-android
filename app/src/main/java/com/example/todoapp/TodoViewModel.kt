package com.example.todoapp
import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TodoRepository(TodoDatabase.getDatabase(application).todoDao())
    val allTodos: LiveData<List<Todo>> = repository.allTodos
    fun insert(todo: Todo) = viewModelScope.launch { repository.insert(todo) }
    fun update(todo: Todo) = viewModelScope.launch { repository.update(todo) }
    fun delete(todo: Todo) = viewModelScope.launch { repository.delete(todo) }
    fun deleteCompleted() = viewModelScope.launch { repository.deleteCompleted() }
    fun toggleComplete(todo: Todo) = viewModelScope.launch { repository.update(todo.copy(isCompleted = !todo.isCompleted)) }
}
