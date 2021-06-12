package fr.epf.min.projet_paul_barthe_min2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {
    lateinit var mScannerView: ZBarScannerView

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.title = "Barcode Scanner"
        mScannerView = ZBarScannerView(this)
        setContentView(mScannerView)
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        val code = rawResult?.contents
        mScannerView.resumeCameraPreview(this)
        val intent = Intent(this, DetailsProductActivity::class.java)
        intent.putExtra("code", code)
        startActivity(intent)
    }


}