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
                db.execSQL("INSERT INTO CATEGORY_TABLE (categoryTitle, imageName, completedPercentage) VALUES ('Default', 'https://img.freepik.com/free-vector/man-moving-clock-arrows-managing-time_74855-10894.jpg?w=900&t=st=1695984581~exp=1695985181~hmac=f252b6c6c03916af4bdc01f3cd1f5956537ad38dde58857d3d84b550648cdf1a', 0.0)")
                db.execSQL("INSERT INTO CATEGORY_TABLE (categoryTitle, imageName, completedPercentage) VALUES ('Work', 'https://img.freepik.com/free-vector/partners-holding-big-jigsaw-puzzle-pieces_74855-5278.jpg?w=996&t=st=1695984583~exp=1695985183~hmac=8ca4845fa268966b30ea65caf3dd207a3a37f3846aff30bb2d4e784d4cb3742a', 0.0)")
                db.execSQL("INSERT INTO CATEGORY_TABLE (categoryTitle, imageName, completedPercentage) VALUES ('Study', 'https://img.freepik.com/free-vector/focused-tiny-people-reading-books_74855-5836.jpg?w=826&t=st=1695984525~exp=1695985125~hmac=e99eaaf6b17b3dc473c13c79820ed930909f1e6c3ad6df0f908d46b703a51c23', 0.0)")
            }
        }
    }

}