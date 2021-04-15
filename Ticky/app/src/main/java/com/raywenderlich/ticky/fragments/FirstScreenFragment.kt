package com.raywenderlich.ticky.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.raywenderlich.ticky.R
import kotlinx.android.synthetic.main.adding_activity_task.*
import kotlinx.android.synthetic.main.first_screen.*
import kotlinx.android.synthetic.main.onboarding.*
import kotlinx.android.synthetic.main.recycler_layout.*
import java.util.*

class FirstScreenFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.first_screen, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDate()

        add_task_button.setOnClickListener {
            listener?.Clicked()
        }


        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(context, R.anim.stb)
        val btt1 = AnimationUtils.loadAnimation(context, R.anim.btt1)


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


    var listener: Click? = null

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
    private fun setDefaultColors(){
        Monday.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday2.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday4.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday5.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday6.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday7.setTextColor(resources.getColor(R.color.default_calendar_days_color))
        monday8.setTextColor(resources.getColor(R.color.default_calendar_days_color))
    }
    private fun setDefaultPosition(){
        imageView1.visibility = View.INVISIBLE
        imageView21.visibility = View.INVISIBLE
        imageView31.visibility = View.INVISIBLE
        imageView4.visibility = View.INVISIBLE
        imageView5.visibility = View.INVISIBLE
        imageView6.visibility = View.INVISIBLE
        imageView7.visibility = View.INVISIBLE
    }

    private fun enableBlue(int: Int){
        setDefaultPosition()
        setDefaultColors()
        if (int == 2){
            imageView1.visibility = View.VISIBLE
            Monday.setTextColor(resources.getColor(R.color.selectedItemColor))
            Monday.alpha = 1f
        }
        else if (int == 3) {
            imageView21.visibility = View.VISIBLE
            monday2.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday2.alpha = 1f
        }
        else if (int == 4) {
            imageView31.visibility = View.VISIBLE
            monday4.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday4.alpha = 1f
        }
        else if (int == 5) {
            imageView4.visibility = View.VISIBLE
            monday5.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday5.alpha = 1f
        }
        else if (int == 6){
            imageView5.visibility = View.VISIBLE
            monday6.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday6.alpha = 1f
        }
        else if (int == 0) {
            imageView6.visibility = View.VISIBLE
            monday7.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday7.alpha = 1f
        }
        else if (int == 1){
            imageView7.visibility = View.VISIBLE
            monday8.setTextColor(resources.getColor(R.color.selectedItemColor))
            monday8.alpha = 1f
        }

    }


    private fun setDate() {

        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

// dayofweek = 2 monday
        if (dayOfWeek == 2) {
            enableBlue(dayOfWeek)
            monday?.text = dayOfMonth.toString()
            tuesday?.text = (dayOfMonth + 1).toString()
            wednesday?.text = (dayOfMonth + 2).toString()
            thursday?.text = (dayOfMonth + 3).toString()
            friday?.text = (dayOfMonth + 4).toString()
            saturday?.text = (dayOfMonth + 5).toString()
            sunday?.text = (dayOfMonth + 6).toString()

        }
        if (dayOfWeek == 3) {
            enableBlue(dayOfWeek)

            monday?.text = (dayOfMonth - 1).toString()
            tuesday?.text = (dayOfMonth).toString()
            wednesday?.text = (dayOfMonth + 1).toString()
            thursday?.text = (dayOfMonth + 2).toString()
            friday?.text = (dayOfMonth + 3).toString()
            saturday?.text = (dayOfMonth + 4).toString()
            sunday?.text = (dayOfMonth + 5).toString()
        }
        if (dayOfWeek == 4) {
            enableBlue(dayOfWeek)

           monday?.text = (dayOfMonth - 2).toString()
            tuesday?.text = (dayOfMonth - 1).toString()
            wednesday?.text = (dayOfMonth).toString()
            thursday?.text = (dayOfMonth + 1).toString()
            friday?.text = (dayOfMonth + 2).toString()
            saturday?.text = (dayOfMonth + 3).toString()
            sunday?.text = (dayOfMonth + 4).toString()
        }
        if (dayOfWeek == 5) {
            enableBlue(dayOfWeek)

            monday?.text = (dayOfMonth - 3).toString()
            tuesday?.text = (dayOfMonth - 2).toString()
            wednesday?.text = (dayOfMonth - 1).toString()
            thursday?.text = (dayOfMonth).toString()
            friday?.text = (dayOfMonth + 1).toString()
            saturday?.text = (dayOfMonth + 2).toString()
            sunday?.text = (dayOfMonth + 3).toString()
        }
        if (dayOfWeek == 6) {
            enableBlue(dayOfWeek)

            monday?.text = (dayOfMonth - 4).toString()
            tuesday?.text = (dayOfMonth - 3).toString()
            wednesday?.text = (dayOfMonth - 2).toString()
            thursday?.text = (dayOfMonth - 1).toString()
            friday?.text = (dayOfMonth).toString()
            saturday?.text = (dayOfMonth + 1).toString()
            sunday?.text = (dayOfMonth + 2).toString()
        }
        if (dayOfWeek == 0) {
            enableBlue(dayOfWeek)

            monday?.text = (dayOfMonth - 5).toString()
            tuesday?.text = (dayOfMonth - 4).toString()
            wednesday?.text = (dayOfMonth - 3).toString()
            thursday?.text = (dayOfMonth - 2).toString()
            friday?.text = (dayOfMonth - 1).toString()
            saturday?.text = (dayOfMonth).toString()
            sunday?.text = (dayOfMonth + 1).toString()
        }
        if (dayOfWeek == 1) {
            enableBlue(dayOfWeek)

            monday?.text = (dayOfMonth - 6).toString()
            tuesday?.text = (dayOfMonth - 5).toString()
            wednesday?.text = (dayOfMonth - 4).toString()
            thursday?.text = (dayOfMonth - 3).toString()
            friday?.text = (dayOfMonth - 2).toString()
            saturday?.text = (dayOfMonth - 1).toString()
            sunday?.text = (dayOfMonth).toString()
        }

        if (dayOfWeek == 2) {
            if (dayOfMonth == 26) {
                if (month % 2 == 1) { //month%2 == 1 ნიშნავს რო 31 ით მთავრდება თვე
                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (dayOfMonth + 1).toString()
                    wednesday?.text = (dayOfMonth + 2).toString()
                    thursday?.text = (dayOfMonth + 3).toString()
                    friday?.text = (dayOfMonth + 4).toString()
                    saturday?.text = (dayOfMonth + 5).toString()
                    sunday?.text = (1).toString()
                } else if (month % 2 == 0) { // month%2 == 0 ნიშნავს რო 30 ით მთავრდება თვე

                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (dayOfMonth + 1).toString()
                    wednesday?.text = (dayOfMonth + 2).toString()
                    thursday?.text = (dayOfMonth + 3).toString()
                    friday?.text = (dayOfMonth + 4).toString()
                    saturday?.text = (1).toString()
                    sunday?.text = (2).toString()
                }
            } else if (dayOfMonth == 27) {
                if (month % 2 == 1) {

                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (dayOfMonth + 1).toString()
                    wednesday?.text = (dayOfMonth + 2).toString()
                    thursday?.text = (dayOfMonth + 3).toString()
                    friday?.text = (dayOfMonth + 4).toString()
                    saturday?.text = (1).toString()
                    sunday?.text = (2).toString()
                } else if (month % 2 == 0) {

                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (dayOfMonth + 1).toString()
                    wednesday?.text = (dayOfMonth + 2).toString()
                    thursday?.text = (dayOfMonth + 3).toString()
                    friday?.text = (1).toString()
                    saturday?.text = (2).toString()
                    sunday?.text = (3).toString()
                }
            } else if (dayOfMonth == 28) {
                if (month % 2 == 0) {

                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (dayOfMonth + 1).toString()
                    wednesday?.text = (dayOfMonth + 2).toString()
                    thursday?.text = (1).toString()
                    friday?.text = (2).toString()
                    saturday?.text = (3).toString()
                    sunday?.text = (4).toString()
                } else if (month % 2 == 1) {

                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (dayOfMonth + 1).toString()
                    wednesday?.text = (dayOfMonth + 2).toString()
                    thursday.text = (dayOfMonth + 3).toString()
                    friday?.text = (1).toString()
                    saturday?.text = (2).toString()
                    sunday?.text = (3).toString()
                }
            } else if (dayOfMonth == 29) {
                if (month % 2 == 0) {
                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (dayOfMonth + 1).toString()
                    wednesday?.text = (1).toString()
                    thursday?.text = (2).toString()
                    friday?.text = (3).toString()
                    saturday?.text = (4).toString()
                    sunday?.text = (5).toString()
                }
                if (month % 2 == 1) {
                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (dayOfMonth + 1).toString()
                    wednesday?.text = (31).toString()
                    thursday?.text = (1).toString()
                    friday?.text = (2).toString()
                    saturday?.text = (3).toString()
                    sunday?.text = (4).toString()
                }
            } else if (dayOfMonth == 30) {
                if (month % 2 == 0) {
                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (1).toString()
                    wednesday?.text = (2).toString()
                    thursday?.text = (3).toString()
                    friday?.text = (4).toString()
                    saturday?.text = (5).toString()
                    sunday?.text = (6).toString()
                }
                if (month % 2 == 1) {
                    monday?.text = dayOfMonth.toString()
                    tuesday?.text = (dayOfMonth + 1).toString()
                    wednesday?.text = (1).toString()
                    thursday?.text = (2).toString()
                    friday?.text = (3).toString()
                    saturday?.text = (4).toString()
                    sunday?.text = (5).toString()
                }
            } else if (dayOfMonth == 31) {
                monday?.text = dayOfMonth.toString()
                tuesday?.text = (1).toString()
                wednesday?.text = (2).toString()
                thursday?.text = (3).toString()
                friday?.text = (4).toString()
                saturday?.text = (5).toString()
                sunday?.text = (6).toString()
            }
        }

        if (dayOfWeek == 3) {
            if (dayOfMonth == 26) {
                if (month % 2 == 1) {

                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (dayOfMonth + 2).toString()
                    friday?.text = (dayOfMonth + 3).toString()
                    saturday?.text = (30).toString()
                    sunday?.text = (31).toString()
                } else if (month % 2 == 0) {

                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (dayOfMonth + 2).toString()
                    friday?.text = (29).toString()
                    saturday?.text = (30).toString()
                    sunday?.text = (1).toString()
                }
            } else if (dayOfMonth == 27) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (dayOfMonth + 2).toString()
                    friday?.text = (dayOfMonth + 3).toString()
                    saturday?.text = (31).toString()
                    sunday?.text = (1).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (dayOfMonth + 2).toString()
                    friday?.text = (30).toString()
                    saturday?.text = (1).toString()
                    sunday?.text = (2).toString()
                }
            } else if (dayOfMonth == 28) {
                if (month % 2 == 1) {

                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (dayOfMonth + 2).toString()
                    friday?.text = (dayOfMonth + 3).toString()
                    saturday?.text = (1).toString()
                    sunday?.text = (2).toString()
                } else if (month % 2 == 0) {

                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (dayOfMonth + 2).toString()
                    friday?.text = (1).toString()
                    saturday?.text = (2).toString()
                    sunday?.text = (3).toString()
                }
            } else if (dayOfMonth == 29) {
                if (month % 2 == 1) {

                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (dayOfMonth + 2).toString()
                    friday?.text = (1).toString()
                    saturday?.text = (2).toString()
                    sunday?.text = (3).toString()
                } else if (month % 2 == 0) {

                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (1).toString()
                    friday?.text = (2).toString()
                    saturday?.text = (3).toString()
                    sunday?.text = (4).toString()
                }
            } else if (dayOfMonth == 30) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (1).toString()
                    friday?.text = (2).toString()
                    saturday?.text = (3).toString()
                    sunday?.text = (3 + 1).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 1).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (1).toString()
                    thursday?.text = (2).toString()
                    friday?.text = (3).toString()
                    saturday?.text = (4).toString()
                    sunday?.text = (5).toString()
                }
            } else if (dayOfMonth == 31) {
                monday?.text = (dayOfMonth - 1).toString()
                tuesday?.text = (dayOfMonth).toString()
                wednesday?.text = (1).toString()
                thursday?.text = (2).toString()
                friday?.text = (3).toString()
                saturday?.text = (4).toString()
                sunday?.text = (5).toString()
            }
        }
        if (dayOfWeek == 4) {
            if (dayOfMonth == 27) {

                monday?.text = (dayOfMonth - 2).toString()
                tuesday?.text = (dayOfMonth - 1).toString()
                wednesday?.text = (dayOfMonth).toString()
                thursday?.text = (dayOfMonth + 1).toString()
                friday?.text = (dayOfMonth + 2).toString()
                saturday?.text = (dayOfMonth + 3).toString()
                sunday?.text = (1).toString()

            } else if (dayOfMonth == 28) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 2).toString()
                    tuesday?.text = (dayOfMonth - 1).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (dayOfMonth + 1).toString()
                    friday?.text = (dayOfMonth + 2).toString()
                    saturday?.text = (dayOfMonth + 3).toString()
                    sunday?.text = (1).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 2).toString()
                    tuesday?.text = (dayOfMonth - 1).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (dayOfMonth + 1).toString()
                    friday?.text = (dayOfMonth + 2).toString()
                    saturday?.text = (1).toString()
                    sunday?.text = (2).toString()
                }
            } else if (dayOfMonth == 29) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 2).toString()
                    tuesday?.text = (dayOfMonth - 1).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (dayOfMonth + 1).toString()
                    friday?.text = (dayOfMonth + 2).toString()
                    saturday?.text = (1).toString()
                    sunday?.text = (2).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 2).toString()
                    tuesday?.text = (dayOfMonth - 1).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (dayOfMonth + 1).toString()
                    friday?.text = (1).toString()
                    saturday?.text = (2).toString()
                    sunday?.text = (3).toString()
                }
            } else if (dayOfMonth == 30) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 2).toString()
                    tuesday?.text = (dayOfMonth - 1).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (dayOfMonth + 1).toString()
                    friday?.text = (1).toString()
                    saturday?.text = (2).toString()
                    sunday?.text = (3).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 2).toString()
                    tuesday?.text = (dayOfMonth - 1).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (1).toString()
                    friday?.text = (2).toString()
                    saturday?.text = (3).toString()
                    sunday?.text = (4).toString()
                }
            } else if (dayOfMonth == 31) {

                monday?.text = (dayOfMonth - 2).toString()
                tuesday?.text = (dayOfMonth - 1).toString()
                wednesday?.text = (dayOfMonth).toString()
                thursday?.text = (1).toString()
                friday?.text = (2).toString()
                saturday?.text = (3).toString()
                sunday?.text = (4).toString()

            }
        }
        if (dayOfWeek == 5) {
            if (dayOfMonth == 28) {
                if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 3).toString()
                    tuesday?.text = (dayOfMonth - 2).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (dayOfMonth + 2).toString()
                    sunday?.text = (1).toString()
                }
            } else if (dayOfMonth == 29) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 3).toString()
                    tuesday?.text = (dayOfMonth - 2).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (dayOfMonth + 2).toString()
                    sunday?.text = (1).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 3).toString()
                    tuesday?.text = (dayOfMonth - 2).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (1).toString()
                    sunday?.text = (2).toString()
                }
            } else if (dayOfMonth == 30) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 3).toString()
                    tuesday?.text = (dayOfMonth - 2).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (1).toString()
                    sunday?.text = (2).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 3).toString()
                    tuesday?.text = (dayOfMonth - 2).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (1).toString()
                    saturday?.text = (2).toString()
                    sunday?.text = (3).toString()
                }
            } else if (dayOfMonth == 31) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 3).toString()
                    tuesday?.text = (dayOfMonth - 2).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (1).toString()
                    saturday?.text = (2).toString()
                    sunday?.text = (3).toString()
                }
            }
        }
        if (dayOfWeek == 6) {
            if (dayOfMonth == 29) {
                if (month % 2 == 31) {
                    monday?.text = (dayOfMonth - 4).toString()
                    tuesday?.text = (dayOfMonth - 3).toString()
                    wednesday?.text = (dayOfMonth - 2).toString()
                    thursday?.text = (dayOfMonth - 1).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (dayOfMonth + 2).toString()
                }
            }
            if (dayOfMonth == 30) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 4).toString()
                    tuesday?.text = (dayOfMonth - 3).toString()
                    wednesday?.text = (dayOfMonth - 2).toString()
                    thursday?.text = (dayOfMonth - 1).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (1).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 4).toString()
                    tuesday?.text = (dayOfMonth - 3).toString()
                    wednesday?.text = (dayOfMonth - 2).toString()
                    thursday?.text = (dayOfMonth - 1).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (1).toString()
                    sunday?.text = (2).toString()
                }
            } else if (dayOfMonth == 31) {
                monday?.text = (dayOfMonth - 4).toString()
                tuesday?.text = (dayOfMonth - 3).toString()
                wednesday?.text = (dayOfMonth - 2).toString()
                thursday?.text = (dayOfMonth - 1).toString()
                friday?.text = (dayOfMonth).toString()
                saturday?.text = (1).toString()
                sunday?.text = (2).toString()
            }
        }
        if (dayOfWeek == 0) {
            if (dayOfMonth == 30) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 5).toString()
                    tuesday?.text = (dayOfMonth - 4).toString()
                    wednesday?.text = (dayOfMonth - 3).toString()
                    thursday?.text = (dayOfMonth - 2).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (31).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (dayOfMonth - 5).toString()
                    tuesday?.text = (dayOfMonth - 4).toString()
                    wednesday?.text = (dayOfMonth - 3).toString()
                    thursday?.text = (dayOfMonth - 2).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (1).toString()
                }
            }
            if (dayOfMonth == 31) {
                if (month % 2 == 1) {
                    monday?.text = (dayOfMonth - 5).toString()
                    tuesday?.text = (dayOfMonth - 4).toString()
                    wednesday?.text = (dayOfMonth - 3).toString()
                    thursday?.text = (dayOfMonth - 2).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (1).toString()
                }

            }

        }
        if (dayOfWeek == 1) {
            if (dayOfMonth == 1) {
                if (month % 2 == 0) {
                    monday?.text = (25).toString()
                    tuesday?.text = (26).toString()
                    wednesday?.text = (27).toString()
                    thursday?.text = (28).toString()
                    friday?.text = (29).toString()
                    saturday?.text = (30).toString()
                    sunday?.text = (dayOfMonth).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (26).toString()
                    tuesday?.text = (27).toString()
                    wednesday?.text = (28).toString()
                    thursday?.text = (29).toString()
                    friday?.text = (30).toString()
                    saturday?.text = (31).toString()
                    sunday?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 2) {
                if (month % 2 == 0) {
                    monday?.text = (26).toString()
                    tuesday?.text = (27).toString()
                    wednesday?.text = (28).toString()
                    thursday?.text = (29).toString()
                    friday?.text = (30).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (27).toString()
                    tuesday?.text = (28).toString()
                    wednesday?.text = (29).toString()
                    thursday?.text = (30).toString()
                    friday?.text = (31).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 3) {
                if (month % 2 == 0) {
                    monday?.text = (27).toString()
                    tuesday?.text = (28).toString()
                    wednesday?.text = (29).toString()
                    thursday?.text = (30).toString()
                    friday?.text = (dayOfMonth - 2).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (28).toString()
                    tuesday?.text = (29).toString()
                    wednesday?.text = (30).toString()
                    thursday?.text = (31).toString()
                    friday?.text = (dayOfMonth - 2).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 4) {
                if (month % 2 == 0) {
                    monday?.text = (28).toString()
                    tuesday?.text = (29).toString()
                    wednesday?.text = (30).toString()
                    thursday?.text = (dayOfMonth - 3).toString()
                    friday?.text = (dayOfMonth - 2).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (29).toString()
                    tuesday?.text = (30).toString()
                    wednesday?.text = (31).toString()
                    thursday?.text = (dayOfMonth - 3).toString()
                    friday?.text = (dayOfMonth - 2).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 5) {
                if (month % 2 == 0) {
                    monday?.text = (29).toString()
                    tuesday?.text = (30).toString()
                    wednesday?.text = (dayOfMonth - 4).toString()
                    thursday?.text = (dayOfMonth - 3).toString()
                    friday?.text = (dayOfMonth - 2).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (30).toString()
                    tuesday?.text = (31).toString()
                    wednesday?.text = (dayOfMonth - 4).toString()
                    thursday?.text = (dayOfMonth - 3).toString()
                    friday?.text = (dayOfMonth - 2).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                }
            }
            if (dayOfMonth == 6) {
                if (month % 2 == 0) {
                    monday?.text = (30).toString()
                    tuesday?.text = (dayOfMonth - 5).toString()
                    wednesday?.text = (dayOfMonth - 4).toString()
                    thursday?.text = (dayOfMonth - 3).toString()
                    friday?.text = (dayOfMonth - 2).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (31).toString()
                    tuesday?.text = (dayOfMonth - 5).toString()
                    wednesday?.text = (dayOfMonth - 4).toString()
                    thursday?.text = (dayOfMonth - 3).toString()
                    friday?.text = (dayOfMonth - 2).toString()
                    saturday?.text = (dayOfMonth - 1).toString()
                    sunday?.text = (dayOfMonth).toString()
                }
            }

        }
        if (dayOfWeek == 0) {
            if (dayOfMonth == 1) {
                if (month % 2 == 0) {
                    monday?.text = (26).toString()
                    tuesday?.text = (27).toString()
                    wednesday?.text = (28).toString()
                    thursday?.text = (29).toString()
                    friday?.text = (30).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (27).toString()
                    tuesday?.text = (28).toString()
                    wednesday?.text = (29).toString()
                    thursday?.text = (30).toString()
                    friday?.text = (31).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                }
            }
            if (dayOfMonth == 2) {
                if (month % 2 == 0) {
                    monday?.text = (27).toString()
                    tuesday?.text = (28).toString()
                    wednesday?.text = (29).toString()
                    thursday?.text = (30).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (28).toString()
                    tuesday?.text = (29).toString()
                    wednesday?.text = (30).toString()
                    thursday?.text = (31).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                }
            }
            if (dayOfMonth == 3) {
                if (month % 2 == 0) {
                    monday?.text = (28).toString()
                    tuesday?.text = (29).toString()
                    wednesday?.text = (30).toString()
                    thursday?.text = (dayOfMonth - 2).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (29).toString()
                    tuesday?.text = (30).toString()
                    wednesday?.text = (31).toString()
                    thursday?.text = (dayOfMonth - 2).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                }
            }
            if (dayOfMonth == 4) {
                if (month % 2 == 0) {
                    monday?.text = (29).toString()
                    tuesday?.text = (30).toString()
                    wednesday?.text = (dayOfMonth - 3).toString()
                    thursday?.text = (dayOfMonth - 2).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (30).toString()
                    tuesday?.text = (31).toString()
                    wednesday?.text = (dayOfMonth - 3).toString()
                    thursday?.text = (dayOfMonth - 2).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                }
            }
            if (dayOfMonth == 5) {
                if (month % 2 == 0) {
                    monday?.text = (30).toString()
                    tuesday?.text = (dayOfMonth - 4).toString()
                    wednesday?.text = (dayOfMonth - 3).toString()
                    thursday?.text = (dayOfMonth - 2).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (31).toString()
                    tuesday?.text = (dayOfMonth - 4).toString()
                    wednesday?.text = (dayOfMonth - 3).toString()
                    thursday?.text = (dayOfMonth - 2).toString()
                    friday?.text = (dayOfMonth - 1).toString()
                    saturday?.text = (dayOfMonth).toString()
                    sunday?.text = (dayOfMonth + 1).toString()
                }
            }

        }
        if (dayOfWeek == 6) {
            if (dayOfMonth == 1) {
                if (month % 2 == 0) {
                    monday?.text = (27).toString()
                    tuesday?.text = (28).toString()
                    wednesday?.text = (29).toString()
                    thursday?.text = (30).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (dayOfMonth + 2).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (28).toString()
                    tuesday?.text = (29).toString()
                    wednesday?.text = (30).toString()
                    thursday?.text = (31).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (dayOfMonth + 2).toString()
                }
            }
            if (dayOfMonth == 2) {
                if (month % 2 == 0) {
                    monday?.text = (28).toString()
                    tuesday?.text = (29).toString()
                    wednesday?.text = (30).toString()
                    thursday?.text = (dayOfMonth - 1).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (dayOfMonth + 2).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (29).toString()
                    tuesday?.text = (30).toString()
                    wednesday?.text = (31).toString()
                    thursday?.text = (dayOfMonth - 1).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (dayOfMonth + 2).toString()
                }
            }
            if (dayOfMonth == 3) {
                if (month % 2 == 0) {
                    monday?.text = (29).toString()
                    tuesday?.text = (30).toString()
                    wednesday?.text = (dayOfMonth - 2).toString()
                    thursday?.text = (dayOfMonth - 1).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (dayOfMonth + 2).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (30).toString()
                    tuesday?.text = (31).toString()
                    wednesday?.text = (dayOfMonth - 2).toString()
                    thursday?.text = (dayOfMonth - 1).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (dayOfMonth + 2).toString()
                }
            }
            if (dayOfMonth == 4) {
                if (month % 2 == 0) {
                    monday?.text = (30).toString()
                    tuesday?.text = (dayOfMonth - 3).toString()
                    wednesday?.text = (dayOfMonth - 2).toString()
                    thursday?.text = (dayOfMonth - 1).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (dayOfMonth + 2).toString()
                } else if (month % 2 == 1) {
                    monday?.text = (31).toString()
                    tuesday?.text = (dayOfMonth - 3).toString()
                    wednesday?.text = (dayOfMonth - 2).toString()
                    thursday?.text = (dayOfMonth - 1).toString()
                    friday?.text = (dayOfMonth).toString()
                    saturday?.text = (dayOfMonth + 1).toString()
                    sunday?.text = (dayOfMonth + 2).toString()
                }
            }

        }
        if (dayOfWeek == 5) {
            if (dayOfMonth == 1) {
                if (month % 2 == 1) {
                    monday?.text = (29).toString()
                    tuesday?.text = (30).toString()
                    wednesday?.text = (31).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (dayOfMonth + 2).toString()
                    sunday?.text = (dayOfMonth + 3).toString()
                }
                if (month % 2 == 0) {
                    monday?.text = (28).toString()
                    tuesday?.text = (29).toString()
                    wednesday?.text = (30).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (dayOfMonth + 2).toString()
                    sunday?.text = (dayOfMonth + 3).toString()
                }
            } else if (dayOfMonth == 2) {

                if (month % 2 == 1) {
                    monday?.text = (30).toString()
                    tuesday?.text = (31).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (dayOfMonth + 2).toString()
                    sunday?.text = (dayOfMonth + 3).toString()
                }
                if (month % 2 == 0) {
                    monday?.text = (29).toString()
                    tuesday?.text = (30).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (dayOfMonth + 2).toString()
                    sunday?.text = (dayOfMonth + 3).toString()
                }
            } else if (dayOfMonth == 3) {

                if (month % 2 == 1) {
                    monday?.text = (31).toString()
                    tuesday?.text = (dayOfMonth - 2).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (dayOfMonth + 2).toString()
                    sunday?.text = (dayOfMonth + 3).toString()
                }
                if (month % 2 == 0) {
                    monday?.text = (30).toString()
                    tuesday?.text = (dayOfMonth - 2).toString()
                    wednesday?.text = (dayOfMonth - 1).toString()
                    thursday?.text = (dayOfMonth).toString()
                    friday?.text = (dayOfMonth + 1).toString()
                    saturday?.text = (dayOfMonth + 2).toString()
                    sunday?.text = (dayOfMonth + 3).toString()
                }
            }

        }
        if (dayOfWeek == 4) {
            if (dayOfMonth == 1) {
                if (month % 2 == 1) {
                    monday?.text = (30).toString()
                    tuesday?.text = (31).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (dayOfMonth + 1).toString()
                    friday?.text = (dayOfMonth + 2).toString()
                    saturday?.text = (dayOfMonth + 3).toString()
                    sunday?.text = (dayOfMonth + 4).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (29).toString()
                    tuesday?.text = (30).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (dayOfMonth + 1).toString()
                    friday?.text = (dayOfMonth + 2).toString()
                    saturday?.text = (dayOfMonth + 3).toString()
                    sunday?.text = (dayOfMonth + 4).toString()
                }
            } else if (dayOfMonth == 2) {
                if (month % 2 == 1) {
                    monday?.text = (31).toString()
                    tuesday?.text = (dayOfMonth - 1).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (dayOfMonth + 1).toString()
                    friday?.text = (dayOfMonth + 2).toString()
                    saturday?.text = (dayOfMonth + 3).toString()
                    sunday?.text = (dayOfMonth + 4).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (30).toString()
                    tuesday?.text = (dayOfMonth - 1).toString()
                    wednesday?.text = (dayOfMonth).toString()
                    thursday?.text = (dayOfMonth + 1).toString()
                    friday?.text = (dayOfMonth + 2).toString()
                    saturday?.text = (dayOfMonth + 3).toString()
                    sunday?.text = (dayOfMonth + 4).toString()
                }
            }
        }

        if (dayOfWeek == 3) {
            if (dayOfMonth == 1) {
                if (month % 2 == 1) {
                    monday?.text = (31).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (dayOfMonth + 2).toString()
                    friday?.text = (dayOfMonth + 3).toString()
                    saturday?.text = (dayOfMonth + 4).toString()
                    sunday?.text = (dayOfMonth + 5).toString()
                } else if (month % 2 == 0) {
                    monday?.text = (30).toString()
                    tuesday?.text = (dayOfMonth).toString()
                    wednesday?.text = (dayOfMonth + 1).toString()
                    thursday?.text = (dayOfMonth + 2).toString()
                    friday?.text = (dayOfMonth + 3).toString()
                    saturday?.text = (dayOfMonth + 4).toString()
                    sunday?.text = (dayOfMonth + 5).toString()
                }
            }
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
}