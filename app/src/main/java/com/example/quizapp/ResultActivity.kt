package com.example.quizapp

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val name = intent.getStringExtra(QuizQuestionsActivity.NAME_KEY)
        val score = intent.getIntExtra(QuizQuestionsActivity.SCORE_KEY, 0)
        val questionCount = intent.getIntExtra(QuizQuestionsActivity.QUESTION_COUNT_KEY, 0)

        tv_name.text = name
        tv_score.text = "Your Score is $score out of $questionCount"
    }

    fun onFinish(view: View) {
        setResult(Activity.RESULT_OK)
        finish()
    }
}