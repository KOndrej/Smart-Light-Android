package com.cubanstudio.smartlight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class DeviceListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.device_list_fragment, container, false)
        var text = ArrayList<String>()
        text.add("Addressable LED Strip")
        var img = listOf<Int>(R.drawable.led_strip)
        val adapter = DeviceAdapter(context!!.applicationContext,text)
        val listView=  view.findViewById<ListView>(R.id.devices)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            when(text[position]){
                "Addressable LED Strip" -> {

                }
            }
        }
        return view
    }

}