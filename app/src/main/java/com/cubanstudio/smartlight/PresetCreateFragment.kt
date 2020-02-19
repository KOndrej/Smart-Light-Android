package com.cubanstudio.smartlight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.datepicker.MaterialTextInputPicker

class PresetCreateFragment(var parent: PresetsFragment) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.preset_create_fragment, container, false)
        var delaypicker =view.findViewById<NumberPicker>(R.id.turnoffdelazpicker)
        delaypicker.minValue = 1
        delaypicker.maxValue =30
        var label  = view.findViewById<TextView>(R.id.presetLabel)
        var time = view.findViewById<TimePicker>(R.id.timePicker)
        var effect  = view.findViewById<TextView>(R.id.effectdropdown)
        var mon  = view.findViewById<MaterialCheckBox>(R.id.monBox)
        var tue  = view.findViewById<MaterialCheckBox>(R.id.tueBox)
        var wed  = view.findViewById<MaterialCheckBox>(R.id.wedBox)
        var thu  = view.findViewById<MaterialCheckBox>(R.id.thuBox)
        var fri  = view.findViewById<MaterialCheckBox>(R.id.friBox)
        var sat  = view.findViewById<MaterialCheckBox>(R.id.satBox)
        var sun  = view.findViewById<MaterialCheckBox>(R.id.sunBox)





        view.findViewById<MaterialButton>(R.id.addpresetButton).setOnClickListener {
            val list =arrayListOf<Boolean>(mon.isChecked,tue.isChecked,wed.isChecked,thu.isChecked,fri.isChecked,sat.isChecked,sun.isChecked)
            var routine = Routine(label.text.toString(),String.format("%d:%02d",time.hour,time.minute),true,list,effect.text.toString())
            parent.routines.add(routine)
            parent.adapter.notifyDataSetChanged()
            fragmentManager?.popBackStack()
        }

        return view
    }
}