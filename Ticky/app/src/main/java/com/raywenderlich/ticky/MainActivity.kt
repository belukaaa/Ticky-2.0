package com.raywenderlich.ticky

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.raywenderlich.ticky.db.TaskieDatabase
import com.raywenderlich.ticky.db.dao.TaskieDao
import com.raywenderlich.ticky.fragments.*
import com.raywenderlich.ticky.repository.Factory
import com.raywenderlich.ticky.repository.TaskViewModel
import com.raywenderlich.ticky.repository.TaskieRepository
import kotlinx.android.synthetic.main.adding_activity_task.*
import kotlinx.android.synthetic.main.adding_activity_task.view.*
import kotlinx.android.synthetic.main.dialog_fragment.*
import kotlinx.android.synthetic.main.dialog_fragment.view.*
import kotlinx.android.synthetic.main.home_task_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), OnboardingFragment.ButtonClicked,
    FirstScreenFragment.Click, TaskAddingFragment.BttnClicked, TaskAddingFragment.Task_addingButton,
    HomeTaskScreenFragment.HomeTaskScreenButton , CustomDialogFragment.DialogSorting  {



    private lateinit var factory: Factory
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var taskieDao: TaskieDao
    private lateinit var taskDB: TaskieDatabase
    private lateinit var homeTaskScreenFragment: HomeTaskScreenFragment
    private lateinit var onboardingFrag: OnboardingFragment
    private lateinit var FirstScreenFrag: FirstScreenFragment
    private lateinit var mySharedPref: MySharedPreference
    private lateinit var addTaskFrag: TaskAddingFragment
    private lateinit var repository: TaskieRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        onboardingFrag = OnboardingFragment.getFirstFragInstance()
        FirstScreenFrag = FirstScreenFragment.getFirstScreenFragInstance()
        mySharedPref = MySharedPreference(this)
        addTaskFrag = TaskAddingFragment.getTaskFragInstance()
        homeTaskScreenFragment = HomeTaskScreenFragment.getHomeTaskScrenFragment()


        initViewModel(this)

        startingApp()



    }

    private fun initViewModel(context: Context) {

        taskDB = TaskieDatabase.getDatabase(context)
        taskieDao = taskDB.taskieDao()
        repository = TaskieRepository(taskieDao)
        factory = Factory(repository)
        mTaskViewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)


    }

    private fun observe(){
        mTaskViewModel.colorLIveData.observe(this, Observer {
            if (it.isEmpty()) {
                firstScreen()
            }
        })
    }
    private fun observer(){
        mTaskViewModel.colorLIveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                homeScreen()
            }
        })
    }

    private fun startingApp() {

        if (mySharedPref.getWhenAplicationFirstOpened()) {

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Main) {

                    mTaskViewModel.colorLIveData.observe(this@MainActivity, Observer {
                        if (it.isEmpty()) {
                            firstScreen()
                        }
                    })
                    mTaskViewModel.colorLIveData.observe(this@MainActivity, Observer {
                        if (it.isNotEmpty()) {
                            homeScreen()
                        }
                    })

                }

            }

        }
        else {
            onboarrding()
        }
    }




    private fun onboarrding() {

               supportFragmentManager
               .beginTransaction()
               .setCustomAnimations(
                   R.anim.slide_in,
                   R.anim.first_fragment_animation,
                   R.anim.fade_in,
                   R.anim.slide_out
               )
               .replace(R.id.frame_id, onboardingFrag)
                .commit()


        mySharedPref.saveWhenAplicationFirstOpened(true)
    }

    override fun bttnClicked() {

        mTaskViewModel.colorLIveData.observe(this, Observer {
            if (it.isEmpty()) {
                observe()
            } else {
                observer()
            }
        })

    }
    private fun firstScreen() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.first_fragment_animation,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.frame_id, FirstScreenFrag)
            .commit()
    }

    private fun homeScreen() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.first_fragment_animation,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.frame_id, homeTaskScreenFragment)
            .commit()
    }

    override fun buttonClicked() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.first_fragment_animation,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.frame_id, FirstScreenFrag)
            .commit()
    }

    override fun Clicked() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_up,
                R.anim.slide_out_down,
                R.anim.slide_in_up,
                R.anim.slide_out_down
            )
            .replace(R.id.frame_id, addTaskFrag)
            .commit()
    }



    override fun taskAdd() {
        supportFragmentManager
            .beginTransaction()
            .addSharedElement(Task_input, Task_input.transitionName)
            .replace(R.id.frame_id, homeTaskScreenFragment)
            .commit()
    }

    override fun homeTaskScrenButton() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_up,
                R.anim.slide_out_down,
                R.anim.slide_in_up,
                R.anim.slide_out_down
            )
            .replace(R.id.frame_id, addTaskFrag)
            .commit()
    }

    override fun sortBy(sort: String) {
        Log.e("DADE", "Movida -> $sort")
        if (sort == "Date added") {
            mTaskViewModel.getSelectedData().observe(this, Observer {
                homeTaskScreenFragment.adapter.setData(it)
            })
            homeTaskScreenFragment.textView5.text = sort

            Toast.makeText(applicationContext, "Sorted by creation date", Toast.LENGTH_SHORT).show()
        }
        else if (sort == "Due date") {
            mTaskViewModel.getTasksByDate().observe(this, Observer {
                homeTaskScreenFragment.adapter.setData(it)
            })
            homeTaskScreenFragment.textView5.text = sort

            Toast.makeText(applicationContext, "Sorted by date", Toast.LENGTH_SHORT).show()
        }
        else if (sort == "Color label") {
            mTaskViewModel.sortTasksByColor().observe(this, Observer {
                homeTaskScreenFragment.adapter.setData(it)
            })
            homeTaskScreenFragment.textView5.text = sort

            Toast.makeText(applicationContext, "Sorted by color", Toast.LENGTH_SHORT).show()
        }



//        CustomDialogFragment().show(supportFragmentManager , "Custom Dialog")
//
//        val dialog = CustomDialogFragment()
//
//
//        date_added.setOnClickListener {
//            val selectedID = radio_group.checkedRadioButtonId
//            val radio = findViewById<RadioButton>(selectedID)
//            val result = radio.text.toString()
//            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//
//        }
    }




//    override fun getDate() {
//       val  calendar : Calendar = Calendar.getInstance()
//         day = calendar.get(Calendar.DAY_OF_MONTH)
//         month = calendar.get(Calendar.MONTH)
//         year = calendar.get(Calendar.YEAR)
//
//        val datePickerDialog =
//            DatePickerDialog(this, this ,year , month , day  )
//        datePickerDialog.show()
//
//    }
//
//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        myDay = day
//        myYear = year
//        myMonth = month
//        calendar.textAlignment = myYear  + myMonth + myDay
//
//
//
//    }

}


//   val data = repository.getData()
//   if (data.hasObservers()) {
//  firstScreen()
//   }else {
//    homeScreen()
//   }
