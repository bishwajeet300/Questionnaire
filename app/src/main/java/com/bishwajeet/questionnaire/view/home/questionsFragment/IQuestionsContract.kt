package com.bishwajeet.questionnaire.view.home.questionsFragment

import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.IfPositiveModel
import com.bishwajeet.questionnaire.model.pojo.Question

interface IQuestionsContract {

    interface QuestionsView {
        fun showConditionalQuestionView(ifPositiveModel: IfPositiveModel, operationmode: Boolean, answer: String)
    }

    interface QuestionsPresenter {

        fun onLoading()

        fun saveSelection(answer: Answer)

        fun shouldWaitForSubQuestion(questionObject: Question, currentAnswer: Answer): Boolean
    }
}