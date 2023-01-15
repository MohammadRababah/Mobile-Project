package com.example.project

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Calc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)

        val button: Button = findViewById(R.id.CalculateBtn)
        val edtxt1: EditText = findViewById(R.id.FirstM)
        val edtxt2: EditText = findViewById(R.id.SecondM)
        val edtxt3: EditText = findViewById(R.id.ThirdM)
        val resultTV: TextView = findViewById(R.id.textview7)
        var flag : Int = 0
        var flagX = false;
        var flagY = false;
        var flagZ = false;


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

        button.setOnClickListener{ view ->
            var x: Int = edtxt1.text.toString().toInt();
            var y: Int = edtxt2.text.toString().toInt();
            var z: Int = edtxt3.text.toString().toInt();
            if(edtxt1.text.toString() != ""){

                flag++
                flagX = true;
            }
            if(edtxt2.text.toString() != ""){

                flag++
                flagY = true
            }
            if(edtxt3.text.toString() != "") {

                flag++
                flagZ = true;
            }

            if (flag==0){
                resultTV.text = ((sum)/(count+flag)).toString()
            }
            else if (flag==1){
                if (flagX){
                    resultTV.text = ((sum+x)/(count+flag)).toString()
                }
                else if (flagY){
                    resultTV.text = ((sum+y)/(count+flag)).toString()
                }
                else {
                    resultTV.text = ((sum+z)/(count+flag)).toString()
                }
            }
            else if (flag==2){
                if (flagX && flagY){
                    resultTV.text = ((sum+x+y)/(count+flag)).toString()
                }
                else if (flagX && flagZ){
                    resultTV.text = ((sum+x+z)/(count+flag)).toString()
                }
                else {
                    resultTV.text = ((sum+x+z)/(count+flag)).toString()
                }
            }

            else {
                resultTV.text = ((sum+x+y+z)/(count+flag)).toString()
            }
        }
    }
}