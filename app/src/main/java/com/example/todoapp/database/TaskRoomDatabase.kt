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
                    .addCallback(this.RoomCallback)
                    .build()

                INSTANCE = instance
                // return instance
                instance
            }
        }

        private val RoomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("INSERT INTO CATEGORY_TABLE (categoryTitle, imageName, completedPercentage) VALUES ('Default', 'null', 0.0)")
                db.execSQL("INSERT INTO CATEGORY_TABLE (categoryTitle, imageName, completedPercentage) VALUES ('Work', 'null', 0.0)")
                db.execSQL("INSERT INTO CATEGORY_TABLE (categoryTitle, imageName, completedPercentage) VALUES ('Study', 'null', 0.0)")
            }
        }
    }

}