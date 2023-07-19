package com.example.tukangmakan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkbox1 = findViewById<CheckBox>(R.id.checkBox)
        val checkbox2 = findViewById<CheckBox>(R.id.checkBox2)

        checkbox1.setOnClickListener {
            getTukangMakan()
        }

        checkbox2.setOnClickListener {
            getTukangMakan()
        }
    }

    private fun getTukangMakan() {
        val checkbox1 = findViewById<CheckBox>(R.id.checkBox)
        val checkbox2 = findViewById<CheckBox>(R.id.checkBox2)
        val outputTextView = findViewById<TextView>(R.id.outputText)

        var output = ""
        if (checkbox1.isChecked && checkbox2.isChecked) {
            output = "Saya adalah omnivora, bisa makan daging dan sayur"
        } else if (checkbox1.isChecked) {
            output = "Saya adalah herbivora, hanya makan sayur"
        } else if (checkbox2.isChecked) {
            output = "Saya adalah karnivora, hanya makan daging"
        }
        outputTextView.text = output

    }
}