package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnGPA : Button = findViewById(R.id.btnGPA)
        val btnCourses : Button = findViewById(R.id.btnCourses)
        val btnCalc : Button = findViewById(R.id.btnCalc)

        val intent1 = Intent(this, GPA::class.java)
        val intent2 = Intent(this, RegisteredCourses::class.java)
        val intent3 = Intent(this, Calc::class.java)

        btnGPA.setOnClickListener { view ->
            startActivity(intent1)
        }
        btnCourses.setOnClickListener { view ->
            startActivity(intent2)
        }
        btnCalc.setOnClickListener { view ->
            startActivity(intent3)
        }
    }
}