package com.example.todoapp
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTodoBinding

class TodoAdapter(
    private val onToggle: (Todo) -> Unit,
    private val onDelete: (Todo) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TodoViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) = binding.apply {
            checkboxTodo.isChecked = todo.isCompleted
            textTodoTitle.text = todo.title
            if (todo.isCompleted) {
                textTodoTitle.paintFlags = textTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                textTodoTitle.alpha = 0.4f
                accentBar.setBackgroundColor(ContextCompat.getColor(root.context, R.color.completed_green))
            } else {
                textTodoTitle.paintFlags = textTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                textTodoTitle.alpha = 1.0f
                accentBar.setBackgroundColor(ContextCompat.getColor(root.context, R.color.primary))
            }
            checkboxTodo.setOnClickListener { onToggle(todo) }
            buttonDelete.setOnClickListener { onDelete(todo) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(a: Todo, b: Todo) = a.id == b.id
        override fun areContentsTheSame(a: Todo, b: Todo) = a == b
    }
}
