package com.raywenderlich.ticky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.first_screen.*
import java.util.*

class DateTime : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.home_task_screen , container , false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate()
    }
    companion object {
        fun getCalendarInstance(): DateTime {
            return DateTime()
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


        if (day == 1) {


            monday.text = (dayOfTheMonth-6) .toString()
            tuesday.text = (dayOfTheMonth - 5).toString()
            wednesday.text = (dayOfTheMonth - 4).toString()
            thursday.text = (dayOfTheMonth - 3).toString()
            friday.text = (dayOfTheMonth - 2).toString()
            saturday.text = (dayOfTheMonth -1).toString()
            sunday.text = (dayOfTheMonth).toString()

        } else if(day == 2) {

            monday.text = dayOfTheMonth.toString()
            tuesday.text = (dayOfTheMonth + 1).toString()
            wednesday.text = (dayOfTheMonth + 2).toString()
            thursday.text = (dayOfTheMonth + 3).toString()
            friday.text = (dayOfTheMonth + 4).toString()
            saturday.text = (dayOfTheMonth + 5).toString()
            sunday.text = (dayOfTheMonth + 6).toString()

        }else if(day == 3) {

            monday.text = (dayOfTheMonth - 1).toString()
            tuesday.text = (dayOfTheMonth).toString()
            wednesday.text = (dayOfTheMonth + 1).toString()
            thursday.text = (dayOfTheMonth + 2).toString()
            friday.text = (dayOfTheMonth + 3).toString()
            saturday.text = (dayOfTheMonth + 4).toString()
            sunday.text = (dayOfTheMonth + 5).toString()

        }else if(day == 4) {

            monday.text = (dayOfTheMonth - 2).toString()
            tuesday.text = (dayOfTheMonth - 1).toString()
            wednesday.text = (dayOfTheMonth).toString()
            thursday.text = (dayOfTheMonth + 1).toString()
            friday.text = (dayOfTheMonth + 2).toString()
            saturday.text = (dayOfTheMonth + 3).toString()
            sunday.text = (dayOfTheMonth + 4).toString()

        }else if (day == 5) {


            monday.text = (dayOfTheMonth - 3).toString()
            tuesday.text = (dayOfTheMonth - 2).toString()
            wednesday.text = (dayOfTheMonth  -1).toString()
            thursday.text = (dayOfTheMonth).toString()
            friday.text = (dayOfTheMonth + 1).toString()
            saturday.text = (dayOfTheMonth + 2).toString()
            sunday.text = (dayOfTheMonth+ 3 ).toString()

        }else if (day == 6) {


            monday.text = (dayOfTheMonth - 4).toString()
            tuesday.text = (dayOfTheMonth - 3).toString()
            wednesday.text = (dayOfTheMonth -2).toString()
            thursday.text = (dayOfTheMonth-1).toString()
            friday.text = (dayOfTheMonth ).toString()
            saturday.text = (dayOfTheMonth + 1).toString()
            sunday.text = (dayOfTheMonth+ 2 ).toString()

        }

        else {

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