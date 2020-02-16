package com.cubanstudio.smartlight

import android.os.AsyncTask
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class CallAPI() : AsyncTask<String,String,String>() {


    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: String?): String {
        var response:Int = 0
        var urlString = params[0]
        var data : String = params[1] as String
        var out : OutputStream
        try {
            val url: URL = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            //connection.requestMethod = "POST"
            val out = BufferedOutputStream(connection.outputStream)
            var writer = BufferedWriter(OutputStreamWriter(out,"UTF-8"))
            writer.write(data)
            writer.flush()
            writer.close()
            out.close()
            connection.connect()
            response = connection.responseCode
        }catch (e: Exception){
            e.printStackTrace()
        }
        return response.toString()
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

}


