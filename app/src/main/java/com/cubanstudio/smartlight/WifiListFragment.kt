package com.cubanstudio.smartlight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView

class WifiListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.wifi_list_fragment, container, false)
        var text = ArrayList<String>()
        text.add("WIFI1")
        text.add("WIFI2")
        text.add("WIFI3")
        text.add("WIFI4")
        text.add("WIFI5")
        text.add("WIFI6")

        val adapter = WifiAdapter(context!!.applicationContext,text)
        val listView=  view.findViewById<ListView>(R.id.wifiListView)
        listView.adapter = adapter
        view.findViewById<ListView>(R.id.wifiListView).adapter = adapter

        return view
    }

}