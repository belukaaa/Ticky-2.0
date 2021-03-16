package com.raywenderlich.ticky.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raywenderlich.ticky.Taskie


@Dao
interface TaskieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task : Taskie)

    @Query("SELECT * FROM tasks ORDER BY taskId ASC ")
    fun readAllData(): LiveData<List<Taskie>>

    @Query("SELECT * FROM tasks WHERE selected = 0")
    fun readSelectedData() : LiveData<List<Taskie>>

    @Query("SELECT * FROM tasks WHERE selected = 1")
    fun readUnselectedData() : LiveData<List<Taskie>>

    @Query("SELECT * FROM tasks WHERE selected = 0 ORDER BY dateLong ASC ")
    fun readTasksByDate() : LiveData<List<Taskie>>

    @Query("SELECT * FROM tasks WHERE selected = 0 ORDER BY taskId ASC")
    fun readTasksByColor() : LiveData<List<Taskie>>

    @Query("SELECT * FROM tasks WHERE selected = 0 ORDER BY sortingColor ASC")
    fun sortTasksByColor() : LiveData<List<Taskie>>

    @Delete
    suspend fun deleteUser(task: List<Taskie>)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllUsers()

    @Update
    fun updateTasks(task : Taskie)

    @Update
    fun updateSelectedTasks(Task : List<Taskie>)



}