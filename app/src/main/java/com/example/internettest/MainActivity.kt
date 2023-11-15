package com.example.internettest


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val myTag: String = "Flickr cats"
    val OkTag: String = "Flickr OkCats"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var myButton: Button = findViewById(R.id.btnHTTP)
        val url = URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
        myButton.setOnClickListener {
            Thread {
                val connection = url.openConnection() as HttpURLConnection
                try{
                    val data = connection.inputStream.bufferedReader().use { data ->
                        connection.disconnect()
                        Log.d(myTag, data.readText())
                    }
                   // connection.disconnect()
                   // Log.d(myTag, data.readText())
                } catch(e: Exception) {
                    e.printStackTrace()
                    myButton.setBackgroundColor(Color.RED)
                }
            }.start()
        }
        val okButton: Button = findViewById(R.id.btnOkHTTP)
        okButton.setOnClickListener {
            val client = OkHttpClient();

            val request = Request.Builder()
                .url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        val text = it.body?.string()
                        Log.i(OkTag, text.toString())
                    }
                }
            })
        }


    }
}