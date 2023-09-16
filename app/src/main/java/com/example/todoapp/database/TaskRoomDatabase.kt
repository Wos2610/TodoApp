package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Task::class, Category::class], version = 2, exportSchema = false)
public abstract class TaskRoomDatabase : RoomDatabase(){
    abstract fun taskDAO() : TaskDAO
    abstract fun categoryDAO() : CategoryDAO
    companion object{
        private var INSTANCE : TaskRoomDatabase? = null
        fun getDatabase(context: Context): TaskRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java,
                    "tasks_database"
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}