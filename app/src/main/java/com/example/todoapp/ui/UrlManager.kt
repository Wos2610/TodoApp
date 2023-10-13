package com.example.todoapp.ui

import android.content.Context
import android.widget.ImageView
import coil.load
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.util.Random

class UrlManager(private val context: Context, private val jsonFileName: String) {
    private val random = Random()
    private var urls: JSONArray? = null

    init {
        try {
            // Read JSON data from the input stream
            val inputStream: InputStream = context.assets.open(jsonFileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)

            // Parse the JSON array
            urls = JSONArray(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}