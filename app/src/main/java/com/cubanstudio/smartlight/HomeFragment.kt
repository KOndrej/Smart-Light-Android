package com.cubanstudio.smartlight

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.internal.NavigationMenu
import kotlinx.android.synthetic.main.activity_screen_slide.*
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {
    companion object IPadress{
        lateinit var address : String
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.home_fragment, container, false)

        view.findViewById<MaterialButton>(R.id.adddevice).setOnClickListener {
            val anim1 = TranslateAnimation(0f,0f,0f,200f)
            anim1.duration = 500
            val anim2 = AlphaAnimation(1f,0f)
            anim2.duration = 350
            val anim3 = AnimationSet(false)
            anim3.addAnimation(anim1)
            anim3.addAnimation(anim2)
            val navBar =
            activity?.findViewById<BottomNavigationView>(R.id.navbar)
            navBar?.startAnimation(anim3)
            navBar?.visibility = View.INVISIBLE

            val ft = fragmentManager?.beginTransaction()
            ft?.replace(R.id.contain,DeviceListFragment())
            ft?.addToBackStack("Main")
            ft?.commit()

        }
        view.findViewById<MaterialButton>(R.id.POST).setOnClickListener {

            var api = CallAPI()
            api.execute(address,"","")
        }
        return view
    }


}