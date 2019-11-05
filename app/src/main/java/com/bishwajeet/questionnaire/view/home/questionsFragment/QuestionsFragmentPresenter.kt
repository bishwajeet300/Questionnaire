package com.bishwajeet.questionnaire.view.home.questionsFragment

import com.bishwajeet.questionnaire.model.firebase.database.IFirebaseDatabase
import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.IfPositiveModel
import com.bishwajeet.questionnaire.model.pojo.Question
import com.bishwajeet.questionnaire.model.pojo.QuestionType
import com.bishwajeet.questionnaire.model.preferences.ISharedPreferenceContract
import com.google.gson.Gson
import javax.inject.Inject

class QuestionsFragmentPresenter @Inject constructor(questionsView: IQuestionsContract.QuestionsView, databaseInterface: IFirebaseDatabase,
                                                     sharedPreferencesContract: ISharedPreferenceContract) : IQuestionsContract.QuestionsPresenter {

    private var mDatabaseInterface: IFirebaseDatabase = databaseInterface
    private var mSharedPreferencesContract: ISharedPreferenceContract = sharedPreferencesContract
    private var mQuestionsFragmentView: IQuestionsContract.QuestionsView = questionsView


    override fun onLoading() {
    }


    override fun saveSelection(answer: Answer) {

        val username: String = mSharedPreferencesContract.fetchUsername()
        mDatabaseInterface.saveAnswer(username, answer) {
            if (it) {
                println("Was that saved?\\nYes! It was")
            }
        }
    }


    override fun shouldWaitForSubQuestion(questionObject: Question, currentAnswer: Answer): Boolean {
        val questionType: QuestionType = questionObject.question_type

        when (questionType.type) {
            "single_choice" -> {
                return false
            }
            "number_range" -> {
                return false
            }
            "single_choice_conditional" -> {
                val ifPositiveModel: IfPositiveModel = Gson().fromJson(questionType.condition, IfPositiveModel::class.java)

                if (ifPositiveModel.predicate.exactEquals.isNotEmpty()) {
                    val operationType: String = ifPositiveModel.predicate.exactEquals[0]
                    val operationValue: String = ifPositiveModel.predicate.exactEquals[1]

                    return if (operationType.contains("selection")) {
                        if (operationValue == currentAnswer.answer) {

                            println("shouldWaitForSubQuestion" + ifPositiveModel.if_positive.question)
                            mQuestionsFragmentView.showConditionalQuestionView(ifPositiveModel, true, "")
                            true
                        } else {
                            false
                        }
                    } else {
                        false
                    }
                } else {
                    return false
                }
            }
            else -> {
                return false
            }
        }
    }
}