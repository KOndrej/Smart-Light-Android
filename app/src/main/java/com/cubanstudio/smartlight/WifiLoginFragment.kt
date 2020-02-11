package com.cubanstudio.smartlight

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.MacAddress
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiNetworkSpecifier
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class WifiLoginFragment(private val wifiSSID:String,private val wifiBSSID: String,private var btService: BluetoothService) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.wifi_login_fragment, container, false)
        var wifiName = view.findViewById<TextView>(R.id.loginName)
        wifiName.text = wifiSSID
        var connectBut = view.findViewById<MaterialButton>(R.id.connect)
        var passwordField = view.findViewById<TextInputEditText>(R.id.passwordInput)
        val incomeDataFilter = IntentFilter()
        incomeDataFilter.addAction("WILI") // WiFi network list
        incomeDataFilter.addAction("IPAD") // IP ADDRESS
        incomeDataFilter.addAction("CONNECTING")
        //incomeDataFilter.addAction("")
        //incomeDataFilter.addAction("")
        context?.registerReceiver(dataReceiver,incomeDataFilter)
        connectBut.setOnClickListener {
            btService.sendData("CONNECT",wifiSSID+";"+passwordField.text.toString())
        }

        return view
    }


    val dataReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            val incoming_data = intent?.getStringExtra("DATA")
            Log.e("INCOME",incoming_data)
            when(intent?.action){
                "WILI" -> {
                    /*if (!wifiArray.contains(incoming_data)){
                        wifiArray.add(incoming_data as String)
                        wifiArrayAdapter.notifyDataSetChanged()}*/
                }
                "IPAD" -> {

                    btService.disconnect()
                    fragmentManager?.popBackStack("Main",FragmentManager.POP_BACK_STACK_INCLUSIVE)

                }
                "CONNECTING" ->{
                    when(incoming_data){
                        "0" ->{
                            Log.e("CONNECTING","CONNECTED")


                        }
                        "1" ->{Log.e("CONNECTING",".............")}
                        "2" ->{Log.e("CONNECTING","FAILED")}
                    }

                }

            }
        }
    }

}
