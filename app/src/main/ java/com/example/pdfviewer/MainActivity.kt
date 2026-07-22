package com.example.pdfviewer

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    
    private lateinit var pdfView: PDFView

    private val pickPdfLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            displayPdf(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Wrapping the entire startup in a safety net so it won't force-close
        try {
            setContentView(R.layout.activity_main)

            pdfView = findViewById(R.id.pdfView)
            val btnSelectPdf = findViewById<Button>(R.id.btnSelectPdf)

            val intentUri: Uri? = intent.data
            if (intentUri != null) {
                displayPdf(intentUri)
            }

            btnSelectPdf.setOnClickListener {
                pickPdfLauncher.launch("application/pdf")
            }
        } catch (e: Exception) {
            // If the layout or setup fails, show a popup instead of crashing
            Toast.makeText(this, "Startup Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayPdf(uri: Uri) {
        try {
            // THE FIX: Open a secure data stream to bypass Android's file path security
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            
            if (inputStream != null) {
                pdfView.fromStream(inputStream)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .onError { 
                        Toast.makeText(this, "Cannot render this PDF file", Toast.LENGTH_LONG).show()
                    }
                    .load()
            } else {
                Toast.makeText(this, "Could not open secure file stream", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Catch security crashes and show them as a popup message
            Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
