package com.example.quizapp

data class Question (
    val id: Int,
    val question: String,
    val image: Int,
    val options: Array<String>,
    val answerIndex: Int
)
