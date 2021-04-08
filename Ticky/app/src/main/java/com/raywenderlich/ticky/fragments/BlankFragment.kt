package com.raywenderlich.resizekeyboardwhenitsappears

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.raywenderlich.ticky.R
import kotlinx.android.synthetic.main.fragment_blank.view.*


class BlankFragment(val clickevent : (int : Int) -> Unit): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val imm1 = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm1.showSoftInput(view.editText1, InputMethodManager.SHOW_IMPLICIT)


        view.textView2.setOnClickListener {

            clickevent.invoke(2)

        }

    }

}