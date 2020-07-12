package com.example.quizapp

class QuestionFactory {
    fun createQuestions(): Array<Question> {
        val q1 = Question(1,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_argentina,
            arrayOf("Armenia", "Argentina",  "United States of America", "Azerbaijan"),
            1
        )

        val q2 = Question(2,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_australia,
            arrayOf("Austria", "Argentina",  "Auckland", "Australia"),
            3
        )

        val q3 = Question(3,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_belgium,
            arrayOf("Brazil", "Belgium",  "Britain", "Brussels"),
            1
        )

        val q4 = Question(4,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_brazil,
            arrayOf("Brazil", "Belgium",  "Britain", "Berlin"),
            0
        )

        val q5 = Question(5,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_denmark,
            arrayOf("Djibouti", "Dominica",  "Denmark", "Deutschland"),
            2
        )

        return arrayOf(q1, q2, q3, q4, q5)
    }
}