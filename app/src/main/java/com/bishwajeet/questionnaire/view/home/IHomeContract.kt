package com.bishwajeet.questionnaire.view.home

import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.IfPositiveModel
import com.bishwajeet.questionnaire.model.pojo.Question
import java.util.*

interface IHomeContract {

    interface HomeView {
        fun dataLoadSuccessful(items: List<Question>, mode: Boolean, answerList: LinkedList<Answer>?)

        fun setCategoryCounter(strCategoryCounter: String)

        fun setQuestionCounter(strQuestionCounter: String)

        fun setCurrentQuestion(strCurrentQuestion: String)

        fun setLastQuestion(strLastQuestion: String)

        fun setSecondLastQuestion(strSecondLastQuestion: String)

        fun closeQuestionnaire()

        fun showUserContent()

        fun isLaunchedInReadMode(): Boolean

        fun showSubQuestion(ifPositiveModel: IfPositiveModel, answer: String)
    }

    interface HomePresenter {

        fun onDataLoad(mode: Boolean, resetToStart: Boolean)

        fun onPageSelected(position: Int)

        fun verifyUserPreviousSession()
    }
}