package com.cubanstudio.smartlight

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.alarm_fragment.*
import java.text.SimpleDateFormat


class AlarmFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        var view = inflater.inflate(R.layout.alarm_fragment, container, false)

        view.findViewById<MaterialTextView>(R.id.timePick).setOnClickListener {
            fragmentManager?.popBackStackImmediate()
           // TimePickerDialog( context, TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
             //   timePick.setText(String.format("%02d:%02d",i,i2))
            //},0,0,true).show()
        }

        return view
    }

    fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
       // timePick.set//.set(Calendar.HOUR_OF_DAY, hourOfDay)
        //rest of the code
    }
}