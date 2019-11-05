package com.bishwajeet.questionnaire.utils

import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.Question
import com.bishwajeet.questionnaire.model.pojo.QuestionType
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class CommonUtilTest {

    @Test
    fun usernameValidity() {

        Assert.assertEquals(isUsernameValid(""), false)

        Assert.assertEquals(isUsernameValid("a"), false)

        Assert.assertEquals(isUsernameValid("ab"), false)

        Assert.assertEquals(isUsernameValid("abc"), true)
    }


    @Test
    fun getQuestionsAnswer() {

        Assert.assertEquals(getQuestionsAnswer(question, answerList = null), VAL_RETURN_NO_ANSWER)

        Assert.assertEquals(getQuestionsAnswer(question, answerList), rightAnswer)
    }


    companion object {
        var rightAnswer: String = "Right Answer"
        var wrongAnswer: String = "Wrong Answer"

        var question: Question = Question(1001, "random_text", "random_question", QuestionType(listOf(), "random_type", "random_condition"))

        val answerList = listOf(Answer("1000", wrongAnswer),
                Answer("1001", rightAnswer))
    }
}
