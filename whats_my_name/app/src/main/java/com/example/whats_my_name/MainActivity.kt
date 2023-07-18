package com.example.whats_my_name

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            setNameOutput()
        }
    }

    private fun setNameOutput() {
        val editText = findViewById<EditText>(R.id.editTextText)
        val output = findViewById<TextView>(R.id.textView2)
        val textOutput = "You are ${editText.editableText}? Don't lie to me. I don't believe you"
        output.text = textOutput
    }
}