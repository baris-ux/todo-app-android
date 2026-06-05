package com.example.todoapp
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TodoViewModel by viewModels()
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        adapter = TodoAdapter(
            onToggle = { viewModel.toggleComplete(it) },
            onDelete = { viewModel.delete(it) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        viewModel.allTodos.observe(this) { todos ->
            adapter.submitList(todos)
            val isEmpty = todos.isEmpty()
            binding.layoutEmpty.visibility = if (isEmpty) android.view.View.VISIBLE else android.view.View.GONE
            binding.recyclerView.visibility = if (isEmpty) android.view.View.GONE else android.view.View.VISIBLE
            val completed = todos.count { it.isCompleted }
            val total = todos.size
            binding.textCounter.text = "$completed / $total complétées"
            binding.progressBar.progress = if (total > 0) completed * 100 / total else 0
        }
        binding.editTextTodo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { addTodo(); true } else false
        }
        binding.fabAdd.setOnClickListener { addTodo() }
    }

    private fun addTodo() {
        val title = binding.editTextTodo.text.toString().trim()
        if (title.isNotEmpty()) { viewModel.insert(Todo(title = title)); binding.editTextTodo.text?.clear() }
        else Toast.makeText(this, "Veuillez entrer une tâche", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { menuInflater.inflate(R.menu.menu_main, menu); return true }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete_completed -> { viewModel.deleteCompleted(); Toast.makeText(this, "Tâches complétées supprimées", Toast.LENGTH_SHORT).show(); true }
        else -> super.onOptionsItemSelected(item)
    }
}
