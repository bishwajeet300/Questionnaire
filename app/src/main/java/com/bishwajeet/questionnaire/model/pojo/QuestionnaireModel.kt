package com.bishwajeet.questionnaire.model.pojo

import android.annotation.SuppressLint
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class Questionnaire(
        var categories: List<String> = listOf(),
        var questions: List<Question> = listOf()
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Question(
        var id: Long = Long.MIN_VALUE,
        var category: String = "",
        var question: String = "",
        var question_type: QuestionType = QuestionType()
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class QuestionType(
        var options: List<String> = listOf(),
        var type: String = "",
        var condition: String = ""
) : Parcelable