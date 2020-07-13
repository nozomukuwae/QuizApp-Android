package com.example.quizapp

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ResultActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    fun onFinish(view: View) {
        setResult(Activity.RESULT_OK)
        finish()
    }
}