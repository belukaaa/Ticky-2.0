package com.raywenderlich.ticky.taskrecyclerview

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.Taskie
import com.raywenderlich.ticky.repository.TaskViewModel
import kotlinx.android.synthetic.main.chechked_task_viewholder.view.*

class SelectedTaskAdapter : RecyclerView.Adapter<SelectedTaskAdapter.SelectTaskViewHolder>() {

    private var selectedList = ArrayList<Taskie>()
    private var unselectedList = ArrayList<Taskie>()


    inner class SelectTaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun unSelect(task : Taskie ){
            itemView.checkBox2.setOnClickListener {

//                unselectedList.add(task)
//                selectedList.remove(task)
//                listener?.unSelectSelected(unselectedList)

                task.selected = false

                listener1?.updateTaskie(task)



                notifyDataSetChanged()

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectTaskViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.chechked_task_viewholder , parent , false)


        view.selected_task.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG




        return SelectTaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectTaskViewHolder, position: Int) {

        val currentItem = (selectedList[position])

        holder.itemView.checkBox2.isChecked = true
        holder.itemView.selected_task.text = currentItem.title
        holder.unSelect(currentItem)





        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = selectedList.size

    fun setSelectedData(task: List<Taskie>) {
        this.selectedList.clear()
        this.selectedList.addAll(task)
        notifyDataSetChanged()

    }

    var listener : unSelectListener ? = null

    var listener1 : updateTaskie ? = null

    interface updateTaskie {
        fun updateTaskie(task: Taskie)
    }
    fun setOnUpdateListener(listener: updateTaskie){
        this.listener1 = listener
    }

    interface unSelectListener {
        fun unSelectSelected(list: List<Taskie>)
    }

    fun setOnCheckListener(listener: unSelectListener) {
        this.listener = listener
    }
}