package com.example.project

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisteredCourses : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registered_courses)
        val btnAddCourses : Button = findViewById(R.id.btnAddCourses)

        btnAddCourses.setOnClickListener { view ->
            if ((findViewById<EditText>(R.id.edCourseName)).text.toString()=="" || (findViewById<EditText>(R.id.edCourseGrade)).text.toString()=="" || (findViewById<EditText>(R.id.edCourseHours)).text.toString()==""){
                Toast.makeText(this, "Invalid Entry: One or More Fields are Empty", Toast.LENGTH_SHORT).show()
            }
            else{
                val values = ContentValues()
                values.put(
                    CoursesProvider.COURSENAME,
                    (findViewById<EditText>(R.id.edCourseName)).text.toString()
                )
                values.put(
                    CoursesProvider.GRADE,
                    (findViewById<EditText>(R.id.edCourseGrade)).text.toString()
                )
                values.put(
                    CoursesProvider.HOURS,
                    (findViewById<EditText>(R.id.edCourseHours)).text.toString()
                )

                val uri = contentResolver.insert(
                    CoursesProvider.CONTENT_URI, values
                )
                Toast.makeText(baseContext, uri.toString(), Toast.LENGTH_LONG).show()
            }
        }


    }
}