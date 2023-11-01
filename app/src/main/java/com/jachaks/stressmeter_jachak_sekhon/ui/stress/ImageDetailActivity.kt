package com.jachaks.stressmeter_jachak_sekhon.ui.stress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.jachaks.stressmeter_jachak_sekhon.R
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class ImageDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        val imageResourceId = intent.getIntExtra("imageResourceId", -1)
        val stressLevel = intent.getIntExtra("stressLevel", -1)

        val stressImage = findViewById<ImageView>(R.id.ivStressPic)
        val selectButton = findViewById<Button>(R.id.selectButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        stressImage.setImageResource(imageResourceId)

        selectButton.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            writeToCsv(currentTime, stressLevel)
            finishAffinity()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun writeToCsv(time: Long, stressLevel: Int) {
        val currentTime = System.currentTimeMillis()

        val fileName = "stress_timestamp.csv"
        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

        try {
            val file = File(externalFilesDir, fileName)

            val writer = BufferedWriter(FileWriter(file, true))

            // If the file is new, add a header line
            if (file.length() == 0L) {
                writer.write("Timestamp,Stress Level\n")
            }

            writer.write("$currentTime,$stressLevel\n")

            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()

            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error writing to the file", Toast.LENGTH_SHORT).show()
        }
    }

}