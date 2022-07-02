package com.maksumon.innov8tif.activities

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupProgressDialog()
    }

    private fun setupProgressDialog() {
        try {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Loading...")
            val progressBar = ProgressBar(this)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            progressBar.layoutParams = lp
            builder.setView(progressBar)
            builder.setCancelable(false)
            progressDialog = builder.create()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showProgressDialog() {
        progressDialog.show()
    }

    fun hideProgressDialog() {
        progressDialog.dismiss()
    }
}