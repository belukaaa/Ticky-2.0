package com.raywenderlich.ticky.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
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
        setDatee()

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



        mTaskViewModel.getSelectedData().observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })
        mTaskViewModel.getUnSelectedData().observe(viewLifecycleOwner, Observer { user ->
            selectedAdapter.setSelectedData(user)
        })


        add_task.setOnClickListener {
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
            var data = showDialog()
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
    private fun setDatee(){
        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)


        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

        monday1.text = (dayOfMonth - 2).toString()
        tuesday1.text = (dayOfMonth - 1).toString()
        wednesday1.text = dayOfMonth.toString()
        thursday1.text = (dayOfMonth + 1) . toString()
        friday1.text = (dayOfMonth + 2).toString()
        saturday1.text = (dayOfMonth + 3).toString()
        sunday1.text = (dayOfMonth + 4).toString()
        // 0- jan , 1 - feb , 2 march , 3 april , 4 may , 5 june , 6 jule , 7 , august , 8 sept , 9 octo , 10 nov , 11 dec

        if (dayOfMonth == 1 && month == 0  )  {
            monday1.text = "30"
            tuesday1.text = "31"
        }
        else if (dayOfMonth == 1 && month == 2  )  {
            monday1.text = "27"
            tuesday1.text = "28"
        }

        else if (dayOfMonth == 1 && month == 4  )  {
            monday1.text = "29"
            tuesday1.text = "30"
        }

        else if (dayOfMonth == 1 && month == 6  )  {
            monday1.text = "29"
            tuesday1.text = "30"
        }

        else if (dayOfMonth == 1 && month == 7  )  {
            monday1.text = "30"
            tuesday1.text = "31"
        }

        else if (dayOfMonth == 1 && month == 9  )  {
            monday1.text = "29"
            tuesday1.text = "30"
        }

        else if (dayOfMonth == 1 && month == 11 )  {
            monday1.text = "29"
            tuesday1.text = "30"
        }
        else if (dayOfMonth == 2 && month == 0  )  {
            monday1.text = "31"
            tuesday1.text = "1"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31
        else if (dayOfMonth == 2 && month == 2  )  {
            monday1.text = "28"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 4  )  {
            monday1.text = "30"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 6  )  {
            monday1.text = "30"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 7  )  {
            monday1.text = "31"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 9  )  {
            monday1.text = "30"
            tuesday1.text = "1"
        }

        else if (dayOfMonth == 2 && month == 11  )  {
            monday1.text = "30"
            tuesday1.text = "1"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31

        else if (dayOfMonth == 1 && month == 1){
            tuesday1.text = "31"
            monday1.text = "30"
        }
        else if (dayOfMonth == 2 && month == 1){
            tuesday1.text = "1"
            monday1.text = "31"
        }
        else if (dayOfMonth == 1 && month == 3){
            tuesday1.text = "31"
            monday1.text = "30"
        }
        else if (dayOfMonth == 1 && month == 5){
            tuesday1.text = "31"
            monday1.text = "30"
        }
        else if (dayOfMonth == 1 && month == 8){
            tuesday1.text = "31"
            monday1.text = "30"
        }
        else if (dayOfMonth == 1 && month == 10){
            tuesday1.text = "31"
            monday1.text = "30"
        }
        else if (dayOfMonth == 2 && month == 3){
            tuesday1.text = "1"
            monday1.text = "31"
        }
        else if (dayOfMonth == 2 && month == 5){
            tuesday1.text = "1"
            monday1.text = "31"
        }
        else if (dayOfMonth == 2 && month == 8){
            tuesday1.text = "1"
            monday1.text = "31"
        }
        else if (dayOfMonth == 2 && month == 10){
            tuesday1.text = "1"
            monday1.text = "31"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31

        else if (dayOfMonth == 28 && month == 0) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 2 ) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 4) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 6) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 7) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 9) {
            sunday1.text = "1"
        }

        else if (dayOfMonth == 28 && month == 11) {
            sunday1.text = "1"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31

        else if (dayOfMonth == 29 && month == 0) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 2) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 4) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 6) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 7) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 9) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 11) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 0) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 2) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 4) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }

        else if (dayOfMonth == 30 && month == 6) {

            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 7) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 9) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 11) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 0) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 2) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 4) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 6) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 7) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 9) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 31 && month == 11) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31

        else if (dayOfMonth == 27 && month == 3) {
            sunday1.text = "1"
        }
        else if (dayOfMonth == 27 && month == 5) {
            sunday1.text = "1"
        }
        else if (dayOfMonth == 27 && month == 8) {
            sunday1.text = "1"
        }
        else if (dayOfMonth == 27 && month == 10) {
            sunday1.text = "1"
        }
        else if (dayOfMonth == 28 && month == 3) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 28 && month == 5) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 28 && month == 8) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 28 && month == 10) {
            sunday1.text = "2"
            saturday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 3) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 5) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 8) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 29 && month == 10) {
            sunday1.text = "3"
            saturday1.text = "2"
            friday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 3) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 5) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 8) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if (dayOfMonth == 30 && month == 10) {
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        else if(dayOfMonth == 28 && month == 1){
            sunday1.text = "4"
            saturday1.text = "3"
            friday1.text = "2"
            thursday1.text = "1"
        }
        if (dayOfWeek == 0) {
                Monday1.text = "T"
                monday21.text = "F"
                monday41.text = "S"
                monday51.text = "S"
                monday61.text = "M"
                monday71.text = "T"
                monday81.text = "W"

            } else if (dayOfWeek == 1) {
                Monday1.text = "F"
                monday21.text = "S"
                monday41.text = "S"
                monday51.text = "M"
                monday61.text = "T"
                monday71.text = "W"
                monday81.text = "T"

            } else if (dayOfWeek == 2) {
                Monday1.text = "S"
                monday21.text = "S"
                monday41.text = "M"
                monday51.text = "T"
                monday61.text = "W"
                monday71.text = "T"
                monday81.text = "F"
            } else if (dayOfWeek == 3) {
                Monday1.text = "S"
                monday21.text = "M"
                monday41.text = "T"
                monday51.text = "W"
                monday61.text = "T"
                monday71.text = "F"
                monday81.text = "S"
            } else if (dayOfWeek == 4) {
                Monday1.text = "M"
                monday21.text = "T"
                monday41.text = "W"
                monday51.text = "T"
                monday61.text = "F"
                monday71.text = "S"
                monday81.text = "S"

            } else if (dayOfWeek == 5) {
                Monday1.text = "T"
                monday21.text = "W"
                monday41.text = "T"
                monday51.text = "F"
                monday61.text = "S"
                monday71.text = "S"
                monday81.text = "M"
            } else if (dayOfWeek == 6) {
                Monday1.text = "W"
                monday21.text = "T"
                monday41.text = "F"
                monday51.text = "S"
                monday61.text = "S"
                monday71.text = "M"
                monday81.text = "T"
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
        val done_button_anim = AnimationUtils.loadAnimation(context, R.anim.done_button_anim_down)
        val delete_button_anim = AnimationUtils.loadAnimation(
            context,
            R.anim.delete_done_button_down
        )
        val x_button_anim = AnimationUtils.loadAnimation(context, R.anim.x_button_anim_down)

        done_button.startAnimation(done_button_anim)
        delete_task_button.startAnimation(delete_button_anim)
        cancel_selecting.startAnimation(x_button_anim)

        x_button_anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                selected_item_funcs.visibility = View.INVISIBLE


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

        selected_item_funcs.visibility = View.VISIBLE

    }
    private fun checkTask(task: Taskie) {

        mTaskViewModel.updateTask(task)
        hideDeleteDonebttns()


    }


    private fun okey(list: List<Taskie>, itemView: ArrayList<View>, position: ArrayList<Int>, state : Int){
        this.List = list as ArrayList<Taskie>

        done_button.setOnClickListener {
            List.forEach { task ->
                task.selected = true
                task.checked = false

                mTaskViewModel.updateTask(task)

            }
            adapter.state = 0
            adapter.notifyDataSetChanged()
            List.clear()
            hideDeleteDonebttns()
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
            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out)
            itemView.forEach { v ->
                v.startAnimation(anim)
            }

            hideDeleteDonebttns()

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {




//                        if (itemView[0] == recyclerView[0]) {
//                            unchecked.forEach { v ->
//                                v.startAnimation(anim2)
//                               imageView6.startAnimation(anim3)
//                                textView8.startAnimation(anim4)
//
//                            }
//
//
//                        }

                }

                override fun onAnimationEnd(animation: Animation?) {
                    list.forEach { task ->

                        task.checked = false

                    }

                    itemView.forEach { v ->
                        v.alpha = 0F
                    }
                    mTaskViewModel.deleteUser(list)

//                    unchecked.forEach {
//                        it.startAnimation(anim2)
//                    }

                    Log.e("Delete" , "$list $List")

                    adapter.state = 0



                }

                override fun onAnimationRepeat(animation: Animation?) {

                }

            })



            Log.e("Delete2 " , "$list $List")

        }

        if (position.isEmpty()){
            hideDeleteDonebttns()
            adapter.state = 0
            adapter.notifyDataSetChanged()

        }else if (selected_item_funcs.isVisible){

        }

        else {
            showDeleteDonebtttns()
        }

    }


//    override fun onChecked(
//        list: List<Taskie>,
//        itemView: ArrayList<View>
//    ) {
//        this.List = list as ArrayList<Taskie>
//
//
//        delete_task_button.setOnClickListener {
//            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out)
//            itemView.forEach { v ->
//                v.startAnimation(anim)
//            }
//
//           hideDeleteDonebttns()
//
//            anim.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationStart(animation: Animation?) {
//
//
//
//
////                        if (itemView[0] == recyclerView[0]) {
////                            unchecked.forEach { v ->
////                                v.startAnimation(anim2)
////                               imageView6.startAnimation(anim3)
////                                textView8.startAnimation(anim4)
////
////                            }
////
////
////                        }
//
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    list.forEach { task ->
//
//                        task.checked = false
//
//                    }
//
//                    itemView.forEach { v ->
//                        v.alpha = 0F
//                    }
//                    mTaskViewModel.deleteUser(list)
//
////                    unchecked.forEach {
////                        it.startAnimation(anim2)
////                    }
//
//                    Log.e("Delete" , "$list $List")
//
//
//
//
//                }
//
//                override fun onAnimationRepeat(animation: Animation?) {
//
//                }
//
//            })
//
//            list.forEach {
//                adapter.notifyItemRemoved(it.taskId)
//            }
//
//            Log.e("Delete2 " , "$list $List")
//
//    }
//
//        if (List.isEmpty()){
//hideDeleteDonebttns()
//
//        }else if (selected_item_funcs.isVisible){
//
//        }
//
//        else {
//showDeleteDonebtttns()
//        }
//
//        Log.e("Delete 1" , "$list $List")
//    }
//    override fun unSelect(list: List<Taskie>) {
//
//        this.checkedList = list as ArrayList<Taskie>
//
//        selectedAdapter.setSelectedData(checkedList)
//
//    }
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

       // Log.e("TaskForEditing" , "task is - ${task.title} , ${task.color} , ${task.dateLong}")

    }


}