package com.example.project

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class GPA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpa)

        val URL = "content://com.example.MyApplication.CoursesProvider"
        val courses = Uri.parse(URL)
        //\  val c = contentResolver!!.query(students,null,null,null,"name")
        var c = contentResolver.query(courses, null, null, null, null)
        //val c = managedQuery(students, null, null, null, "name")
        var sum = 0;
        var count = 0;
        if (c != null) {
            if (c?.moveToFirst() == true) {
                do {
                    sum += c.getInt(CoursesProvider.GRADE.toInt())
                    count++;
                } while (c.moveToNext())
            }
        }
        val tvGPA: TextView = findViewById(R.id.tvGPA)
        if (count==0)
            tvGPA.text = 0.toString();
        else {
            tvGPA.text = (sum/count).toString();
        }


    }

}

