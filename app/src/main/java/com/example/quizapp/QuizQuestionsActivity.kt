package com.example.quizapp

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import java.lang.IllegalArgumentException

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val REQUEST_CODE = 1
        const val NAME_KEY = "Name"
        const val SCORE_KEY = "Score"
        const val QUESTION_COUNT_KEY = "QuestionCount"
    }

    enum class SubmitMode {
        NOT_YET, DONE
    }

    private val questionFactory = QuestionFactory()
    private var questionList: Array<Question>? = null
    private var currentIndex = 0
    private var selectedOptionView: TextView? = null
    private var score = 0
    private var submitMode = SubmitMode.NOT_YET
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        name = intent.getStringExtra(MainActivity.NAME_KEY)

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
        if (submitMode != SubmitMode.NOT_YET) return
        if (view !is TextView) return

        highlightOptions(view)
        selectedOptionView = view
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
        val selectedOptionViewVal = selectedOptionView

        when(submitMode) {
            SubmitMode.NOT_YET -> {
                if (selectedOptionViewVal != null
                    && getOptionIndex(selectedOptionViewVal) == questionListVal[currentIndex].answerIndex) {
                    score ++
                    selectedOptionViewVal.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                } else {
                    selectedOptionViewVal?.background = ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
                    val correctOptionView = getOptionView(questionListVal[currentIndex].answerIndex)
                    correctOptionView.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                }

                submitMode = SubmitMode.DONE
                selectedOptionView = null
                if (currentIndex < questionListVal.size - 1) {
                    submit_button.text = getString(R.string.go_to_next_question_button)
                } else {
                    submit_button.text = getString(R.string.show_result_button)
                }
            }
            SubmitMode.DONE -> {
                submitMode = SubmitMode.NOT_YET

                if (currentIndex < questionListVal.size - 1) {
                    currentIndex ++
                    submit_button.text = getString(R.string.submit_button)
                    showQuestion()
                } else {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(NAME_KEY, name)
                    intent.putExtra(SCORE_KEY, score)
                    intent.putExtra(QUESTION_COUNT_KEY, questionListVal.size)
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                currentIndex = 0
                score = 0
                submit_button.text = getString(R.string.submit_button)
                showQuestion()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getOptionIndex(view: View): Int {
        return when(view.id) {
            option1.id -> 0
            option2.id -> 1
            option3.id -> 2
            option4.id -> 3
            else -> throw IllegalArgumentException("$view is unknown")
        }
    }

    private fun getOptionView(index: Int): TextView {
        return when(index) {
            0 -> option1
            1 -> option2
            2 -> option3
            3 -> option4
            else -> throw IllegalArgumentException("$index is unknown")
        }
    }
}