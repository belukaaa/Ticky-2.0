package com.raywenderlich.ticky

import android.content.Context

class MySharedPreference(context : Context) {

    private val ONBOARDING_JUST_ONCE = ""
    private val KEY = "Key"
    private val pref = context.getSharedPreferences(ONBOARDING_JUST_ONCE , Context.MODE_PRIVATE)
    private val editor = pref.edit()


    private val JUST_ONE_TASK = ""
    private val TASK = "Task"
    private val pref1 = context.getSharedPreferences(JUST_ONE_TASK , Context.MODE_PRIVATE)
    private val editor1 = pref1.edit()


    private val TASK_SELECTED = ""
    private val ONE_ON_ONE = "One"

    private val pref2 = context.getSharedPreferences(TASK_SELECTED , Context.MODE_PRIVATE)
    private val editor2  = pref2.edit()

    fun saveExe(value: String) {
        editor1.putString(TASK,value)
        editor1.apply()
    }

    fun taskExe(): String? {
        return pref1.getString(TASK , "")
    }

    fun saveWhenAplicationFirstOpened(value: Boolean){
        editor.putBoolean(KEY,value)
        editor.apply()
    }
    fun getWhenAplicationFirstOpened(): Boolean {
        return pref.getBoolean(KEY , false)
    }


    fun firstTaskSelected(value: Boolean) {
        editor2.putBoolean(ONE_ON_ONE , value)
        editor2.apply()
    }
    fun taskSelected(): Boolean {
         return pref2.getBoolean(ONE_ON_ONE , false)
    }
}