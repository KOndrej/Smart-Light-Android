package com.cubanstudio.smartlight

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager


class WiFiScanReceiver(var wifiManager: WifiManager,var fragment: WifiListFragment) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
            fragment.wifiList.clear()
            wifiManager.scanResults.forEach {
                var locked = (it.capabilities.contains("WEP")||it.capabilities.contains("PSK")||it.capabilities.contains("EAP"))
                val item = WifiItem(it.SSID,"",it.level,locked)

                    fragment.wifiList.add(item)



            }
            fragment.wifiList.sortBy {  it.wifiStrength}
            fragment.wifiList.reverse()
            fragment.adapter.notifyDataSetChanged()
        }
    }
}



