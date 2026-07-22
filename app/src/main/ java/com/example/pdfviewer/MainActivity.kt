package com.example.pdfviewer

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setContentView(R.layout.activity_main)

            val btnSelectPdf = findViewById<Button>(R.id.btnSelectPdf)
            btnSelectPdf.setOnClickListener {
                Toast.makeText(this, "Button works. The PDF library is the culprit.", Toast.LENGTH_LONG).show()
            }
            
            Toast.makeText(this, "App booted successfully!", Toast.LENGTH_SHORT).show()
            
        } catch (t: Throwable) {
            Toast.makeText(this, "Fatal Error: ${t.message}", Toast.LENGTH_LONG).show()
        }
    }
}
