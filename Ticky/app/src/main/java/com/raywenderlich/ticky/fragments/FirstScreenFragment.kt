package com.raywenderlich.ticky.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.ticky.R
import com.raywenderlich.ticky.taskrecyclerview.TodoListAdapter
import kotlinx.android.synthetic.main.adding_activity_task.*
import kotlinx.android.synthetic.main.first_screen.*
import kotlinx.android.synthetic.main.home_task_screen.*
import kotlinx.android.synthetic.main.recycler_layout.*
import java.util.*

class FirstScreenFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.first_screen , container , false)
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            setDatee()

        add_task_button.setOnClickListener {
            listener?.Clicked()
        }



        val ttb = AnimationUtils.loadAnimation(context , R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(context , R.anim.stb)
        val btt1 = AnimationUtils.loadAnimation(context , R.anim.btt1)


        val headerTitle = vnaxotraiqneba as ConstraintLayout
        val tickyImg = imageView2 as ImageView
        val text = textView3 as TextView
        val button = add_task_button as Button


        headerTitle.startAnimation(ttb)
        tickyImg.startAnimation(stb)
        text.startAnimation(stb)
        button.startAnimation(btt1)

    }

    companion object {
        fun getFirstScreenFragInstance(): FirstScreenFragment {
            return FirstScreenFragment()
        }
    }


    private fun setDate() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val dayOfTheWeek = c.get(Calendar.DAY_OF_WEEK)



        val day = c.get(Calendar.DAY_OF_WEEK)


        val dayOfTheMonth = c.get(Calendar.DAY_OF_MONTH)

            if(month == 0) {
                datetime.text = ("January,$year")
            }
        if(month == 1) {
            datetime.text = ("February,$year")
        }
        if(month == 2) {
            datetime.text = ("March,$year")
        }
        if(month == 3) {
            datetime.text = ("April,$year")
        }
        if(month == 4) {
            datetime.text = ("May,$year")
        }
        if(month == 5) {
            datetime.text = ("June,$year")
        }
        if(month == 6) {
            datetime.text = ("July,$year")
        }
        if(month == 7) {
            datetime.text = ("August,$year")
        }
        if(month == 8) {
            datetime.text = ("September,$year")
        }
        if(month == 9) {
            datetime.text = ("October,$year")
        }
        if(month == 10) {
            datetime.text = ("November,$year")
        }
        if(month == 11){
            datetime.text = ("December,$year")
        }


        when (day) {
            1 -> {

                    monday.text = (dayOfTheMonth - 6).toString()
                    tuesday.text = (dayOfTheMonth - 5).toString()
                    wednesday.text = (dayOfTheMonth - 4).toString()
                    thursday.text = (dayOfTheMonth - 3).toString()
                    friday.text = (dayOfTheMonth - 2).toString()
                    saturday.text = (dayOfTheMonth - 1).toString()
                    sunday.text = (dayOfTheMonth).toString()


            }
            2 -> {

                monday.text = dayOfTheMonth.toString()
                tuesday.text = (dayOfTheMonth + 1).toString()
                wednesday.text = (dayOfTheMonth + 2).toString()
                thursday.text = (dayOfTheMonth + 3).toString()
                friday.text = (dayOfTheMonth + 4).toString()
                saturday.text = (dayOfTheMonth + 5).toString()
                sunday.text = (dayOfTheMonth + 6).toString()

            }
            3 -> {

                monday.text = (dayOfTheMonth - 1).toString()
                tuesday.text = (dayOfTheMonth).toString()
                wednesday.text = (dayOfTheMonth + 1).toString()
                thursday.text = (dayOfTheMonth + 2).toString()
                friday.text = (dayOfTheMonth + 3).toString()
                saturday.text = (dayOfTheMonth + 4).toString()
                sunday.text = (dayOfTheMonth + 5).toString()

            }
            4 -> {

                monday.text = (dayOfTheMonth - 2).toString()
                tuesday.text = (dayOfTheMonth - 1).toString()
                wednesday.text = (dayOfTheMonth).toString()
                thursday.text = (dayOfTheMonth + 1).toString()
                friday.text = (dayOfTheMonth + 2).toString()
                saturday.text = (dayOfTheMonth + 3).toString()
                sunday.text = (dayOfTheMonth + 4).toString()

            }
            5 -> {


                monday.text = (dayOfTheMonth - 3).toString()
                tuesday.text = (dayOfTheMonth - 2).toString()
                wednesday.text = (dayOfTheMonth  -1).toString()
                thursday.text = (dayOfTheMonth).toString()
                friday.text = (dayOfTheMonth + 1).toString()
                saturday.text = (dayOfTheMonth + 2).toString()
                sunday.text = (dayOfTheMonth+ 3 ).toString()

            }
            6 -> {


                monday.text = (dayOfTheMonth - 4).toString()
                tuesday.text = (dayOfTheMonth - 3).toString()
                wednesday.text = (dayOfTheMonth -2).toString()
                thursday.text = (dayOfTheMonth-1).toString()
                friday.text = (dayOfTheMonth ).toString()
                saturday.text = (dayOfTheMonth + 1).toString()
                sunday.text = (dayOfTheMonth+ 2 ).toString()

            }
            else -> {

                monday.text = (dayOfTheMonth - 5).toString()
                tuesday.text = (dayOfTheMonth - 4).toString()
                wednesday.text = (dayOfTheMonth - 3).toString()
                thursday.text = (dayOfTheMonth - 2).toString()
                friday.text = (dayOfTheMonth -1).toString()
                saturday.text = (dayOfTheMonth ).toString()
                sunday.text = (dayOfTheMonth + 1).toString()

            }
        }



    }

    private fun getDaysAgo(daysAgo : Int) {
        val calendar = Calendar.getInstance()
        val dayOfTheYear = calendar.get(Calendar.MONTH)



        Log.e("Foo" , "${dayOfTheYear}")

       // return calendar.time

    }

    private fun setDatee(){
        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)


        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

        monday.text = (dayOfMonth - 2).toString()
        tuesday.text = (dayOfMonth - 1).toString()
        wednesday.text = dayOfMonth.toString()
        thursday.text = (dayOfMonth + 1) . toString()
        friday.text = (dayOfMonth + 2).toString()
        saturday.text = (dayOfMonth + 3).toString()
        sunday.text = (dayOfMonth + 4).toString()
        // 0- jan , 1 - feb , 2 march , 3 april , 4 may , 5 june , 6 jule , 7 , august , 8 sept , 9 octo , 10 nov , 11 dec

        if (dayOfMonth == 1 && month == 0  )  {
            monday.text = "30"
            tuesday.text = "31"
        }
        else if (dayOfMonth == 1 && month == 2  )  {
            monday.text = "27"
            tuesday.text = "28"
        }

        else if (dayOfMonth == 1 && month == 4  )  {
            monday.text = "29"
            tuesday.text = "30"
        }

        else if (dayOfMonth == 1 && month == 6  )  {
            monday.text = "29"
            tuesday.text = "30"
        }

        else if (dayOfMonth == 1 && month == 7  )  {
            monday.text = "30"
            tuesday.text = "31"
        }

        else if (dayOfMonth == 1 && month == 9  )  {
            monday.text = "29"
            tuesday.text = "30"
        }

        else if (dayOfMonth == 1 && month == 11 )  {
            monday.text = "29"
            tuesday.text = "30"
        }
        else if (dayOfMonth == 2 && month == 0  )  {
            monday.text = "31"
            tuesday.text = "1"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31
        else if (dayOfMonth == 2 && month == 2  )  {
            monday.text = "28"
            tuesday.text = "1"
        }

        else if (dayOfMonth == 2 && month == 4  )  {
            monday.text = "30"
            tuesday.text = "1"
        }

        else if (dayOfMonth == 2 && month == 6  )  {
            monday.text = "30"
            tuesday.text = "1"
        }

        else if (dayOfMonth == 2 && month == 7  )  {
            monday.text = "31"
            tuesday.text = "1"
        }

        else if (dayOfMonth == 2 && month == 9  )  {
            monday.text = "30"
            tuesday.text = "1"
        }

        else if (dayOfMonth == 2 && month == 11  )  {
            monday.text = "30"
            tuesday.text = "1"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31

        else if (dayOfMonth == 1 && month == 1){
            tuesday.text = "31"
            monday.text = "30"
        }
        else if (dayOfMonth == 2 && month == 1){
            tuesday.text = "1"
            monday.text = "31"
        }
        else if (dayOfMonth == 1 && month == 3){
            tuesday.text = "31"
            monday.text = "30"
        }
        else if (dayOfMonth == 1 && month == 5){
            tuesday.text = "31"
            monday.text = "30"
        }
        else if (dayOfMonth == 1 && month == 8){
            tuesday.text = "31"
            monday.text = "30"
        }
        else if (dayOfMonth == 1 && month == 10){
            tuesday.text = "31"
            monday.text = "30"
        }
        else if (dayOfMonth == 2 && month == 3){
            tuesday.text = "1"
            monday.text = "31"
        }
        else if (dayOfMonth == 2 && month == 5){
            tuesday.text = "1"
            monday.text = "31"
        }
        else if (dayOfMonth == 2 && month == 8){
            tuesday.text = "1"
            monday.text = "31"
        }
        else if (dayOfMonth == 2 && month == 10){
            tuesday.text = "1"
            monday.text = "31"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31

        else if (dayOfMonth == 28 && month == 0) {
            sunday.text = "1"
        }

        else if (dayOfMonth == 28 && month == 2 ) {
            sunday.text = "1"
        }

        else if (dayOfMonth == 28 && month == 4) {
            sunday.text = "1"
        }

        else if (dayOfMonth == 28 && month == 6) {
            sunday.text = "1"
        }

        else if (dayOfMonth == 28 && month == 7) {
            sunday.text = "1"
        }

        else if (dayOfMonth == 28 && month == 9) {
            sunday.text = "1"
        }

        else if (dayOfMonth == 28 && month == 11) {
            sunday.text = "1"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31

        else if (dayOfMonth == 29 && month == 0) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 2) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 4) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 6) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 7) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 9) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 11) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 30 && month == 0) {

            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }

        else if (dayOfMonth == 30 && month == 2) {

            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }

        else if (dayOfMonth == 30 && month == 4) {

            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }

        else if (dayOfMonth == 30 && month == 6) {

            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }
        else if (dayOfMonth == 30 && month == 7) {
            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }
        else if (dayOfMonth == 30 && month == 9) {
            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }
        else if (dayOfMonth == 30 && month == 11) {
            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }
        else if (dayOfMonth == 31 && month == 0) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if (dayOfMonth == 31 && month == 2) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if (dayOfMonth == 31 && month == 4) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if (dayOfMonth == 31 && month == 6) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if (dayOfMonth == 31 && month == 7) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if (dayOfMonth == 31 && month == 9) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if (dayOfMonth == 31 && month == 11) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        // 0- jan 31 , 1 - feb 28 , 2 march 31 , 3 april 30 , 4 may 31  , 5 june 30 , 6 jule 31 , 7 august 31 , 8 sept 30 , 9 octo 31 , 10 nov 30, 11 dec 31

        else if (dayOfMonth == 27 && month == 3) {
            sunday.text = "1"
        }
        else if (dayOfMonth == 27 && month == 5) {
            sunday.text = "1"
        }
        else if (dayOfMonth == 27 && month == 8) {
            sunday.text = "1"
        }
        else if (dayOfMonth == 27 && month == 10) {
            sunday.text = "1"
        }
        else if (dayOfMonth == 28 && month == 3) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 28 && month == 5) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 28 && month == 8) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 28 && month == 10) {
            sunday.text = "2"
            saturday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 3) {
            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 5) {
            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 8) {
            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }
        else if (dayOfMonth == 29 && month == 10) {
            sunday.text = "3"
            saturday.text = "2"
            friday.text = "1"
        }
        else if (dayOfMonth == 30 && month == 3) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if (dayOfMonth == 30 && month == 5) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if (dayOfMonth == 30 && month == 8) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if (dayOfMonth == 30 && month == 10) {
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        else if(dayOfMonth == 28 && month == 1){
            sunday.text = "4"
            saturday.text = "3"
            friday.text = "2"
            thursday.text = "1"
        }
        if (dayOfWeek == 0) {
            Monday.text = "T"
            monday2.text = "F"
            monday4.text = "S"
            monday5.text = "S"
            monday6.text = "M"
            monday7.text = "T"
            monday8.text = "W"

        } else if (dayOfWeek == 1) {
            Monday.text = "F"
            monday2.text = "S"
            monday4.text = "S"
            monday5.text = "M"
            monday6.text = "T"
            monday7.text = "W"
            monday8.text = "T"

        } else if (dayOfWeek == 2) {
            Monday.text = "S"
            monday2.text = "S"
            monday4.text = "M"
            monday5.text = "T"
            monday6.text = "W"
            monday7.text = "T"
            monday8.text = "F"
        } else if (dayOfWeek == 3) {
            Monday.text = "S"
            monday2.text = "M"
            monday4.text = "T"
            monday5.text = "W"
            monday6.text = "T"
            monday7.text = "F"
            monday8.text = "S"
        } else if (dayOfWeek == 4) {
            Monday.text = "M"
            monday2.text = "T"
            monday4.text = "W"
            monday5.text = "T"
            monday6.text = "F"
            monday7.text = "S"
            monday8.text = "S"

        } else if (dayOfWeek == 5) {
            Monday.text = "T"
            monday2.text = "W"
            monday4.text = "T"
            monday5.text = "F"
            monday6.text = "S"
            monday7.text = "S"
            monday8.text = "M"
        } else if (dayOfWeek == 6) {
            Monday.text = "W"
            monday2.text = "T"
            monday4.text = "F"
            monday5.text = "S"
            monday6.text = "S"
            monday7.text = "M"
            monday8.text = "T"
        }
        if (month == 0) {
            datetime.text = ("January, $year")
        }
        if (month == 1) {
            datetime.text = ("February, $year")
        }
        if (month == 2) {
            datetime.text = ("March, $year")
        }
        if (month == 3) {
            datetime.text = ("April, $year")
        }
        if (month == 4) {
            datetime.text = ("May, $year")
        }
        if (month == 5) {
            datetime.text = ("June, $year")
        }
        if (month == 6) {
            datetime.text = ("July, $year")
        }
        if (month == 7) {
            datetime.text = ("August, $year")
        }
        if (month == 8) {
            datetime.text = ("September, $year")
        }
        if (month == 9) {
            datetime.text = ("October, $year")
        }
        if (month == 10) {
            datetime.text = ("November, $year")
        }
        if (month == 11) {
            datetime.text = ("December, $year")
        }
    }

    var listener : Click? = null
    interface Click {
        fun Clicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Click
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}