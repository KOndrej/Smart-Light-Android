package com.cubanstudio.smartlight

import android.net.MacAddress
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiNetworkSpecifier
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class WifiLoginFragment(private val wifiSSID:String,private val wifiBSSID: String) : Fragment() {
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
        connectBut.setOnClickListener {

        }

        return view
    }
}
