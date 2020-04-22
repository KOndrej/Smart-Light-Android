package com.cubanstudio.smartlight

import android.R.attr
import android.provider.ContactsContract
import android.util.Log
import org.jetbrains.anko.doAsync
import java.io.PrintWriter
import java.net.*


class SocketClient() {
    lateinit var buffer: PrintWriter
    lateinit var socket: DatagramSocket
    lateinit var adresses: ArrayList<InetSocketAddress>

    var beforeData = ""

    fun initSocket(){
        socket = DatagramSocket()
        adresses = ArrayList<InetSocketAddress>()

    }

    fun addDevice(adress: String,port:Int){
        adresses.add(InetSocketAddress.createUnresolved(adress,port))
    }
    fun sendMessage(head: String, vararg body: String) {
            Log.e(head,body[0])
            var data = "\u0001${head}\u0002${body[0]}"
            if (body.size > 1) {
                for (mess in body) {
                    if(mess!=body[0]){
                    data += ";"
                    data += mess
                    }
                }
            }
            data += "\u0003${checkSum(data)}\u0004\r"
            if (beforeData != data) {
                doAsync {

                    for (add in adresses){
                        val dp = DatagramPacket(data.toByteArray(),data.length,add)
                        socket.send(dp)
                    }


                }
                beforeData = data
            }


    }


    private fun checkSum(data: String): String {
        var c1: Long = 0
        var c2: Long = 0
        for (c in data.indices) {
            c1 += c.toLong()
            c2 += c1
        }
        var check = (c1 % 255).toULong().toString(16)
        check += (c2 % 255).toULong().toString(16)
        return check
    }

}