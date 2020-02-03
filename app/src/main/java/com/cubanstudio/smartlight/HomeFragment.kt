package com.cubanstudio.smartlight

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.home_fragment, container, false)

        view.findViewById<MaterialButton>(R.id.adddevice).setOnClickListener {

            val ft = fragmentManager?.beginTransaction()
            ft?.replace(R.id.contain,WifiListFragment())
            ft?.addToBackStack("Main")
            ft?.commit()

        }
        return view
    }
}