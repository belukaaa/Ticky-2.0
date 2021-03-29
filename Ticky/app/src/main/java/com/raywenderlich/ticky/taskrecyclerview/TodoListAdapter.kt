package com.raywenderlich.ticky.taskrecyclerview

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.MySharedPreference
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.Taskie
import kotlinx.android.synthetic.main.todo_list_view_holder.view.*
import kotlin.coroutines.coroutineContext
import java.util.logging.Handler as Handler1


class TodoListAdapter (var click : (list : List<Taskie> , itemView: ArrayList<View> , position : ArrayList<Int> , state : Int) -> Unit
                       ,val updateTask: (task: Taskie) -> Unit) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> () {
    private var firstSelectedItem = ArrayList<Taskie>()
//    private lateinit var firstSelectedItemView : View
    var TaskieView = ArrayList<View>()
    private var uncheckedTaskieView = ArrayList<View>()
    var taskListPosition = ArrayList<Int>()
    private var taskListPosition1 = ArrayList<Int>()
    var taskList1 = ArrayList<Taskie>()
    private var taskList = ArrayList<Taskie>()
    private var checkedTaskList = ArrayList<Taskie>()
    private var evenOneTaskSelected = false
    var state = 0
    private var state1 = 1
    private var state2 = 2

    inner class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

        fun editTask(task: Taskie) {
            if (state == 0) {
                itemView.setOnClickListener {
                    listenerEditText?.editTask(task)
                }
            }
            else if(state == state1){

            }
        }
        fun setData(task: Taskie , holder: TodoListViewHolder , position: Int) {
            if (state == 0) {

                itemView.setOnLongClickListener {
                    if (!task.checked) {
                        task.checked = true
                        taskList1.add(task)
                        TaskieView.add(itemView)
                        taskListPosition.add(adapterPosition)
                        Log.e(
                            "lists",
                            "tasklist -> ${taskList1.size} , itemView -> ${TaskieView.size} , position -> ${taskListPosition.size}"
                        )
                        click.invoke(taskList1, TaskieView, taskListPosition, state)
                        state = 1
                        notifyDataSetChanged()
                    }

//                        Toast.makeText(
//                            itemView.context,
//                            "SELECTING MODE ACTIVATED",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        task.checked = true
//                        itemView.linearLayout.setBackgroundResource(R.drawable.selected_item)
//                        itemView.checkBox.setButtonDrawable(R.drawable.ic_rectangle_completed)
//                        taskList1.add(task)
//                        TaskieView.add(itemView)
//                        taskListPosition.add(adapterPosition)
//                        Log.e("lists" , "tasklist -> ${taskList1.size} , itemView -> ${TaskieView.size} , position -> ${taskListPosition.size}")
//                        click.invoke(taskList1, TaskieView, taskListPosition , state)
//                    Handler().postDelayed({
//                        Toast.makeText(itemView.context, "notifydatasetchanged", Toast.LENGTH_SHORT)
//                            .show()
//                        notifyDataSetChanged()
//                    }, 5000)

//                    } else {
//                        task.checked = false
//                        holder.itemView.checkBox.isChecked = false
//                        itemView.linearLayout.setBackgroundResource(R.drawable.viewholder_background)
//                        itemView.checkBox.setButtonDrawable(R.drawable.unselected_task_checkbox)
//                        taskList1.remove(task)
//                        TaskieView.remove(itemView)
//                        taskListPosition.remove(adapterPosition)
//                        click.invoke(taskList1, TaskieView, taskListPosition , state)
//                    }


                    true

                }
            } else if (state == state1) {
                itemView.setOnClickListener {
                    if (!task.checked) {
                        task.checked = true
                        taskList1.add(task)
                        TaskieView.add(itemView)
                        taskListPosition.add(adapterPosition)
                        click.invoke(taskList1, TaskieView, taskListPosition , state)
                        notifyDataSetChanged()
                    } else {
                        task.checked = false
                        holder.itemView.checkBox.isChecked = false
                        taskList1.remove(task)
                        TaskieView.remove(itemView)
                        taskListPosition.remove(adapterPosition)
                        click.invoke(taskList1, TaskieView, taskListPosition , state)
                        notifyDataSetChanged()
                    }

                }
//            itemView.checkBox.setOnClickListener {
////
////                checkedTaskList.add(task)
////                taskList.remove(task)
////                listener1?.unSelect(checkedTaskList)
//                task.selected = true
//
//                notifyDataSetChanged()
//
//            }
            }
        }

        fun checkTask(task: Taskie ){
            itemView.checkBox.setOnClickListener {
                task.checked = false
                task.selected = true
                taskListPosition.clear()
                TaskieView.clear()
                taskList1.clear()
                updateTask.invoke(task)
                state = 0

//                listener2?.updateTask(task , itemView)



            }
        }





    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        return TodoListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_list_view_holder, parent, false)
        )

    }
    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {

        val currentItem = (taskList[position])

        holder.checkTask(currentItem)

        if (taskListPosition.contains(position)){
            holder.itemView.linearLayout.setBackgroundResource(R.drawable.selected_item)
            holder.itemView.checkBox.setButtonDrawable(R.drawable.ic_rectangle_completed)
        }else
        {
            holder.itemView.linearLayout.setBackgroundResource(R.drawable.viewholder_background)

            holder.itemView.checkBox.setButtonDrawable(R.drawable.unselected_task_checkbox)
        }



        holder.editTask(taskList[position])

        holder.itemView.checkBox.isChecked = false
        holder.setData(currentItem , holder , position )
        holder.itemView.task.text = currentItem.title

        if (currentItem.color == "#ff453a") {
            holder.itemView.task_color_red.visibility = View.VISIBLE
        } else if (currentItem.color == "#ff9f0c") {
            holder.itemView.task_color_orange.visibility = View.VISIBLE
        } else if (currentItem.color == "#ffd50c") {
            holder.itemView.task_color_yellow.visibility = View.VISIBLE
        } else if (currentItem.color == "#32d74b") {
            holder.itemView.task_color_green.visibility = View.VISIBLE
        } else if (currentItem.color == "#64d2ff") {
            holder.itemView.task_color_pachtiblue.visibility = View.VISIBLE
        } else if (currentItem.color == "#0984ff") {
            holder.itemView.task_color_blue.visibility = View.VISIBLE
        } else if (currentItem.color == "#5e5ce6") {
            holder.itemView.task_color_muqiblue.visibility = View.VISIBLE
        } else if (currentItem.color == "#bf5af2") {
            holder.itemView.task_color_purple.visibility = View.VISIBLE
        } else if (currentItem.color == "#ff375f") {
            holder.itemView.task_color_rose.visibility = View.VISIBLE
        } else {
            holder.itemView.task_color_red.visibility = View.INVISIBLE
            holder.itemView.task_color_orange.visibility = View.INVISIBLE
            holder.itemView.task_color_yellow.visibility = View.INVISIBLE
            holder.itemView.task_color_pachtiblue.visibility = View.INVISIBLE
            holder.itemView.task_color_blue.visibility = View.INVISIBLE
            holder.itemView.task_color_muqiblue.visibility = View.INVISIBLE
            holder.itemView.task_color_green.visibility = View.INVISIBLE
            holder.itemView.task_color_purple.visibility = View.INVISIBLE
            holder.itemView.task_color_rose.visibility = View.INVISIBLE

        }

        if (currentItem.datetime == "") {
            holder.itemView.task_date.visibility = View.GONE
        } else {
            holder.itemView.task_date.visibility = View.VISIBLE
            holder.itemView.task_date.text = currentItem.datetime
        }


        holder.setIsRecyclable(false)

    }


    override fun getItemCount(): Int = taskList.size

    override fun getItemId(position: Int): Long = position.toLong()


    fun setData(task: List<Taskie>) {
            this.taskList.clear()
            this.taskList.addAll(task)
            notifyDataSetChanged()
    }

    var listener22 : UpdateAllTasks? = null
    var listener2 : UpdateTask? = null
    var listenerEditText : EditTask? = null

    interface EditTask {
        fun editTask(task: Taskie)
    }
    fun editTask(listenerEditTask: EditTask){
        this.listenerEditText = listenerEditTask
    }

    interface UpdateAllTasks {
        fun updateAllTasks(itemView: View)
    }
    interface UpdateTask {
        fun updateTask(task: Taskie , itemView: View)
    }
    fun updateAllTasks(listener22 : UpdateAllTasks){
        this.listener22 = listener22
    }
    fun updateTask(listener2 : UpdateTask){
        this.listener2 = listener2
    }



    interface IOnClick {
        fun onChecked(list: List<Taskie>, itemView: ArrayList<View> )
    }

    interface Iunselect {
        fun unSelect(list: List<Taskie>)
    }
    var listener: IOnClick? = null

    var listener21 : deleteUserByAnim? = null

    fun deleteUseByAnim(listener21 : deleteUserByAnim) {
        this.listener21 = listener21
    }

    interface deleteUserByAnim {
        fun deleteUserByAnim(task: Taskie, returnView: View)
    }

    var listener1 : Iunselect? = null

    fun setOnCheckListener(listener: IOnClick) {
        this.listener = listener
    }
    fun setCheckedTaskListener(listener1 : Iunselect){
        this.listener1 = listener1
    }

}
//    override fun onItemMove(
//        recyclerView: RecyclerView,
//        fromPosition: Int,
//        toPosition: Int
//    ): Boolean {
//        if(fromPosition < toPosition)
//            for(i in fromPosition until toPosition){
//                Collections.swap(taskList, i , i +1)
//            }else {
//            for (i in fromPosition downTo toPosition + 1){
//                Collections.swap(taskList,i , i -1 )
//            }
//        }
//        notifyItemMoved(fromPosition , toPosition)
//        return true
//    }
