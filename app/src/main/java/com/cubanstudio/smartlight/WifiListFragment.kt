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
        var items = ArrayList<String>()
        items.add("WIFI")
        items.add("WIFI")
        items.add("WIFI")
        items.add("WIFI")
        items.add("WIFI")
        items.add("WIFI")
        items.add("WIFI")
        items.add("WIFI")
        var view = inflater.inflate(R.layout.wifi_list_fragment, container, false)
        var adapter = ArrayAdapter<String>(context!!.applicationContext,android.R.layout.simple_list_item_1,items)
        view.findViewById<ListView>(R.id.wifiListView).adapter = adapter

        return view
    }

}