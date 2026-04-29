package com.example.todoapp
import android.content.Context
import androidx.room.*

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    companion object {
        @Volatile private var INSTANCE: TodoDatabase? = null
        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java, "todo_database")
                    .build().also { INSTANCE = it }
            }
        }
    }
}
