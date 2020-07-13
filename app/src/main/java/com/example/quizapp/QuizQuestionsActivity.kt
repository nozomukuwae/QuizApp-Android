package com.example.quizapp

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import java.lang.IllegalArgumentException

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private val questionFactory = QuestionFactory()
    private var questionList: Array<Question>? = null
    private var currentIndex = 0
    private var selectedOptionIndex: Int? = null
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        questionList = questionFactory.createQuestions()
        showQuestion()

        option1.setOnClickListener(this)
        option2.setOnClickListener(this)
        option3.setOnClickListener(this)
        option4.setOnClickListener(this)
    }

    private fun showQuestion() {
        val questionListVal = questionList ?: return
        val question = questionListVal[currentIndex]

        question_text.text = question.question
        question_image.setImageDrawable(getDrawable(question.image))
        option1.text = question.options[0]
        option2.text = question.options[1]
        option3.text = question.options[2]
        option4.text = question.options[3]

        progress_bar.progress = currentIndex + 1
        progress_text.text = "${currentIndex + 1}/${questionListVal.size}"

        unhighlightOptions()
    }

    override fun onClick(view: View?) {
        if (view !is TextView) return
        highlightOptions(view)

        selectedOptionIndex = when(view?.id) {
            option1.id -> 0
            option2.id -> 1
            option3.id -> 2
            option4.id -> 3
            else -> throw IllegalArgumentException("$view is unknown")
        }
    }

    private fun highlightOptions(selectedOption: TextView) {
        selectedOption.apply {
            setTextColor(ContextCompat.getColor(this.context, R.color.darkTextColor))
            typeface = Typeface.DEFAULT_BOLD
            background = ContextCompat.getDrawable(this.context, R.drawable.selected_option_border_bg)
        }

        val options = arrayOf(option1, option2, option3, option4)
        unhighlightOptions(options.filter { it.id != selectedOption.id })
    }

    private fun unhighlightOptions(options: List<TextView>? = null) {
        val optionsToUnhighlight = options ?: listOf(option1, option2, option3, option4)

        optionsToUnhighlight.forEach {
            it.setTextColor(ContextCompat.getColor(this, R.color.lightTextColor))
            it.typeface = Typeface.DEFAULT
            it.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    fun onSubmit(view: View) {
        val questionListVal = questionList ?: return

        if (selectedOptionIndex == questionListVal[currentIndex].answerIndex) {
            score ++
        }

        if (currentIndex < questionListVal.size - 1) {
            currentIndex ++
            showQuestion()
        } else {
            currentIndex = 0
            showQuestion()
        }
    }
}