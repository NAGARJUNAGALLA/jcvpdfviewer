package com.example.pdfviewer

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView

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
        setContentView(R.layout.activity_main)

        pdfView = findViewById(R.id.pdfView)
        val btnSelectPdf = findViewById<Button>(R.id.btnSelectPdf)

        val intentUri: Uri? = intent.data
        if (intentUri != null) {
            displayPdf(intentUri)
        }
        // The crashing ByteArray(0) dummy load has been permanently removed!

        btnSelectPdf.setOnClickListener {
            pickPdfLauncher.launch("application/pdf")
        }
    }

    private fun displayPdf(uri: Uri) {
        try {
            pdfView.fromUri(uri)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onError { 
                    // Safely catches PDF rendering errors instead of crashing
                    Toast.makeText(this, "Cannot read this PDF file", Toast.LENGTH_LONG).show()
                }
                .load()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to open file", Toast.LENGTH_SHORT).show()
        }
    }
}
