package com.raywenderlich.ticky.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
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
import kotlinx.android.synthetic.main.todo_list_view_holder.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class TaskAddingFragment: Fragment() , DatePickerDialog.OnDateSetListener {

    var TASK_COLOR: String = ""
    var TASK_DATE: String = ""
    var TASK_DATE1: Long? = 999999999999999999
    var TASK_COLORED: Int = 10

    private var CIRCLE_POSITION1: Boolean = false
    private var CIRCLE_POSITION2: Boolean = false
    private var CIRCLE_POSITION3: Boolean = false
    private var CIRCLE_POSITION4: Boolean = false
    private var CIRCLE_POSITION5: Boolean = false
    private var CIRCLE_POSITION6: Boolean = false
    private var CIRCLE_POSITION7: Boolean = false
    private var CIRCLE_POSITION8: Boolean = false
    private var CIRCLE_POSITION9: Boolean = false


    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var factory: com.raywenderlich.ticky.repository.Factory
    private lateinit var taskieDao: TaskieDao
    private lateinit var taskDB: TaskieDatabase
    private lateinit var repository: TaskieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.adding_activity_task, container, false)


        initViewModel()



        return view
    }

    private fun initViewModel() {

        taskDB = TaskieDatabase.getDatabase(requireContext())
        taskieDao = taskDB.taskieDao()
        repository = TaskieRepository(taskieDao)
        factory = com.raywenderlich.ticky.repository.Factory(repository)
        mTaskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view.Task_input , InputMethodManager.SHOW_IMPLICIT)


        val btt1 = AnimationUtils.loadAnimation(context, R.anim.btt2)

        val oval1 = oval1 as Button
        val oval2 = oval2 as Button
        val oval3 = oval3 as Button
        val oval4 = oval4 as Button
        val oval5 = oval5 as Button
        val oval6 = oval6 as Button
        val oval7 = oval7 as Button
        val oval8 = oval8 as Button
        val oval9 = oval9 as Button
        val saveButton = saveButton as Button
        val cancelText = textView4 as TextView
        val cancelButton = cancel_selecting2 as ImageView
        val input = Task_input as EditText
        val textView6 = textView6 as TextView

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



        dateCalendar.visibility = INVISIBLE
        xoo.visibility = INVISIBLE

        disableEnable()

        view.calendar.setOnClickListener {
            getDate()

        }
        view.dateCalendar.setOnClickListener {
            getDate()
        }

        setColor()

        view.saveButton.setOnClickListener {

            insertDataToDatabase()
            listener1?.taskAdd()
        }
        view.back_to_tasks.setOnClickListener {
            setToDefs()
            listener?.bttnClicked()

        }

        view.textView4.setOnClickListener {
            setToDefs()
            listener?.bttnClicked()

        }
        view.cancel_selecting2.setOnClickListener {
           setToDefs()
            listener?.bttnClicked()
        }

        view.xoo.setOnClickListener {
            TASK_DATE = ""
            xoo.visibility = INVISIBLE
            calendar.visibility = VISIBLE
            dateCalendar.text = ""

        }


    }

    private fun setToDefs(){
        TASK_COLOR = ""
        TASK_DATE = ""
        TASK_DATE1 = 999999999999999999
        TASK_COLORED = 10
        Task_input.text.clear()
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

    private fun setViewDisable() {
        view?.circleRed?.visibility = INVISIBLE
        view?.circleOrange?.visibility = INVISIBLE
        view?.circleYellow?.visibility = INVISIBLE
        view?.circleGreen?.visibility = INVISIBLE
        view?.lightBlue?.visibility = INVISIBLE
        view?.circleBlue?.visibility = INVISIBLE
        view?.circleUcnobi?.visibility = INVISIBLE
        view?.circle_purple?.visibility = INVISIBLE
        view?.circleRose?.visibility = INVISIBLE
    }


    private fun disableEnable() {

        view?.Task_input?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                saveButton.setBackgroundResource(R.drawable.onboarding_button)
                Task_input.setHintTextColor(resources.getColor(R.color.hint_color))
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (Task_input.text.toString().trim({ it <= ' ' }).isEmpty()) {
                    saveButton.setEnabled(false)
                    saveButton.setBackgroundResource(R.drawable.onboarding_button)

                } else {


                    saveButton.setEnabled(true)
                    saveButton.setBackgroundResource(R.drawable.disabled_save_button)
                }


            }

            override fun afterTextChanged(s: Editable?) {
                //  saveButton.setBackgroundResource(R.drawable.disabled_save_button)

            }

        })


    }
    
    private fun setColor()  {
        view?.oval1?.setOnClickListener {
            TASK_COLOR = "#ff453a"
            TASK_COLORED = 0

            if (CIRCLE_POSITION1){

                setViewDisable()

                setToFalse()
            }
            else{
                setToFalse()
                setViewDisable()
                view?.circleRed?.visibility = VISIBLE
                CIRCLE_POSITION1 = true
            }

        }
        view?.oval2?.setOnClickListener {
            TASK_COLOR = "#ff9f0c"
            TASK_COLORED = 1

            if (CIRCLE_POSITION2){

                setViewDisable()


                setToFalse()
            }
            else{
               setToFalse()
                setViewDisable()
                view?.circleOrange?.visibility = VISIBLE
                CIRCLE_POSITION2 = true
            }
        }
        view?.oval3?.setOnClickListener {
            TASK_COLOR = "#ffd50c"
            TASK_COLORED = 2

            if (CIRCLE_POSITION3){

                setViewDisable()

                setToFalse()

            }

            else{

            setViewDisable()
               setToFalse()
                view?.circleYellow?.visibility = VISIBLE
                CIRCLE_POSITION3 = true
            }
        }
        view?.oval4?.setOnClickListener {
            TASK_COLOR = "#32d74b"
            TASK_COLORED = 3

            if (CIRCLE_POSITION4){

                setViewDisable()

                setToFalse()
            }
            else{
                setViewDisable()
                setToFalse()
                view?.circleGreen?.visibility = VISIBLE
                CIRCLE_POSITION4 = true
            }
        }
        view?.oval5?.setOnClickListener {
            TASK_COLOR = "#64d2ff"
            TASK_COLORED = 4

            if (CIRCLE_POSITION5){

                setViewDisable()

                setToFalse()
            }
            else{

                setToFalse()
                setViewDisable()
                view?.lightBlue?.visibility = VISIBLE
                CIRCLE_POSITION5 = true

                          }
        }
        view?.oval6?.setOnClickListener {
            TASK_COLOR = "#0984ff"
            TASK_COLORED = 5

            if(CIRCLE_POSITION6) {
                setViewDisable()

                setToFalse()
            }
            else{
                setViewDisable()
                setToFalse()
                view?.circleBlue?.visibility = VISIBLE
                CIRCLE_POSITION6=true
            }

        }
        view?.oval7?.setOnClickListener {
            TASK_COLOR = "#5e5ce6"
            TASK_COLORED = 6

            if (CIRCLE_POSITION7){
                setViewDisable()

                setToFalse()
            }
            else{
                setViewDisable()
                setToFalse()
                view?.circleUcnobi?.visibility = VISIBLE
                CIRCLE_POSITION7 = true

            }
        }
        view?.oval8?.setOnClickListener {
            TASK_COLOR = "#bf5af2"
            TASK_COLORED = 7

            if (CIRCLE_POSITION8){

                setViewDisable()

                setToFalse()
            }
            else{
                setViewDisable()
                setToFalse()
                view?.circle_purple?.visibility = VISIBLE
                CIRCLE_POSITION8 = true
            }
        }
        view?.oval9?.setOnClickListener {
            TASK_COLOR = "#ff375f"
            TASK_COLORED = 8

            if (CIRCLE_POSITION9){

                setViewDisable()

                setToFalse()
            }
            else{
                setViewDisable()
                setToFalse()
                view?.circleRose?.visibility = VISIBLE
                CIRCLE_POSITION9 = true
            }
        }

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
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.datePickerOkButtonCollor))
        //0xFF0000FF



        // val datepickerdialog = DatePickerDialog(requireContext() , R.style.DatePickerDialog , this , year , month , day )
     //   datepickerdialog.show()
    }




    private fun insertDataToDatabase()  {
        val title = Task_input.text.toString().trim()
        val color = TASK_COLOR
        val datetime = TASK_DATE
        val checked = false
        val selected = false
        val dateLong = TASK_DATE1
        val sortingColor = TASK_COLORED
        if(title.isNotEmpty()){
            val task = Taskie(0, title, color, datetime, checked, selected, dateLong, sortingColor)
            mTaskViewModel.addTask(task)
            setToDefs()
        }
    }

    private var listener1 : Task_addingButton? = null
    interface Task_addingButton{
        fun taskAdd()
    }

    companion object {
        fun getTaskFragInstance(): TaskAddingFragment{
            return TaskAddingFragment()
        }
    }

    var listener : BttnClicked? = null

    interface BttnClicked {
        fun bttnClicked()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as BttnClicked
        listener1 = context as Task_addingButton

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        listener1 = null
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
        TASK_DATE= "Due $dayOfMonth $myMonth."
        if(dayOfMonth == 0 && myMonth.isEmpty()){
            calendar.visibility = VISIBLE
        }else{
            calendar.visibility = INVISIBLE
            xoo.visibility = VISIBLE
            dateCalendar.visibility = VISIBLE
            dateCalendar.text = TASK_DATE
        }
    }


}






