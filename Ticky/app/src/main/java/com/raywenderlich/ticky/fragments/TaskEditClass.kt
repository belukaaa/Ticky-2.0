package com.raywenderlich.ticky.fragments

import android.R.string
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.Taskie
import com.raywenderlich.ticky.db.TaskieDatabase
import com.raywenderlich.ticky.db.dao.TaskieDao
import com.raywenderlich.ticky.repository.TaskViewModel
import com.raywenderlich.ticky.repository.TaskieRepository
import kotlinx.android.synthetic.main.adding_activity_task.*
import kotlinx.android.synthetic.main.adding_activity_task.view.*
import kotlinx.android.synthetic.main.edit_task_layout.*
import kotlinx.android.synthetic.main.edit_task_layout.view.*
import kotlinx.android.synthetic.main.todo_list_view_holder.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class TaskEditClass(taskie: Taskie) : Fragment(), DatePickerDialog.OnDateSetListener {

    var TASK_COLOR: String? = taskie.color
    var TASK_DATE: String? = taskie.datetime
    var TASK_DATE1: Long? = taskie.dateLong
    var TASK_COLORED: Int = taskie.sortingColor


    private var CIRCLE_POSITION1: Boolean = false
    private var CIRCLE_POSITION2: Boolean = false
    private var CIRCLE_POSITION3: Boolean = false
    private var CIRCLE_POSITION4: Boolean = false
    private var CIRCLE_POSITION5: Boolean = false
    private var CIRCLE_POSITION6: Boolean = false
    private var CIRCLE_POSITION7: Boolean = false
    private var CIRCLE_POSITION8: Boolean = false
    private var CIRCLE_POSITION9: Boolean = false

    private val task : Taskie = taskie
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var factory: com.raywenderlich.ticky.repository.Factory
    private lateinit var taskieDao: TaskieDao
    private lateinit var taskDB: TaskieDatabase
    private lateinit var repository: TaskieRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_task_layout, container, false)


        initViewModel()


        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disableEnable()


        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view.Task_input1, InputMethodManager.SHOW_IMPLICIT)

        Log.e(
            "TaskForEditing",
            "TaskForEditing -> ${task.title} , ${task.color} , ${task.datetime}"
        )

        val btt1 = AnimationUtils.loadAnimation(context, R.anim.btt2)

        val oval1 = oval11 as Button
        val oval2 = oval21 as Button
        val oval3 = oval31 as Button
        val oval4 = oval41 as Button
        val oval5 = oval51 as Button
        val oval6 = oval61 as Button
        val oval7 = oval71 as Button
        val oval8 = oval81 as Button
        val oval9 = oval91 as Button
        val saveButton = saveButton1 as Button
        val cancelText = textView41 as TextView
        val cancelButton = cancel_selecting21 as ImageView
        val input = Task_input1 as EditText
        val textView6 = textView61 as TextView

        oval1.startAnimation(btt1)
        oval2.startAnimation(btt1)
        oval3.startAnimation(btt1)
        oval4.startAnimation(btt1)
        oval5.startAnimation(btt1)
        oval6.startAnimation(btt1)
        oval7.startAnimation(btt1)
        oval8.startAnimation(btt1)
        oval9.startAnimation(btt1)
        saveButton.startAnimation(btt1)
        cancelText.startAnimation(btt1)
        cancelButton.startAnimation(btt1)
        input.startAnimation(btt1)
        textView6.startAnimation(btt1)

        setToDefs()
        setDefParameters(task)
        setColor()
        setTaskDate(task)


        view.calendar1.setOnClickListener {
            getDate()
        }
        view.dateCalendar1.setOnClickListener {
            getDate()
        }
        view.back_to_tasks1.setOnClickListener {
            setToDefs()
            listener?.cancelEditing()
//            listener?.bttnClicked()
        }
        view.textView41.setOnClickListener {
            setToDefs()
            listener?.cancelEditing()

//            listener?.bttnClicked()
        }
        view.cancel_selecting21.setOnClickListener {
            setToDefs()
            listener?.cancelEditing()

//            listener?.bttnClicked()
        }
        view.xoo1.setOnClickListener {
            TASK_DATE = ""
            xoo1.visibility = View.INVISIBLE
            calendar1.visibility = View.VISIBLE
            dateCalendar1.text = ""
        }

        view.saveButton1.setOnClickListener {
            updateTask()
        }

    }

    private fun updateTask() {
        val title = Task_input1.text.toString().trim()
        val color = TASK_COLOR
        val datetime = TASK_DATE
        val checked = false
        val selected = false
        val dateLong = TASK_DATE1
        val sortingColor = TASK_COLORED

        val EditedTask = Taskie(task.taskId,title,color,datetime,checked,selected,dateLong,sortingColor)

        mTaskViewModel.updateTask(EditedTask)


    }

    private fun setToDefs(){
        TASK_COLOR = task.color
        TASK_DATE = task.datetime
        TASK_DATE1 = task.dateLong
        TASK_COLORED = task.sortingColor
        view?.Task_input1?.setText(task.title)

        val editText = view?.findViewById(R.id.Task_input1) as EditText
        editText.post(Runnable { editText.setSelection(editText.length()) })
    }
    private fun setTaskDate(taskie: Taskie) {



//        view?.dateCalendar1?.visibility = View.VISIBLE
//        view?.dateCalendar1?.text = "daglije misha"
//
        if (taskie.datetime == ""){
            view?.calendar1?.visibility = View.VISIBLE
            view?.xoo1?.visibility = View.INVISIBLE
            view?.dateCalendar1?.visibility = View.INVISIBLE
        }
        else {
            view?.calendar1?.visibility = View.INVISIBLE
            view?.xoo1?.visibility = View.VISIBLE
            view?.dateCalendar1?.visibility = View.VISIBLE
            view?.dateCalendar1?.text = taskie.datetime
        }

//        if (currentItem.datetime == "") {
//            calendar1.visibility = View.VISIBLE
//            xoo1.visibility = View.INVISIBLE
//            dateCalendar1.visibility = View.INVISIBLE
//        } else {
//            calendar1.visibility = View.INVISIBLE
//            xoo1.visibility = View.VISIBLE
//            dateCalendar1.visibility = View.VISIBLE
//            dateCalendar1.text = TASK_DATE
//
//        }

    }

    private fun setColor()  {
        view?.oval11?.setOnClickListener {
            TASK_COLOR = "#ff453a"
            TASK_COLORED = 0

            if (CIRCLE_POSITION1){

                setViewDisable()

                setToFalse()
            }
            else{
                view?.circleRed1?.visibility = View.VISIBLE
                view?.circleOrange1?.visibility = View.INVISIBLE
                view?.circleYellow1?.visibility = View.INVISIBLE
                view?.circleGreen1?.visibility = View.INVISIBLE
                view?.lightBlue1?.visibility = View.INVISIBLE
                view?.circleBlue1?.visibility = View.INVISIBLE
                view?.circleUcnobi1?.visibility = View.INVISIBLE
                view?.circle_purple1?.visibility = View.INVISIBLE
                view?.circleRose1?.visibility = View.INVISIBLE

                setToFalse()
                CIRCLE_POSITION1 = true
            }

        }
        view?.oval21?.setOnClickListener {
            TASK_COLOR = "#ff9f0c"
            TASK_COLORED = 1

            if (CIRCLE_POSITION2){

                setViewDisable()


                setToFalse()
            }
            else{
                view?.circleRed1?.visibility = View.INVISIBLE
                view?.circleOrange1?.visibility = View.VISIBLE
                view?.circleYellow1?.visibility = View.INVISIBLE
                view?.circleGreen1?.visibility = View.INVISIBLE
                view?.lightBlue1?.visibility = View.INVISIBLE
                view?.circleBlue1?.visibility = View.INVISIBLE
                view?.circleUcnobi1?.visibility = View.INVISIBLE
                view?.circle_purple1?.visibility = View.INVISIBLE
                view?.circleRose1?.visibility = View.INVISIBLE


                setToFalse()
                CIRCLE_POSITION2 = true
            }
        }
        view?.oval31?.setOnClickListener {
            TASK_COLOR = "#ffd50c"
            TASK_COLORED = 2

            if (CIRCLE_POSITION3){

                setViewDisable()

                setToFalse()

            }

            else{

                view?.circleRed1?.visibility = View.INVISIBLE
                view?.circleOrange1?.visibility = View.INVISIBLE
                view?.circleYellow1?.visibility = View.VISIBLE
                view?.circleGreen1?.visibility = View.INVISIBLE
                view?.lightBlue1?.visibility = View.INVISIBLE
                view?.circleBlue1?.visibility = View.INVISIBLE
                view?.circleUcnobi1?.visibility = View.INVISIBLE
                view?.circle_purple1?.visibility = View.INVISIBLE
                view?.circleRose1?.visibility = View.INVISIBLE

                setToFalse()
                CIRCLE_POSITION3 = true
            }
        }
        view?.oval41?.setOnClickListener {
            TASK_COLOR = "#32d74b"
            TASK_COLORED = 3

            if (CIRCLE_POSITION4){

                setViewDisable()

                setToFalse()
            }
            else{

                view?.circleRed1?.visibility = View.INVISIBLE
                view?.circleOrange1?.visibility = View.INVISIBLE
                view?.circleYellow1?.visibility = View.INVISIBLE
                view?.circleGreen1?.visibility = View.VISIBLE
                view?.lightBlue1?.visibility = View.INVISIBLE
                view?.circleBlue1?.visibility = View.INVISIBLE
                view?.circleUcnobi1?.visibility = View.INVISIBLE
                view?.circle_purple1?.visibility = View.INVISIBLE
                view?.circleRose1?.visibility = View.INVISIBLE

                setToFalse()
                CIRCLE_POSITION4 = true
            }
        }
        view?.oval51?.setOnClickListener {
            TASK_COLOR = "#64d2ff"
            TASK_COLORED = 4

            if (CIRCLE_POSITION5){

                setViewDisable()

                setToFalse()
            }
            else{

                view?.circleRed1?.visibility = View.INVISIBLE
                view?.circleOrange1?.visibility = View.INVISIBLE
                view?.circleYellow1?.visibility = View.INVISIBLE
                view?.circleGreen1?.visibility = View.INVISIBLE
                view?.lightBlue1?.visibility = View.VISIBLE
                view?.circleBlue1?.visibility = View.INVISIBLE
                view?.circleUcnobi1?.visibility = View.INVISIBLE
                view?.circle_purple1?.visibility = View.INVISIBLE
                view?.circleRose1?.visibility = View.INVISIBLE

                setToFalse()
                CIRCLE_POSITION5 = true

            }
        }
        view?.oval61?.setOnClickListener {
            TASK_COLOR = "#0984ff"
            TASK_COLORED = 5

            if(CIRCLE_POSITION6) {
                setViewDisable()

                setToFalse()
            }
            else{
                view?.circleRed1?.visibility = View.INVISIBLE
                view?.circleOrange1?.visibility = View.INVISIBLE
                view?.circleYellow1?.visibility = View.INVISIBLE
                view?.circleGreen1?.visibility = View.INVISIBLE
                view?.lightBlue1?.visibility = View.INVISIBLE
                view?.circleBlue1?.visibility = View.VISIBLE
                view?.circleUcnobi1?.visibility = View.INVISIBLE
                view?.circle_purple1?.visibility = View.INVISIBLE
                view?.circleRose1?.visibility = View.INVISIBLE

                setToFalse()
                CIRCLE_POSITION6=true
            }

        }
        view?.oval71?.setOnClickListener {
            TASK_COLOR = "#5e5ce6"
            TASK_COLORED = 6

            if (CIRCLE_POSITION7){
                setViewDisable()

                setToFalse()
            }
            else{

                view?.circleRed1?.visibility = View.INVISIBLE
                view?.circleOrange1?.visibility = View.INVISIBLE
                view?.circleYellow1?.visibility = View.INVISIBLE
                view?.circleGreen1?.visibility = View.INVISIBLE
                view?.lightBlue1?.visibility = View.INVISIBLE
                view?.circleBlue1?.visibility = View.INVISIBLE
                view?.circleUcnobi1?.visibility = View.VISIBLE
                view?.circle_purple1?.visibility = View.INVISIBLE
                view?.circleRose1?.visibility = View.INVISIBLE

                setToFalse()
                CIRCLE_POSITION7 = true

            }
        }
        view?.oval81?.setOnClickListener {
            TASK_COLOR = "#bf5af2"
            TASK_COLORED = 7

            if (CIRCLE_POSITION8){

                setViewDisable()

                setToFalse()
            }
            else{

                view?.circleRed1?.visibility = View.INVISIBLE
                view?.circleOrange1?.visibility = View.INVISIBLE
                view?.circleYellow1?.visibility = View.INVISIBLE
                view?.circleGreen1?.visibility = View.INVISIBLE
                view?.lightBlue1?.visibility = View.INVISIBLE
                view?.circleBlue1?.visibility = View.INVISIBLE
                view?.circleUcnobi1?.visibility = View.INVISIBLE
                view?.circle_purple1?.visibility = View.VISIBLE
                view?.circleRose1?.visibility = View.INVISIBLE

                setToFalse()
                CIRCLE_POSITION8 = true
            }
        }
        view?.oval91?.setOnClickListener {
            TASK_COLOR = "#ff375f"
            TASK_COLORED = 8

            if (CIRCLE_POSITION9){

                setViewDisable()

                setToFalse()
            }
            else{

                view?.circleRed1?.visibility = View.INVISIBLE
                view?.circleOrange1?.visibility = View.INVISIBLE
                view?.circleYellow1?.visibility = View.INVISIBLE
                view?.circleGreen1?.visibility = View.INVISIBLE
                view?.lightBlue1?.visibility = View.INVISIBLE
                view?.circleBlue1?.visibility = View.INVISIBLE
                view?.circleUcnobi1?.visibility = View.INVISIBLE
                view?.circle_purple1?.visibility = View.INVISIBLE
                view?.circleRose1?.visibility = View.VISIBLE


                setToFalse()
                CIRCLE_POSITION9 = true
            }
        }

    }
    private fun setViewDisable() {
        view?.circleRed1?.visibility = View.INVISIBLE
        view?.circleOrange1?.visibility = View.INVISIBLE
        view?.circleYellow1?.visibility = View.INVISIBLE
        view?.circleGreen1?.visibility = View.INVISIBLE
        view?.lightBlue1?.visibility = View.INVISIBLE
        view?.circleBlue1?.visibility = View.INVISIBLE
        view?.circleUcnobi1?.visibility = View.INVISIBLE
        view?.circle_purple1?.visibility = View.INVISIBLE
        view?.circleRose1?.visibility = View.INVISIBLE
    }
    private fun setToFalse() {

        CIRCLE_POSITION1 = false
        CIRCLE_POSITION2 = false
        CIRCLE_POSITION3 = false
        CIRCLE_POSITION4 = false
        CIRCLE_POSITION5 = false
        CIRCLE_POSITION6 = false
        CIRCLE_POSITION7 = false
        CIRCLE_POSITION8 = false
        CIRCLE_POSITION9 = false
    }



    fun setDefParameters(currentItem: Taskie) {
        view?.Task_input1?.setText(currentItem.title)
        val btt1 = AnimationUtils.loadAnimation(context, R.anim.btt2)
        if (currentItem.color == "#ff453a") {
            view?.circleRed1?.visibility = View.VISIBLE
            circleRed1.startAnimation(btt1)
        } else if (currentItem.color == "#ff9f0c") {
            view?.circleOrange1?.visibility = View.VISIBLE
            circleOrange1.startAnimation(btt1)
        } else if (currentItem.color == "#ffd50c") {
            view?.circleYellow1?.visibility = View.VISIBLE
            circleYellow1.startAnimation(btt1)
        } else if (currentItem.color == "#32d74b") {
            view?.circleGreen1?.visibility = View.VISIBLE
            circleGreen1.startAnimation(btt1)
        } else if (currentItem.color == "#64d2ff") {
            view?.lightBlue1?.visibility = View.VISIBLE
            lightBlue1.startAnimation(btt1)
        } else if (currentItem.color == "#0984ff") {
            view?.circleBlue1?.visibility = View.VISIBLE
            circleBlue1.startAnimation(btt1)
        } else if (currentItem.color == "#5e5ce6") {
            view?.circleUcnobi1?.visibility = View.VISIBLE
            circleUcnobi1.startAnimation(btt1)
        } else if (currentItem.color == "#bf5af2") {
            view?.circle_purple1?.visibility = View.VISIBLE
            circle_purple1.startAnimation(btt1)
        } else if (currentItem.color == "#ff375f") {
            view?.circleRose1?.visibility = View.VISIBLE
            circleRose1.startAnimation(btt1)
        } else {
            view?.circleRed1?.visibility = View.INVISIBLE
            view?.circleOrange1?.visibility = View.INVISIBLE
            view?.circleGreen1?.visibility = View.INVISIBLE
            view?.lightBlue1?.visibility = View.INVISIBLE
            view?.circleBlue1?.visibility = View.INVISIBLE
            view?.circleUcnobi1?.visibility = View.INVISIBLE
            view?.circle_purple1?.visibility = View.INVISIBLE
            view?.circleRose1?.visibility = View.INVISIBLE

            view?.circleYellow1?.visibility = View.INVISIBLE
        }





//        if (currentItem.datetime == "") {
//            calendar1.visibility = View.VISIBLE
//            xoo1.visibility = View.INVISIBLE
//            dateCalendar1.visibility = View.INVISIBLE
//        } else {
//            calendar1.visibility = View.INVISIBLE
//            xoo1.visibility = View.VISIBLE
//            dateCalendar1.visibility = View.VISIBLE
//            dateCalendar1.text = TASK_DATE
//
//        }


    }

    private fun getDate() {
        val calendar : Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

// AlertDialog.THEME_DEVICE_DEFAULT_DARK

        val datePickerDialog =
            DatePickerDialog(requireContext(), R.style.DatePickerDialog, this, year, month, day)
//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
        datePickerDialog.show()
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        //0xFF0000FF



        // val datepickerdialog = DatePickerDialog(requireContext() , R.style.DatePickerDialog , this , year , month , day )
        //   datepickerdialog.show()
    }

    private fun initViewModel() {

        taskDB = TaskieDatabase.getDatabase(requireContext())
        taskieDao = taskDB.taskieDao()
        repository = TaskieRepository(taskieDao)
        factory = com.raywenderlich.ticky.repository.Factory(repository)
        mTaskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)

    }
    private fun disableEnable() {

        view?.Task_input1?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                saveButton1.setBackgroundResource(R.drawable.onboarding_button)

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (Task_input1.text.toString().trim({ it <= ' ' }).isEmpty()) {
                    saveButton1.setEnabled(false)
                    saveButton1.setBackgroundResource(R.drawable.onboarding_button)

                } else {

                    saveButton1.setEnabled(true)
                    saveButton1.setBackgroundResource(R.drawable.disabled_save_button)
                }


            }

            override fun afterTextChanged(s: Editable?) {
                //  saveButton.setBackgroundResource(R.drawable.disabled_save_button)

            }

        })


    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val Calendar = Calendar.getInstance()
        Calendar[year, month] = dayOfMonth

//        val formatedDate = sdf.format(calendar.time)
        Log.e("TAG", "Format date is ${Calendar.time}")
        val givenDateString = Calendar.time.toString()
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
        try {
            val mDate = sdf.parse(givenDateString)
            val timeInMilliseconds = mDate.time
            TASK_DATE1 = timeInMilliseconds
            Log.e("TAG", "Format date is ${timeInMilliseconds}")
        } catch (e: ParseException) {
            e.printStackTrace()
        }




        var myMonth = ""

        if(month == 0) {
            myMonth= ("Jan")
        }
        if(month == 1) {
            myMonth = ("Feb")
        }
        if(month == 2) {
            myMonth= ("Mar")
        }
        if(month == 3) {
            myMonth = ("Apr")
        }
        if(month == 4) {
            myMonth = ("May")
        }
        if(month == 5) {
            myMonth = ("Jun")
        }
        if(month == 6) {
            myMonth= ("Jul")
        }
        if(month == 7) {
            myMonth= ("Aug")
        }
        if(month == 8) {
            myMonth= ("Sep")
        }
        if(month == 9) {
            myMonth = ("Oct")
        }
        if(month == 10) {
            myMonth = ("Nov")
        }
        if(month == 11){
            myMonth = ("Dec")
        }
        TASK_DATE= "Due $dayOfMonth $myMonth"
        if(dayOfMonth == 0 && myMonth.isEmpty()){
            calendar1.visibility = View.VISIBLE
        }else{
            calendar1.visibility = View.INVISIBLE
            xoo1.visibility = View.VISIBLE
            dateCalendar1.visibility = View.VISIBLE
            dateCalendar1.text = TASK_DATE

        }
    }

    var listener : ICancelEditing? = null

    interface ICancelEditing {
        fun cancelEditing()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ICancelEditing
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}