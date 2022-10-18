package com.example.dialogs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

import android.widget.Button
import android.widget.TextView
import java.util.jar.Attributes.Name

class MainActivity2 : AppCompatActivity() {
    lateinit var receiver_msg: TextView
    lateinit var button: Button
    lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        receiver_msg = findViewById(R.id.received_value_id)
        val intent = intent
        val str = intent.getStringExtra("message_key")
        receiver_msg.text = str
        button = findViewById<Button>(R.id.buttonRetour)
        button.setOnClickListener {
            val shareIntent : Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM,str)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent,null))
        }
        btn = findViewById<Button>(R.id.url)
        btn.setOnClickListener{
            val myWebView: WebView = findViewById(R.id.webView)
            myWebView.loadUrl("http://www.google.com")
        }
    }
}