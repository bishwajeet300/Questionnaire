package com.bishwajeet.questionnaire.utils

import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.Question


const val MIN_CREDENTIAL_LENGTH = 3

fun isUsernameValid(username: String) = username.length >= MIN_CREDENTIAL_LENGTH


fun getQuestionsAnswer(question: Question, answerList: List<Answer>?): String {
    if(null != answerList) {
        for (currentAnswer in answerList) {
            if(currentAnswer.questionId == question.id.toString()) {
                return currentAnswer.answer
            }
        }
    }
    return VAL_RETURN_NO_ANSWER
}