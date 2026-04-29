package com.example.todoapp
import androidx.lifecycle.LiveData

class TodoRepository(private val dao: TodoDao) {
    val allTodos: LiveData<List<Todo>> = dao.getAllTodos()
    suspend fun insert(todo: Todo) = dao.insert(todo)
    suspend fun update(todo: Todo) = dao.update(todo)
    suspend fun delete(todo: Todo) = dao.delete(todo)
    suspend fun deleteCompleted() = dao.deleteCompleted()
}
