package com.raywenderlich.ticky.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Factory(private val repository: TaskieRepository ) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(repository) as T
    }
}