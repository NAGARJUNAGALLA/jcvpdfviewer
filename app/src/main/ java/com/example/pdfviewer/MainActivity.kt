package com.example.pdfviewer

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView

class MainActivity : AppCompatActivity() {
    
    private lateinit var pdfView: PDFView

    private val pickPdfLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            pdfView.fromUri(uri)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .load()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pdfView = findViewById(R.id.pdfView)
        val btnSelectPdf = findViewById<Button>(R.id.btnSelectPdf)

        // FIX: Loads a 0-byte blank array so the app doesn't crash on startup
        pdfView.fromBytes(ByteArray(0)).load()

        btnSelectPdf.setOnClickListener {
            pickPdfLauncher.launch("application/pdf")
        }
    }
}
