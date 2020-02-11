package com.cubanstudio.smartlight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class DeviceListFragment: Fragment() {
    lateinit var btService: BluetoothService
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
        val appcontext = context!!.applicationContext
        btService = BluetoothService(appcontext)
        btService.bluetoothInit()
        if(btService.getTargetDevice(resources.getString(R.string.target_device))!=null){
            btService.connectDevice(btService.getFoundDevice())
        }else{
            //TODO find and bond with device
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            when(text[position]){
                "Addressable LED Strip" -> {
                    val ft = fragmentManager?.beginTransaction()
                    ft?.replace(R.id.contain,LightSettingsFragment())
                    ft?.addToBackStack("Main")
                    ft?.commit()

                }
            }
        }
        return view
    }

}