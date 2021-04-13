package com.raywenderlich.ticky.fragments

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.MySharedPreference
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.Taskie
import com.raywenderlich.ticky.db.TaskieDatabase
import com.raywenderlich.ticky.db.dao.TaskieDao
import com.raywenderlich.ticky.repository.Factory
import com.raywenderlich.ticky.repository.TaskViewModel
import com.raywenderlich.ticky.repository.TaskieRepository
import com.raywenderlich.ticky.taskrecyclerview.SelectedTaskAdapter
import com.raywenderlich.ticky.taskrecyclerview.TodoListAdapter
import kotlinx.android.synthetic.main.home_task_screen.*
import kotlinx.android.synthetic.main.home_task_screen.view.*
import kotlinx.android.synthetic.main.todo_list_view_holder.view.*
import java.util.*
import kotlin.collections.ArrayList


class HomeTaskScreenFragment(val EditTask :(task : Taskie) -> Unit): Fragment() , SelectedTaskAdapter.unSelectListener , TodoListAdapter.UpdateTask , SelectedTaskAdapter.updateTaskie , TodoListAdapter.EditTask  {

    var taskie : Taskie = Taskie(0,"title",sortingColor = 10)

    private var List = ArrayList<Taskie>()
    private var checkedList = ArrayList<Taskie>()
    private var selectedList = ArrayList<Taskie>()
    private var sortBy : String = ""

    private lateinit var selectedTaskRecyclerView: RecyclerView
    private lateinit var recyclerView: RecyclerView
    var adapter: TodoListAdapter = TodoListAdapter(click = { list, itemView, position , state ->
        okey(
            list,
            itemView,
            position,
            state
        )
    }, updateTask = { task -> checkTask(task) })
    private lateinit var selectedAdapter: SelectedTaskAdapter
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var factory: Factory
    private lateinit var taskieDao: TaskieDao
    private lateinit var taskDB : TaskieDatabase
    private lateinit var repository: TaskieRepository
    private lateinit var mySharedPref : MySharedPreference
    private lateinit var viewHolder: TodoListAdapter.TodoListViewHolder

//    companion object {
//        fun getHomeTaskScrenFragment():HomeTaskScreenFragment {
//            return  HomeTaskScreenFragment(EditTask = {task -> editTask(task) } )
//        }
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.home_task_screen, container, false)



        initViewModel(view.context)




        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate()
        val heroImageView = view.recycle
        ViewCompat.setTransitionName(heroImageView, "hero_image")

        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in2)
        val headerTitle = vnaxotraiqneba11 as ConstraintLayout
        val recycler = recycle as RecyclerView
        val recycler1 = checked_recycler as RecyclerView
        val textView = textView7 as TextView
        val imageViewer = imageView5 as ImageView
        val textView2 = textView8 as TextView
        val imageViewer2 = imageView6 as ImageView;

        textView.startAnimation(fadeIn)
        imageViewer.startAnimation(fadeIn)
        imageViewer2.startAnimation(fadeIn)
        textView2.startAnimation(fadeIn)
        headerTitle.startAnimation(ttb)
        recycler.startAnimation(fadeIn)
        recycler1.startAnimation(fadeIn)



        selectedAdapter = SelectedTaskAdapter()
        selectedAdapter.setOnCheckListener(this)
        selectedAdapter.setOnUpdateListener(this)
        adapter.editTask(this)

        selectedTaskRecyclerView = view.checked_recycler
        selectedTaskRecyclerView.adapter = selectedAdapter
        selectedTaskRecyclerView.layoutManager = LinearLayoutManager(context)


        recyclerView = view.recycle
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)



        val sortItems = mySharedPref.taskExe()

        if (sortItems == "")  {
            mTaskViewModel.getSelectedData().observe(viewLifecycleOwner, Observer { user ->
                adapter.setData(user)
            })
           view.textView5.text = "sort by"
        }
        else if (sortItems == "Due date") {
            mTaskViewModel.getTasksByDate().observe(viewLifecycleOwner, Observer {
              adapter.setData(it)
            })
           view.textView5.text = sortItems


        }
        else if (sortItems == "Color label") {
            mTaskViewModel.sortTasksByColor().observe(viewLifecycleOwner, Observer {
                adapter.setData(it)
            })
            view.textView5.text = sortItems
        }
        else if (sortItems == "Date added") {
            mTaskViewModel.getSelectedData().observe(viewLifecycleOwner, Observer {
                adapter.setData(it)
            })
           view.textView5.text = sortItems
        }

        mTaskViewModel.getUnSelectedData().observe(viewLifecycleOwner, Observer { user ->
            selectedAdapter.setSelectedData(user)
        })


        add_task.setOnClickListener {
            adapter.state = 0
            adapter.taskList1.clear()
            adapter.taskListPosition.clear()
            adapter.TaskieView.clear()
            listener?.homeTaskScrenButton()
        }



//
//
//        cancel_selecting.setOnClickListener {
//            List.clear()
//
//            mTaskViewModel.getSelectedData().observe(viewLifecycleOwner, Observer { user ->
//                adapter.setData(user)
//            })
//            mTaskViewModel.getUnSelectedData().observe(viewLifecycleOwner, Observer { user ->
//                selectedAdapter.setSelectedData(user)
//            })
//            hideDeleteDonebttns()
//        }


//        done_button.setOnClickListener {
//            List.forEach { task ->
//                task.selected = true
//                task.checked = false
//
//                mTaskViewModel.updateTask(task)
//
//            }
//            List.clear()
//            adapter.notifyDataSetChanged()
//            hideDeleteDonebttns()
//        }
        view.textView5.setOnClickListener {
            showDialog()
        }
        setSortingName()



    }


    private fun setSortingName() {
        val sortBy = mySharedPref.taskExe()
        if (sortBy != "") {
            view?.textView5?.text = sortBy
        }
    }




    private fun showDialog(){


        val dialog = CustomDialogFragment()




        dialog.show(childFragmentManager, "CustomDialog")



    }
    private fun initViewModel(context: Context) {
        taskDB = TaskieDatabase.getDatabase(context)
        taskieDao = taskDB.taskieDao()
        repository = TaskieRepository(taskieDao)
        factory = Factory(repository)
        mTaskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)
        mySharedPref = MySharedPreference(context)
    }
//    companion object {
//        fun getHomeTaskScrenFragment():HomeTaskScreenFragment {
//            return  HomeTaskScreenFragment(EditTask = )
//        }
//    }

    private fun setDefaultColors(){
        Monday1.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday21.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday41.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday51.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday61.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday71.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday81.setTextColor(resources.getColor(R.color.default_calendar_days_color))
    }
    private fun setDefaultPosition(){
        imageView11.visibility = View.INVISIBLE
        imageView21.visibility = View.INVISIBLE
        imageView31.visibility = View.INVISIBLE
        imageView41.visibility = View.INVISIBLE
        imageView51.visibility = View.INVISIBLE
        imageView61.visibility = View.INVISIBLE
        imageView71.visibility = View.INVISIBLE
    }

    private fun enableBlue(int: Int){

        if (int == 2){
            setDefaultPosition()
            setDefaultColors()
            imageView11.visibility = View.VISIBLE
            Monday1.setTextColor(resources.getColor(R.color.selectedItemColor))
            Monday1.alpha = 1f
        }
        else if (int == 3) {
            setDefaultColors()
            setDefaultPosition()
            imageView21.visibility = View.VISIBLE
            monday21.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday21.alpha = 1f
        }
        else if (int == 4) {
            setDefaultColors()
            setDefaultPosition()
            imageView31.visibility = View.VISIBLE
            monday41.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday41.alpha = 1f
        }
        else if (int == 5) {
            setDefaultColors()
            setDefaultPosition()
            imageView41.visibility = View.VISIBLE
            monday51.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday51.alpha = 1f
        }
        else if (int == 6){
            setDefaultColors()
            setDefaultPosition()
            imageView51.visibility = View.VISIBLE
            monday61.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday61.alpha = 1f
        }
        else if (int == 0) {
            setDefaultColors()
            setDefaultPosition()
            imageView61.visibility = View.VISIBLE
            monday71.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday71.alpha = 1f
        }
        else if (int == 1){
            setDefaultColors()
            setDefaultPosition()
            imageView71.visibility = View.VISIBLE
            monday81.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday81.alpha = 1f
        }

    }
    private fun setDate(){

    val c = Calendar.getInstance()
    val month = c.get(Calendar.MONTH)
    val year = c.get(Calendar.YEAR)
    val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
    val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

// dayofweek = 2 monday
    if (dayOfWeek == 2) {

        enableBlue(dayOfWeek)
        view?.monday1?.text = dayOfMonth.toString()
        view?.tuesday1?.text = (dayOfMonth + 1).toString()
        view?.wednesday1?.text = (dayOfMonth + 2).toString()
        view?.thursday1?.text = (dayOfMonth + 3).toString()
        view?.friday1?.text = (dayOfMonth + 4).toString()
        view?.saturday1?.text = (dayOfMonth + 5).toString()
        view?.sunday1?.text = (dayOfMonth + 6).toString()

    }
    if (dayOfWeek ==3 ){
        enableBlue(dayOfWeek)

        view?.monday1?.text = (dayOfMonth - 1).toString()
        view?.tuesday1?.text = (dayOfMonth).toString()
        view?.wednesday1?.text = (dayOfMonth + 1).toString()
        view?.thursday1?.text = (dayOfMonth + 2).toString()
        view?.friday1?.text = (dayOfMonth + 3).toString()
        view?.saturday1?.text = (dayOfMonth + 4).toString()
        view?.sunday1?.text = (dayOfMonth + 5).toString()
    }
    if (dayOfWeek == 4 ){
        enableBlue(dayOfWeek)

        view?.monday1?.text = (dayOfMonth - 2).toString()
        view?.tuesday1?.text = (dayOfMonth - 1).toString()
        view?.wednesday1?.text = (dayOfMonth ).toString()
        view?.thursday1?.text = (dayOfMonth + 1).toString()
        view?.friday1?.text = (dayOfMonth + 2).toString()
        view?.saturday1?.text = (dayOfMonth + 3).toString()
        view?.sunday1?.text = (dayOfMonth + 4).toString()
    }
    if (dayOfWeek ==5 ){
        enableBlue(dayOfWeek)

        view?.monday1?.text = (dayOfMonth - 3).toString()
        view?.tuesday1?.text = (dayOfMonth - 2).toString()
        view?.wednesday1?.text = (dayOfMonth - 1).toString()
        view?.thursday1?.text = (dayOfMonth ).toString()
        view?.friday1?.text = (dayOfMonth + 1 ).toString()
        view?.saturday1?.text = (dayOfMonth + 2).toString()
        view?.sunday1?.text = (dayOfMonth + 3).toString()
    }
    if (dayOfWeek == 6 ){
        enableBlue(dayOfWeek)

        view?.monday1?.text = (dayOfMonth - 4).toString()
        view?.tuesday1?.text = (dayOfMonth - 3).toString()
        view?.wednesday1?.text = (dayOfMonth - 2).toString()
        view?.thursday1?.text = (dayOfMonth - 1).toString()
        view?.friday1?.text = (dayOfMonth ).toString()
        view?.saturday1?.text = (dayOfMonth + 1).toString()
        view?.sunday1?.text = (dayOfMonth + 2).toString()
    }
    if (dayOfWeek == 0  ){
        enableBlue(dayOfWeek)

        view?.monday1?.text = (dayOfMonth - 5).toString()
        view?.tuesday1?.text = (dayOfMonth - 4).toString()
        view?.wednesday1?.text = (dayOfMonth -3).toString()
        view?.thursday1?.text = (dayOfMonth -2).toString()
        view?.friday1?.text = (dayOfMonth -1).toString()
        view?.saturday1?.text = (dayOfMonth).toString()
        view?.sunday1?.text = (dayOfMonth + 1).toString()
    }
    if (dayOfWeek == 1 ) {
        enableBlue(dayOfWeek)

        view?.monday1?.text = (dayOfMonth - 6).toString()
        view?.tuesday1?.text = (dayOfMonth - 5).toString()
        view?.wednesday1?.text = (dayOfMonth - 4).toString()
        view?.thursday1?.text = (dayOfMonth - 3).toString()
        view?.friday1?.text = (dayOfMonth - 2).toString()
        view?.saturday1?.text = (dayOfMonth - 1).toString()
        view?.sunday1?.text = (dayOfMonth).toString()
        }

        if (dayOfWeek == 2) {
            if (dayOfMonth == 26) {
                if (month%2 == 1){ //month%2 == 1 ნიშნავს რო 31 ით მთავრდება თვე
                    view?.monday1?.text = dayOfMonth.toString()
                    view?.tuesday1?.text = (dayOfMonth + 1).toString()
                    view?.wednesday1?.text = (dayOfMonth + 2).toString()
                    view?.thursday1?.text = (dayOfMonth + 3).toString()
                    view?.friday1?.text = (dayOfMonth + 4).toString()
                    view?.saturday1?.text = (dayOfMonth + 5).toString()
                    view?.sunday1?.text = (1).toString()
                }
                else if (month%2 == 0){ // month%2 == 0 ნიშნავს რო 30 ით მთავრდება თვე

                    view?.monday1?.text = dayOfMonth.toString()
                    view?.tuesday1?.text = (dayOfMonth + 1).toString()
                    view?.wednesday1?.text = (dayOfMonth + 2).toString()
                    view?.thursday1?.text = (dayOfMonth + 3).toString()
                    view?.friday1?.text = (dayOfMonth + 4).toString()
                    view?.saturday1?.text = (1).toString()
                    view?.sunday1?.text = (2).toString()
                }
            }
            else if (dayOfMonth == 27) {
                if (month%2 == 1){

                    view?.monday1?.text = dayOfMonth.toString()
                    view?.tuesday1?.text = (dayOfMonth + 1).toString()
                    view?.wednesday1?.text = (dayOfMonth + 2).toString()
                    view?.thursday1?.text = (dayOfMonth + 3).toString()
                    view?.friday1?.text = (dayOfMonth + 4).toString()
                    view?.saturday1?.text = (1).toString()
                    view?.sunday1?.text = (2).toString()
                }
                else if (month%2 == 0){

                    view?.monday1?.text = dayOfMonth.toString()
                    view?.tuesday1?.text = (dayOfMonth + 1).toString()
                    view?.wednesday1?.text = (dayOfMonth + 2).toString()
                    view?.thursday1?.text = (dayOfMonth + 3).toString()
                    view?.friday1?.text = (1).toString()
                    view?.saturday1?.text = (2).toString()
                    view?.sunday1?.text = (3).toString()
                }
            }
            else if (dayOfMonth == 28){
                    if (month%2 == 0){

                        view?.monday1?.text = dayOfMonth.toString()
                        view?.tuesday1?.text = (dayOfMonth + 1).toString()
                        view?.wednesday1?.text = (dayOfMonth + 2).toString()
                        view?.thursday1?.text = (1).toString()
                        view?.friday1?.text = (2).toString()
                        view?.saturday1?.text = (3).toString()
                        view?.sunday1?.text = (4).toString()
                    }
                else if (month%2 == 1){

                        view?.monday1?.text = dayOfMonth.toString()
                        view?.tuesday1?.text = (dayOfMonth + 1).toString()
                        view?.wednesday1?.text = (dayOfMonth + 2).toString()
                        view?.thursday1?.text = (dayOfMonth + 3).toString()
                        view?.friday1?.text = (1).toString()
                        view?.saturday1?.text = (2).toString()
                        view?.sunday1?.text = (3).toString()
                    }
            }
            else if (dayOfMonth == 29) {
                if (month % 2 == 0) {
                    view?.monday1?.text = dayOfMonth.toString()
                    view?.tuesday1?.text = (dayOfMonth + 1).toString()
                    view?.wednesday1?.text = (1).toString()
                    view?.thursday1?.text = (2).toString()
                    view?.friday1?.text = (3).toString()
                    view?.saturday1?.text = (4).toString()
                    view?.sunday1?.text = (5).toString()
                }
                if (month % 2 == 1) {
                    view?.monday1?.text = dayOfMonth.toString()
                    view?.tuesday1?.text = (dayOfMonth + 1).toString()
                    view?.wednesday1?.text = (31).toString()
                    view?.thursday1?.text = (1).toString()
                    view?.friday1?.text = (2).toString()
                    view?.saturday1?.text = (3).toString()
                    view?.sunday1?.text = (4).toString()
                }
            }
            else if (dayOfMonth == 30){
                if (month%2==0){
                    view?.monday1?.text = dayOfMonth.toString()
                    view?.tuesday1?.text = (1).toString()
                    view?.wednesday1?.text = (2).toString()
                    view?.thursday1?.text = (3).toString()
                    view?.friday1?.text = (4).toString()
                    view?.saturday1?.text = (5).toString()
                    view?.sunday1?.text = (6).toString()
                }
                if (month % 2 == 1) {
                    view?.monday1?.text = dayOfMonth.toString()
                    view?.tuesday1?.text = (dayOfMonth + 1).toString()
                    view?.wednesday1?.text = (1).toString()
                    view?.thursday1?.text = (2).toString()
                    view?.friday1?.text = (3).toString()
                    view?.saturday1?.text = (4).toString()
                    view?.sunday1?.text = (5).toString()
                }
            }
            else if (dayOfMonth == 31){
                view?.monday1?.text = dayOfMonth.toString()
                view?.tuesday1?.text = (1).toString()
                view?.wednesday1?.text = (2).toString()
                view?.thursday1?.text = (3).toString()
                view?.friday1?.text = (4).toString()
                view?.saturday1?.text = (5).toString()
                view?.sunday1?.text = (6).toString()
            }
        }

    if (dayOfWeek == 3){
        if (dayOfMonth == 26){
            if (month%2 == 1){

                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth).toString()
                view?.wednesday1?.text = (dayOfMonth + 1).toString()
                view?.thursday1?.text = (dayOfMonth + 2).toString()
                view?.friday1?.text = (dayOfMonth + 3).toString()
                view?.saturday1?.text = (30).toString()
                view?.sunday1?.text = (31).toString()
            }
            else if (month%2 == 0){

                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth ).toString()
                view?.wednesday1?.text = (dayOfMonth + 1).toString()
                view?.thursday1?.text = (dayOfMonth + 2).toString()
                view?.friday1?.text = (29).toString()
                view?.saturday1?.text = (30).toString()
                view?.sunday1?.text = (1).toString()
            }
        }
        else if (dayOfMonth == 27){
            if (month%2 == 1){
                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth).toString()
                view?.wednesday1?.text = (dayOfMonth + 1).toString()
                view?.thursday1?.text = (dayOfMonth + 2).toString()
                view?.friday1?.text = (dayOfMonth + 3).toString()
                view?.saturday1?.text = (31).toString()
                view?.sunday1?.text = (1).toString()
            }
            else if (month%2 == 0){
                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth).toString()
                view?.wednesday1?.text = (dayOfMonth + 1).toString()
                view?.thursday1?.text = (dayOfMonth + 2).toString()
                view?.friday1?.text = (30).toString()
                view?.saturday1?.text = (1).toString()
                view?.sunday1?.text = (2).toString()
            }
        }
        else if (dayOfMonth == 28){
            if (month%2 == 1){

                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth).toString()
                view?.wednesday1?.text = (dayOfMonth + 1).toString()
                view?.thursday1?.text = (dayOfMonth + 2).toString()
                view?.friday1?.text = (dayOfMonth + 3).toString()
                view?.saturday1?.text = (1).toString()
                view?.sunday1?.text = (2).toString()
            }
            else if (month%2 == 0){

                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth ).toString()
                view?.wednesday1?.text = (dayOfMonth + 1).toString()
                view?.thursday1?.text = (dayOfMonth + 2).toString()
                view?.friday1?.text = (1).toString()
                view?.saturday1?.text = (2).toString()
                view?.sunday1?.text = (3).toString()
            }
        }
        else if (dayOfMonth == 29){
            if (month%2 == 1){

                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth).toString()
                view?.wednesday1?.text = (dayOfMonth + 1).toString()
                view?.thursday1?.text = (dayOfMonth + 2).toString()
                view?.friday1?.text = (1).toString()
                view?.saturday1?.text = (2).toString()
                view?.sunday1?.text = (3).toString()
            }
            else if (month%2 == 0){

                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth ).toString()
                view?.wednesday1?.text = (dayOfMonth + 1).toString()
                view?.thursday1?.text = (1).toString()
                view?.friday1?.text = (2).toString()
                view?.saturday1?.text = (3).toString()
                view?.sunday1?.text = (4).toString()
            }
        }
        else if (dayOfMonth == 30){
            if (month%2 == 1){
                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth).toString()
                view?.wednesday1?.text = (dayOfMonth + 1).toString()
                view?.thursday1?.text = (1).toString()
                view?.friday1?.text = (2).toString()
                view?.saturday1?.text = (3).toString()
                view?.sunday1?.text = (3+1).toString()
            }
            else if (month%2 == 0){
                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth ).toString()
                view?.wednesday1?.text = ( 1).toString()
                view?.thursday1?.text = ( 2).toString()
                view?.friday1?.text = (3).toString()
                view?.saturday1?.text = (4).toString()
                view?.sunday1?.text = (5).toString()
            }
        }
        else if (dayOfMonth == 31){
                view?.monday1?.text = (dayOfMonth-1).toString()
                view?.tuesday1?.text = (dayOfMonth).toString()
                view?.wednesday1?.text = (1).toString()
                view?.thursday1?.text = ( 2).toString()
                view?.friday1?.text = ( 3).toString()
                view?.saturday1?.text = (4).toString()
                view?.sunday1?.text = (5).toString()
        }
    }
        if (dayOfWeek == 4){
            if (dayOfMonth == 27){

                    view?.monday1?.text = (dayOfMonth - 2).toString()
                    view?.tuesday1?.text = (dayOfMonth - 1).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (dayOfMonth + 2).toString()
                    view?.saturday1?.text = (dayOfMonth + 3).toString()
                    view?.sunday1?.text = (1).toString()

            }
            else if (dayOfMonth == 28){
                if (month%2 == 1){
                    view?.monday1?.text = (dayOfMonth - 2).toString()
                    view?.tuesday1?.text = (dayOfMonth - 1).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (dayOfMonth + 2).toString()
                    view?.saturday1?.text = (dayOfMonth + 3).toString()
                    view?.sunday1?.text = (1).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (dayOfMonth - 2).toString()
                    view?.tuesday1?.text = (dayOfMonth - 1).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (dayOfMonth + 2).toString()
                    view?.saturday1?.text = (1).toString()
                    view?.sunday1?.text = (2).toString()
                }
            }
            else if (dayOfMonth == 29) {
                if (month%2 == 1){
                    view?.monday1?.text = (dayOfMonth - 2).toString()
                    view?.tuesday1?.text = (dayOfMonth - 1).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (dayOfMonth + 2).toString()
                    view?.saturday1?.text = (1).toString()
                    view?.sunday1?.text = (2).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (dayOfMonth - 2).toString()
                    view?.tuesday1?.text = (dayOfMonth - 1).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (1).toString()
                    view?.saturday1?.text = (2).toString()
                    view?.sunday1?.text = (3).toString()
                }
            }
            else if (dayOfMonth == 30){
                if (month%2 == 1){
                    view?.monday1?.text = (dayOfMonth - 2).toString()
                    view?.tuesday1?.text = (dayOfMonth - 1).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (1).toString()
                    view?.saturday1?.text = (2).toString()
                    view?.sunday1?.text = (3).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (dayOfMonth - 2).toString()
                    view?.tuesday1?.text = (dayOfMonth - 1).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = ( 1).toString()
                    view?.friday1?.text = ( 2).toString()
                    view?.saturday1?.text = ( 3).toString()
                    view?.sunday1?.text = (4).toString()
                }
            }
            else if (dayOfMonth == 31) {

                    view?.monday1?.text = (dayOfMonth - 2).toString()
                    view?.tuesday1?.text = (dayOfMonth - 1).toString()
                    view?.wednesday1?.text = (dayOfMonth).toString()
                    view?.thursday1?.text = (1).toString()
                    view?.friday1?.text = (2).toString()
                    view?.saturday1?.text = (3).toString()
                    view?.sunday1?.text = (4).toString()

            }
        }
        if (dayOfWeek == 5){
            if (dayOfMonth == 28){
                if (month%2 == 0){
                    view?.monday1?.text = (dayOfMonth - 3).toString()
                    view?.tuesday1?.text = (dayOfMonth - 2).toString()
                    view?.wednesday1?.text = (dayOfMonth - 1).toString()
                    view?.thursday1?.text = (dayOfMonth ).toString()
                    view?.friday1?.text = (dayOfMonth + 1 ).toString()
                    view?.saturday1?.text = (dayOfMonth + 2).toString()
                    view?.sunday1?.text = (1).toString()
                }
            }
            else if (dayOfMonth == 29){
                if (month%2 == 1){
                    view?.monday1?.text = (dayOfMonth - 3).toString()
                    view?.tuesday1?.text = (dayOfMonth - 2).toString()
                    view?.wednesday1?.text = (dayOfMonth - 1).toString()
                    view?.thursday1?.text = (dayOfMonth ).toString()
                    view?.friday1?.text = (dayOfMonth + 1 ).toString()
                    view?.saturday1?.text = (dayOfMonth + 2).toString()
                    view?.sunday1?.text = (1).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (dayOfMonth - 3).toString()
                    view?.tuesday1?.text = (dayOfMonth - 2).toString()
                    view?.wednesday1?.text = (dayOfMonth - 1).toString()
                    view?.thursday1?.text = (dayOfMonth ).toString()
                    view?.friday1?.text = (dayOfMonth + 1 ).toString()
                    view?.saturday1?.text = (1).toString()
                    view?.sunday1?.text = (2).toString()
                }
            }
            else if (dayOfMonth == 30){
                if (month%2 == 1){
                    view?.monday1?.text = (dayOfMonth - 3).toString()
                    view?.tuesday1?.text = (dayOfMonth - 2).toString()
                    view?.wednesday1?.text = (dayOfMonth - 1).toString()
                    view?.thursday1?.text = (dayOfMonth ).toString()
                    view?.friday1?.text = (dayOfMonth + 1 ).toString()
                    view?.saturday1?.text = (1).toString()
                    view?.sunday1?.text = (2).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (dayOfMonth - 3).toString()
                    view?.tuesday1?.text = (dayOfMonth - 2).toString()
                    view?.wednesday1?.text = (dayOfMonth - 1).toString()
                    view?.thursday1?.text = (dayOfMonth ).toString()
                    view?.friday1?.text = ( 1 ).toString()
                    view?.saturday1?.text = (2).toString()
                    view?.sunday1?.text = (3).toString()
                }
            }
            else if (dayOfMonth == 31){
                if (month%2 == 1) {
                    view?.monday1?.text = (dayOfMonth - 3).toString()
                    view?.tuesday1?.text = (dayOfMonth - 2).toString()
                    view?.wednesday1?.text = (dayOfMonth - 1).toString()
                    view?.thursday1?.text = (dayOfMonth).toString()
                    view?.friday1?.text = (1).toString()
                    view?.saturday1?.text = (2).toString()
                    view?.sunday1?.text = (3).toString()
                }
            }
        }
        if (dayOfWeek == 6){
            if (dayOfMonth == 29) {
                if (month % 2 == 31) {
                    view?.monday1?.text = (dayOfMonth - 4).toString()
                    view?.tuesday1?.text = (dayOfMonth - 3).toString()
                    view?.wednesday1?.text = (dayOfMonth - 2).toString()
                    view?.thursday1?.text = (dayOfMonth - 1).toString()
                    view?.friday1?.text = (dayOfMonth).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (dayOfMonth + 2).toString()
                }
            }
            if (dayOfMonth == 30){
                if (month%2 == 1){
                    view?.monday1?.text = (dayOfMonth - 4).toString()
                    view?.tuesday1?.text = (dayOfMonth - 3).toString()
                    view?.wednesday1?.text = (dayOfMonth - 2).toString()
                    view?.thursday1?.text = (dayOfMonth - 1).toString()
                    view?.friday1?.text = (dayOfMonth ).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (1).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (dayOfMonth - 4).toString()
                    view?.tuesday1?.text = (dayOfMonth - 3).toString()
                    view?.wednesday1?.text = (dayOfMonth - 2).toString()
                    view?.thursday1?.text = (dayOfMonth - 1).toString()
                    view?.friday1?.text = (dayOfMonth ).toString()
                    view?.saturday1?.text = (1).toString()
                    view?.sunday1?.text = (2).toString()
                }
            }
            else if (dayOfMonth == 31){
                view?.monday1?.text = (dayOfMonth - 4).toString()
                view?.tuesday1?.text = (dayOfMonth - 3).toString()
                view?.wednesday1?.text = (dayOfMonth - 2).toString()
                view?.thursday1?.text = (dayOfMonth - 1).toString()
                view?.friday1?.text = (dayOfMonth ).toString()
                view?.saturday1?.text = (1).toString()
                view?.sunday1?.text = (2).toString()
            }
        }
        if (dayOfWeek == 0){
            if(dayOfMonth == 30) {
                if (month % 2 == 1) {
                    view?.monday1?.text = (dayOfMonth - 5).toString()
                    view?.tuesday1?.text = (dayOfMonth - 4).toString()
                    view?.wednesday1?.text = (dayOfMonth - 3).toString()
                    view?.thursday1?.text = (dayOfMonth - 2).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (31).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (dayOfMonth - 5).toString()
                    view?.tuesday1?.text = (dayOfMonth - 4).toString()
                    view?.wednesday1?.text = (dayOfMonth - 3).toString()
                    view?.thursday1?.text = (dayOfMonth - 2).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (1).toString()
                }
            }
            if (dayOfMonth == 31){
                if (month % 2 == 1) {
                    view?.monday1?.text = (dayOfMonth - 5).toString()
                    view?.tuesday1?.text = (dayOfMonth - 4).toString()
                    view?.wednesday1?.text = (dayOfMonth - 3).toString()
                    view?.thursday1?.text = (dayOfMonth - 2).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (1).toString()
                }

            }

        }
        if(dayOfWeek == 1){
            if (dayOfMonth == 1){
                if (month%2 == 0){
                    view?.monday1?.text = (25).toString()
                    view?.tuesday1?.text = (26).toString()
                    view?.wednesday1?.text = (27).toString()
                    view?.thursday1?.text = (28).toString()
                    view?.friday1?.text = (29).toString()
                    view?.saturday1?.text = (30).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (26).toString()
                    view?.tuesday1?.text = (27).toString()
                    view?.wednesday1?.text = (28).toString()
                    view?.thursday1?.text = (29).toString()
                    view?.friday1?.text = (30).toString()
                    view?.saturday1?.text = (31).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 2){
                if (month%2 == 0){
                    view?.monday1?.text = (26).toString()
                    view?.tuesday1?.text = (27).toString()
                    view?.wednesday1?.text = (28).toString()
                    view?.thursday1?.text = (29).toString()
                    view?.friday1?.text = (30).toString()
                    view?.saturday1?.text = (dayOfMonth - 1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (27).toString()
                    view?.tuesday1?.text = (28).toString()
                    view?.wednesday1?.text = (29).toString()
                    view?.thursday1?.text = (30).toString()
                    view?.friday1?.text = (31).toString()
                    view?.saturday1?.text = (dayOfMonth-1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 3){
                if (month%2 == 0){
                    view?.monday1?.text = (27).toString()
                    view?.tuesday1?.text = (28).toString()
                    view?.wednesday1?.text = (29).toString()
                    view?.thursday1?.text = (30).toString()
                    view?.friday1?.text = (dayOfMonth -2).toString()
                    view?.saturday1?.text = (dayOfMonth - 1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (28).toString()
                    view?.tuesday1?.text = (29).toString()
                    view?.wednesday1?.text = (30).toString()
                    view?.thursday1?.text = (31).toString()
                    view?.friday1?.text = (dayOfMonth - 2).toString()
                    view?.saturday1?.text = (dayOfMonth - 1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 4){
                if (month%2 == 0){
                    view?.monday1?.text = (28).toString()
                    view?.tuesday1?.text = (29).toString()
                    view?.wednesday1?.text = (30).toString()
                    view?.thursday1?.text = (dayOfMonth - 3).toString()
                    view?.friday1?.text = (dayOfMonth - 2).toString()
                    view?.saturday1?.text = (dayOfMonth -1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (29).toString()
                    view?.tuesday1?.text = (30).toString()
                    view?.wednesday1?.text = (31).toString()
                    view?.thursday1?.text = (dayOfMonth - 3).toString()
                    view?.friday1?.text = (dayOfMonth-2).toString()
                    view?.saturday1?.text = (dayOfMonth - 1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 5){
                if (month%2 == 0){
                    view?.monday1?.text = (29).toString()
                    view?.tuesday1?.text = (30).toString()
                    view?.wednesday1?.text = (dayOfMonth - 4).toString()
                    view?.thursday1?.text = (dayOfMonth - 3).toString()
                    view?.friday1?.text = (dayOfMonth - 2).toString()
                    view?.saturday1?.text = (dayOfMonth -1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (31).toString()
                    view?.wednesday1?.text = (dayOfMonth - 4).toString()
                    view?.thursday1?.text = (dayOfMonth - 3).toString()
                    view?.friday1?.text = (dayOfMonth-2).toString()
                    view?.saturday1?.text = (dayOfMonth - 1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 6){
                if (month%2 == 0){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (dayOfMonth - 5).toString()
                    view?.wednesday1?.text = (dayOfMonth -4).toString()
                    view?.thursday1?.text = (dayOfMonth - 3).toString()
                    view?.friday1?.text = (dayOfMonth - 2).toString()
                    view?.saturday1?.text = (dayOfMonth -1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (31).toString()
                    view?.tuesday1?.text = (dayOfMonth - 5).toString()
                    view?.wednesday1?.text = (dayOfMonth - 4).toString()
                    view?.thursday1?.text = (dayOfMonth - 3).toString()
                    view?.friday1?.text = (dayOfMonth-2).toString()
                    view?.saturday1?.text = (dayOfMonth - 1).toString()
                    view?.sunday1?.text = (dayOfMonth).toString()
                }
            }

        }
        if (dayOfWeek == 0){
            if (dayOfMonth == 1){
                if (month%2 == 0){
                    view?.monday1?.text = (26).toString()
                    view?.tuesday1?.text = (27).toString()
                    view?.wednesday1?.text = (28).toString()
                    view?.thursday1?.text = (29).toString()
                    view?.friday1?.text = (30).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (27).toString()
                    view?.tuesday1?.text = (28).toString()
                    view?.wednesday1?.text = (29).toString()
                    view?.thursday1?.text = (30).toString()
                    view?.friday1?.text = (31).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
            }
            if (dayOfMonth == 2){
                if (month%2 == 0){
                    view?.monday1?.text = (27).toString()
                    view?.tuesday1?.text = (28).toString()
                    view?.wednesday1?.text = (29).toString()
                    view?.thursday1?.text = (30).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (28).toString()
                    view?.tuesday1?.text = (29).toString()
                    view?.wednesday1?.text = (30).toString()
                    view?.thursday1?.text = (31).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
            }
            if (dayOfMonth == 3){
                if (month%2 == 0){
                    view?.monday1?.text = (28).toString()
                    view?.tuesday1?.text = (29).toString()
                    view?.wednesday1?.text = (30).toString()
                    view?.thursday1?.text = (dayOfMonth -2).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (29).toString()
                    view?.tuesday1?.text = (30).toString()
                    view?.wednesday1?.text = (31).toString()
                    view?.thursday1?.text = (dayOfMonth - 2).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
            }
            if (dayOfMonth == 4){
                if (month%2 == 0){
                    view?.monday1?.text = (29).toString()
                    view?.tuesday1?.text = (30).toString()
                    view?.wednesday1?.text = (dayOfMonth - 3).toString()
                    view?.thursday1?.text = (dayOfMonth - 2).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (31).toString()
                    view?.wednesday1?.text = (dayOfMonth - 3).toString()
                    view?.thursday1?.text = (dayOfMonth -2).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
            }
            if (dayOfMonth == 5){
                if (month%2 == 0){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (dayOfMonth -4).toString()
                    view?.wednesday1?.text = (dayOfMonth - 3).toString()
                    view?.thursday1?.text = (dayOfMonth - 2).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (31).toString()
                    view?.tuesday1?.text = (dayOfMonth - 4).toString()
                    view?.wednesday1?.text = (dayOfMonth - 3).toString()
                    view?.thursday1?.text = (dayOfMonth -2).toString()
                    view?.friday1?.text = (dayOfMonth - 1).toString()
                    view?.saturday1?.text = (dayOfMonth).toString()
                    view?.sunday1?.text = (dayOfMonth + 1).toString()
                }
            }

        }
        if(dayOfWeek == 6){
            if (dayOfMonth == 1){
                if (month%2 == 0){
                    view?.monday1?.text = (27).toString()
                    view?.tuesday1?.text = (28).toString()
                    view?.wednesday1?.text = (29).toString()
                    view?.thursday1?.text = (30).toString()
                    view?.friday1?.text = (dayOfMonth).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (dayOfMonth + 2).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (28).toString()
                    view?.tuesday1?.text = (29).toString()
                    view?.wednesday1?.text = (30).toString()
                    view?.thursday1?.text = (31).toString()
                    view?.friday1?.text = (dayOfMonth ).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (dayOfMonth + 2).toString()
                }
            }
            if (dayOfMonth == 2){
                if (month%2 == 0){
                    view?.monday1?.text = (28).toString()
                    view?.tuesday1?.text = (29).toString()
                    view?.wednesday1?.text = (30).toString()
                    view?.thursday1?.text = (dayOfMonth - 1).toString()
                    view?.friday1?.text = (dayOfMonth).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (dayOfMonth + 2).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (29).toString()
                    view?.tuesday1?.text = (30).toString()
                    view?.wednesday1?.text = (31).toString()
                    view?.thursday1?.text = (dayOfMonth - 1).toString()
                    view?.friday1?.text = (dayOfMonth ).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (dayOfMonth + 2).toString()
                }
            }
            if(dayOfMonth == 3){
                if (month%2 == 0){
                    view?.monday1?.text = (29).toString()
                    view?.tuesday1?.text = (30).toString()
                    view?.wednesday1?.text = (dayOfMonth - 2).toString()
                    view?.thursday1?.text = (dayOfMonth - 1).toString()
                    view?.friday1?.text = (dayOfMonth).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (dayOfMonth + 2).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (31).toString()
                    view?.wednesday1?.text = (dayOfMonth - 2).toString()
                    view?.thursday1?.text = (dayOfMonth - 1).toString()
                    view?.friday1?.text = (dayOfMonth ).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (dayOfMonth + 2).toString()
                }
            }
            if (dayOfMonth == 4){
                if (month%2 == 0){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (dayOfMonth - 3).toString()
                    view?.wednesday1?.text = (dayOfMonth - 2).toString()
                    view?.thursday1?.text = (dayOfMonth - 1).toString()
                    view?.friday1?.text = (dayOfMonth).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (dayOfMonth + 2).toString()
                }
                else if (month%2 == 1){
                    view?.monday1?.text = (31).toString()
                    view?.tuesday1?.text = (dayOfMonth - 3).toString()
                    view?.wednesday1?.text = (dayOfMonth - 2).toString()
                    view?.thursday1?.text = (dayOfMonth - 1).toString()
                    view?.friday1?.text = (dayOfMonth ).toString()
                    view?.saturday1?.text = (dayOfMonth + 1).toString()
                    view?.sunday1?.text = (dayOfMonth + 2).toString()
                }
            }

        }
        if (dayOfWeek == 5){
            if(dayOfMonth == 1){
                if (month%2 == 1){
                    view?.monday1?.text = (29).toString()
                    view?.tuesday1?.text = (30).toString()
                    view?.wednesday1?.text = (31).toString()
                    view?.thursday1?.text = (dayOfMonth ).toString()
                    view?.friday1?.text = (dayOfMonth + 1 ).toString()
                    view?.saturday1?.text = (dayOfMonth + 2).toString()
                    view?.sunday1?.text = (dayOfMonth + 3).toString()
                }
                if (month%2 == 0){
                    view?.monday1?.text = (28).toString()
                    view?.tuesday1?.text = (29).toString()
                    view?.wednesday1?.text = (30).toString()
                    view?.thursday1?.text = (dayOfMonth ).toString()
                    view?.friday1?.text = (dayOfMonth + 1 ).toString()
                    view?.saturday1?.text = (dayOfMonth + 2).toString()
                    view?.sunday1?.text = (dayOfMonth + 3).toString()
                }
            }
            else if(dayOfMonth == 2){

                    if (month%2 == 1){
                        view?.monday1?.text = (30).toString()
                        view?.tuesday1?.text = (31).toString()
                        view?.wednesday1?.text = (dayOfMonth - 1).toString()
                        view?.thursday1?.text = (dayOfMonth ).toString()
                        view?.friday1?.text = (dayOfMonth + 1 ).toString()
                        view?.saturday1?.text = (dayOfMonth + 2).toString()
                        view?.sunday1?.text = (dayOfMonth + 3).toString()
                    }
                    if (month%2 == 0){
                        view?.monday1?.text = (29).toString()
                        view?.tuesday1?.text = (30).toString()
                        view?.wednesday1?.text = (dayOfMonth - 1).toString()
                        view?.thursday1?.text = (dayOfMonth ).toString()
                        view?.friday1?.text = (dayOfMonth + 1 ).toString()
                        view?.saturday1?.text = (dayOfMonth + 2).toString()
                        view?.sunday1?.text = (dayOfMonth + 3).toString()
                    }
            }
            else if (dayOfMonth == 3){

                if (month%2 == 1){
                    view?.monday1?.text = (31).toString()
                    view?.tuesday1?.text = (dayOfMonth -2).toString()
                    view?.wednesday1?.text = (dayOfMonth -1).toString()
                    view?.thursday1?.text = (dayOfMonth ).toString()
                    view?.friday1?.text = (dayOfMonth + 1 ).toString()
                    view?.saturday1?.text = (dayOfMonth + 2).toString()
                    view?.sunday1?.text = (dayOfMonth + 3).toString()
                }
                if (month%2 == 0){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (dayOfMonth -2).toString()
                    view?.wednesday1?.text = (dayOfMonth - 1).toString()
                    view?.thursday1?.text = (dayOfMonth ).toString()
                    view?.friday1?.text = (dayOfMonth + 1 ).toString()
                    view?.saturday1?.text = (dayOfMonth + 2).toString()
                    view?.sunday1?.text = (dayOfMonth + 3).toString()
                }
            }

        }
        if (dayOfWeek ==4){
            if (dayOfMonth == 1){
                if (month%2 == 1){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (31).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (dayOfMonth + 2).toString()
                    view?.saturday1?.text = (dayOfMonth + 3).toString()
                    view?.sunday1?.text = (dayOfMonth + 4).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (29).toString()
                    view?.tuesday1?.text = (30).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (dayOfMonth + 2).toString()
                    view?.saturday1?.text = (dayOfMonth + 3).toString()
                    view?.sunday1?.text = (dayOfMonth + 4).toString()
                }
            }
            else  if (dayOfMonth == 2){
                if (month%2 == 1){
                    view?.monday1?.text = (31).toString()
                    view?.tuesday1?.text = (dayOfMonth -1).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (dayOfMonth + 2).toString()
                    view?.saturday1?.text = (dayOfMonth + 3).toString()
                    view?.sunday1?.text = (dayOfMonth + 4).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (dayOfMonth - 1).toString()
                    view?.wednesday1?.text = (dayOfMonth ).toString()
                    view?.thursday1?.text = (dayOfMonth + 1).toString()
                    view?.friday1?.text = (dayOfMonth + 2).toString()
                    view?.saturday1?.text = (dayOfMonth + 3).toString()
                    view?.sunday1?.text = (dayOfMonth + 4).toString()
                }
            }
        }

        if (dayOfWeek == 3){
            if (dayOfMonth == 1){
                if (month%2 == 1){
                    view?.monday1?.text = (31).toString()
                    view?.tuesday1?.text = (dayOfMonth).toString()
                    view?.wednesday1?.text = (dayOfMonth + 1).toString()
                    view?.thursday1?.text = (dayOfMonth + 2).toString()
                    view?.friday1?.text = (dayOfMonth + 3).toString()
                    view?.saturday1?.text = (dayOfMonth + 4).toString()
                    view?.sunday1?.text = (dayOfMonth + 5).toString()
                }
                else if (month%2 == 0){
                    view?.monday1?.text = (30).toString()
                    view?.tuesday1?.text = (dayOfMonth).toString()
                    view?.wednesday1?.text = (dayOfMonth + 1).toString()
                    view?.thursday1?.text = (dayOfMonth + 2).toString()
                    view?.friday1?.text = (dayOfMonth + 3).toString()
                    view?.saturday1?.text = (dayOfMonth + 4).toString()
                    view?.sunday1?.text = (dayOfMonth + 5).toString()
                }
            }
        }


    if (month == 0) {
        datetime1.text = ("January, $year")
    }
    if (month == 1) {
        datetime1.text = ("February, $year")
    }
    if (month == 2) {
        datetime1.text = ("March, $year")
    }
    if (month == 3) {
        datetime1.text = ("April, $year")
    }
    if (month == 4) {
        datetime1.text = ("May, $year")
    }
    if (month == 5) {
        datetime1.text = ("June, $year")
    }
    if (month == 6) {
        datetime1.text = ("July, $year")
    }
    if (month == 7) {
        datetime1.text = ("August, $year")
    }
    if (month == 8) {
        datetime1.text = ("September, $year")
    }
    if (month == 9) {
        datetime1.text = ("October, $year")
    }
    if (month == 10) {
        datetime1.text = ("November, $year")
    }
    if (month == 11) {
        datetime1.text = ("December, $year")
    }


}

    private var listener : HomeTaskScreenButton? =null
    private var listener1 : deletingUser? = null

    interface deletingUser {
        fun deletingUser()
    }

    interface HomeTaskScreenButton {
        fun homeTaskScrenButton()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as HomeTaskScreenButton
//        listener1 = context as deletingUser
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    //    listener1 = null
    }
    private fun hideDeleteDonebttns(){
        val done_button_anim = AnimationUtils.loadAnimation(context,R.anim.done_button_anim_down)
        val delete_button_anim = AnimationUtils.loadAnimation(context,R.anim.delete_done_button_down)
        val x_button_anim = AnimationUtils.loadAnimation(context,R.anim.x_button_anim_down)

        done_button.startAnimation(done_button_anim)
        delete_task_button.startAnimation(delete_button_anim)
        cancel_selecting.startAnimation(x_button_anim)

        delete_button_anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
               delete_task_button.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        done_button_anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                done_button.visibility = View.INVISIBLE
                selected_item_funcs.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        x_button_anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                cancel_selecting.visibility = View.INVISIBLE

            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })


    }
    private fun showDeleteDonebtttns() {
        val done_button_anim = AnimationUtils.loadAnimation(context, R.anim.done_button_anim_up)
        val delete_button_anim = AnimationUtils.loadAnimation(context, R.anim.delete_button_anim_up)
        val x_button_anim = AnimationUtils.loadAnimation(context, R.anim.x_button_anim_up)



        done_button.startAnimation(done_button_anim)
        delete_task_button.startAnimation(delete_button_anim)
        cancel_selecting.startAnimation(x_button_anim)

        x_button_anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                cancel_selecting.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }


        })
        done_button_anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                done_button.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }


        })

        delete_button_anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                delete_task_button.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }


        })

        selected_item_funcs.visibility = View.VISIBLE

    }
    private fun checkTask(task: Taskie) {

        mTaskViewModel.updateTask(task)
        hideDeleteDonebttns()


    }


    private fun okey(list: List<Taskie>, itemView: ArrayList<View>, position: ArrayList<Int>, state : Int){
        this.List = list as ArrayList<Taskie>
        var arrayOfItemViews   = ArrayList<View>()
        done_button.setOnClickListener {
            List.forEach { task ->
                task.selected = true
                task.checked = false
                mTaskViewModel.updateTask(task)
            }
            list.clear()
            itemView.clear()
            position.clear()
            adapter.state = 0
            adapter.notifyDataSetChanged()
            hideDeleteDonebttns()
        }

        position.forEach {
            val view = recyclerView.findViewHolderForAdapterPosition(it)
            val itemView = view?.itemView

            arrayOfItemViews.add(itemView!!)

        }

        cancel_selecting.setOnClickListener {
           list.forEach {
               it.checked = false
           }
            adapter.state = 0
            position.clear()
            itemView.clear()
            list.clear()
            adapter.notifyDataSetChanged()
            hideDeleteDonebttns()
        }
        delete_task_button.setOnClickListener {
            position.forEach {
                val view = recyclerView.findViewHolderForAdapterPosition(it)
                val itemView = view?.itemView

                arrayOfItemViews.add(itemView!!)

            }

            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out)
            arrayOfItemViews.forEach { v ->
                v.startAnimation(anim)
            }

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
              }
                override fun onAnimationEnd(animation: Animation?) {
                    list.forEach { task ->
                        task.checked = false
                    }
                    arrayOfItemViews.forEach {
                        it.alpha = 0F
                    }
                    mTaskViewModel.deleteUser(list)
                    Log.e("Delete" , "$list $List")
                    position.clear()
                    arrayOfItemViews.clear()
                    adapter.state = 0
                    hideDeleteDonebttns()
                }
                override fun onAnimationRepeat(animation: Animation?) {

                }

            })

        }

        if (position.isEmpty()){
            hideDeleteDonebttns()
            adapter.taskieView.clear()
            adapter.TaskieView.clear()
            adapter.state = 0
            adapter.notifyDataSetChanged()

        }else if (selected_item_funcs.isVisible){

        }

        else {
            showDeleteDonebtttns()
        }

    }

    override fun unSelectSelected(list: List<Taskie>) {

        this.selectedList = list as ArrayList<Taskie>

    }

    override fun updateTask(task: Taskie, itemView: View) {
        mTaskViewModel.updateTask(task)
        hideDeleteDonebttns()
    }

    override fun updateTaskie(task: Taskie) {
        mTaskViewModel.updateTask(task)
        hideDeleteDonebttns()
    }

    override fun editTask(task: Taskie) {

        taskie = task

        EditTask.invoke(task)



    }


}