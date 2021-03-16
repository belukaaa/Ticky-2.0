package com.raywenderlich.ticky

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.fragments.HomeTaskScreenFragment
import kotlinx.android.synthetic.main.home_task_screen.*
import kotlinx.android.synthetic.main.home_task_screen.view.*
import kotlinx.coroutines.android.awaitFrame

class ItemTouchHelperCallback(private val listener: ItemTouchHelperListener) : ItemTouchHelper.Callback() {

   private lateinit var homeTaskScreenFragment: HomeTaskScreenFragment

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT, ItemTouchHelper.START or ItemTouchHelper.END)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
      return listener.onItemMove(recyclerView,viewHolder.adapterPosition,target.adapterPosition)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            listener.onItemDismiss(viewHolder, viewHolder.adapterPosition)
    }




    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            if(viewHolder is ItemSelectedListener){
                viewHolder.onItemSelected()



            }
        }
//        homeTaskScreenFragment = HomeTaskScreenFragment.getHomeTaskScrenFragment()
//        val i = homeTaskScreenFragment.selected_item_funcs
//        i.visibility = View.VISIBLE



    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if(viewHolder is ItemSelectedListener) {
            viewHolder.onItemCleared()


        }
    }


    override fun isItemViewSwipeEnabled(): Boolean {
        return super.isItemViewSwipeEnabled()
    }
}