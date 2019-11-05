package com.bishwajeet.questionnaire.model.firebase.database

import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.Questionnaire
import java.util.*


interface IFirebaseDatabase {

    fun getQuestionnaire(onResult: (Questionnaire) -> Unit)


    fun getUserAnswer(userId: String, onResult: (LinkedList<Answer>) -> Unit)


    fun saveAnswer(userId: String, answer: Answer, onResult: (Boolean) -> Unit)


    fun getAnswer(userId: String, questionId: String, onResult: (Answer) -> Unit)


    fun saveQuestionnaireAnswer(userId: String, answerList: List<Answer>, onResult: (Boolean) -> Unit)
}