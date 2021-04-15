package com.raywenderlich.ticky.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.raywenderlich.ticky.R
import kotlinx.android.synthetic.main.home_task_screen.*
import kotlinx.android.synthetic.main.onboarding.*
import kotlinx.android.synthetic.main.onboarding.view.*


class OnboardingFragment: Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.onboarding, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onboarding_button.setOnClickListener {
           myFirstTryToAnimate()
         // mySecondTryToAnimate()

       // listener?.buttonClicked()

        }

    }
    private fun myFirstTryToAnimate() {

        val fadeOutAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out_anim)
        val fadeOutAnim1 = AnimationUtils.loadAnimation(requireContext() , R.anim.onboarding_button_animation)
        val animation123 = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_from_button)
        val whiteAnimation = AnimationUtils.loadAnimation(requireContext() , R.anim.scale_white_dot)
        fadeOutAnim1.duration = 700
        fadeOutAnim.duration = 200
        animation123.duration = 500
        whiteAnimation.duration = 500



        onboarding_button1.startAnimation(animation123)

        animation123.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {
                onboarding_button.startAnimation(fadeOutAnim1)

            }
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {

                onboarding_button.visibility = INVISIBLE

                white_circular.startAnimation(whiteAnimation)
                white_circular.visibility = VISIBLE
                whiteAnimation.setAnimationListener(object: Animation.AnimationListener{
                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        listener?.buttonClicked()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                } )


            }
        })


        //  animations.duration = 7000
        imageView.startAnimation(fadeOutAnim)
        imageView.visibility = INVISIBLE
        textView.startAnimation(fadeOutAnim)
        textView.visibility = INVISIBLE
        textView2.startAnimation(fadeOutAnim)
        textView2.visibility = INVISIBLE


    }

    companion object {
        fun getFirstFragInstance(): OnboardingFragment {
            return OnboardingFragment()
        }
    }

    private var listener : ButtonClicked? = null
    interface ButtonClicked {
        fun buttonClicked ()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ButtonClicked
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}