package com.raywenderlich.ticky

import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.taskrecyclerview.TodoListAdapter

interface ItemTouchHelperListener {
    fun onItemMove(recyclerView: RecyclerView, fromPosition: Int , toPosition: Int) : Boolean
    fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position : Int)

    
}