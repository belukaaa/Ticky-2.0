package com.raywenderlich.ticky.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raywenderlich.ticky.Taskie
import com.raywenderlich.ticky.db.dao.TaskieDao

const val DATABASE_VERSION = 8
const val DATABASE_NAME = "task_database"
@Database(
    entities = [Taskie::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class TaskieDatabase : RoomDatabase (){
    abstract  fun taskieDao():TaskieDao
    companion object{
        @Volatile
        private var instance:TaskieDatabase?=null
        fun getDatabase(context: Context) : TaskieDatabase {

            if(instance == null){
                instance = Room.databaseBuilder(
                    context,
                    TaskieDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .build()

            }
            return  instance!!

        }
    }
}