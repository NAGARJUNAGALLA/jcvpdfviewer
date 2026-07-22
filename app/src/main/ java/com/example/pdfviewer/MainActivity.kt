package com.example.pdfviewer

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView

class MainActivity : AppCompatActivity() {
    
    private var pdfView: PDFView? = null

    private val pickPdfLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            displayPdf(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            setContentView(R.layout.activity_main)

            pdfView = findViewById(R.id.pdfView)
            val btnSelectPdf = findViewById<Button>(R.id.btnSelectPdf)

            val intentUri: Uri? = intent.data
            if (intentUri != null) {
                displayPdf(intentUri)
            }

            btnSelectPdf?.setOnClickListener {
                pickPdfLauncher.launch("application/pdf")
            }
        } catch (t: Throwable) {
            // Catching Throwable guarantees even fatal system crashes are caught
            Toast.makeText(this, "Setup Error: ${t.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayPdf(uri: Uri) {
        try {
            pdfView?.fromUri(uri)
                ?.defaultPage(0)
                ?.enableSwipe(true)
                ?.swipeHorizontal(false)
                ?.onError { t -> 
                    Toast.makeText(this, "PDF Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
                ?.load()
        } catch (t: Throwable) {
            Toast.makeText(this, "Failed to load: ${t.message}", Toast.LENGTH_LONG).show()
        }
    }
}
