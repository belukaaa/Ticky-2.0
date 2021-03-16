package com.raywenderlich.ticky.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.ticky.Taskie
import com.raywenderlich.ticky.db.dao.TaskieDao
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class TaskieRepository(private val taskDao: TaskieDao) {

    fun getData() : LiveData<List<Taskie>> {
       return taskDao.readAllData()
   }
    fun getSelectedData() : LiveData<List<Taskie>> {
        return  taskDao.readSelectedData()
    }
    fun getUnSelectedData() : LiveData<List<Taskie>>{
        return taskDao.readUnselectedData()
    }
    fun getTaskByDate() : LiveData<List<Taskie>>{
        return taskDao.readTasksByDate()
    }
    fun getTasksByColor() : LiveData<List<Taskie>>{
        return taskDao.readTasksByColor()
    }
    fun sortTasksByColor() : LiveData<List<Taskie>>{
        return taskDao.sortTasksByColor()
    }

     fun addTask(task : Taskie) {
       CoroutineScope(Dispatchers.IO).launch {
           taskDao.addTask(task)
       }
    }

//    suspend fun getRowCountt() : ArrayList<Taskie> {
//        return taskDao.getRowCountt()
//    }


    suspend fun deleteUser(task: List<Taskie>){
        taskDao.deleteUser(task)
    }

    suspend fun deleteAllUser() {
        taskDao.deleteAllUsers()
    }

    fun updateTask(task: Taskie) {
        CoroutineScope(Dispatchers.IO).launch{
            taskDao.updateTasks(task)
        }
    }






//    fun getCount(): Int = runBlocking {
//        val count = async {
//            taskDao.getCount()
//        }
//        count.start()
//        count.await()
//    }


}