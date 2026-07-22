package com.example.pdfviewer

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView

class MainActivity : AppCompatActivity() {
    
    private lateinit var pdfView: PDFView

    // For the manual "Select PDF" button
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

        // Check if the app was opened by tapping a PDF in another app
        val intentUri: Uri? = intent.data
        
        if (intentUri != null) {
            // A file was passed to us, render it!
            displayPdf(intentUri)
        } else {
            // App was opened manually from the home screen, load a safe blank page
            pdfView.fromBytes(ByteArray(0)).load()
        }

        btnSelectPdf.setOnClickListener {
            pickPdfLauncher.launch("application/pdf")
        }
    }

    // A separate function to handle drawing the PDF with a safety net
    private fun displayPdf(uri: Uri) {
        try {
            pdfView.fromUri(uri)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .load()
        } catch (e: Exception) {
            // If the PDF is corrupted, this catches the error so the app doesn't close
            e.printStackTrace()
        }
    }
}
