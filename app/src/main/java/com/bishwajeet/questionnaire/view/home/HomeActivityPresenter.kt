package com.bishwajeet.questionnaire.view.home

import com.bishwajeet.questionnaire.model.firebase.database.IFirebaseDatabase
import com.bishwajeet.questionnaire.model.pojo.IfPositiveModel
import com.bishwajeet.questionnaire.model.pojo.Question
import com.bishwajeet.questionnaire.model.preferences.ISharedPreferenceContract
import com.google.gson.Gson
import javax.inject.Inject

class HomeActivityPresenter @Inject constructor(homeActivityView: IHomeContract.HomeView,
                                                databaseInterface: IFirebaseDatabase, iSharedPreferenceContract: ISharedPreferenceContract) : IHomeContract.HomePresenter {

    private var mHomeActivityView: IHomeContract.HomeView = homeActivityView
    private var mDatabaseInterface: IFirebaseDatabase = databaseInterface
    private var mSharedPreferenceContract: ISharedPreferenceContract = iSharedPreferenceContract

    private lateinit var questionList: List<Question>
    private lateinit var categoryList: List<String>


    override fun onDataLoad(mode: Boolean, resetToStart: Boolean) {
        mDatabaseInterface.getQuestionnaire { result ->
            questionList = result.questions
            categoryList = result.categories

            if (!mode) {
                mDatabaseInterface.getUserAnswer(mSharedPreferenceContract.fetchUsername()) {
                    if (it.isNullOrEmpty()) {
                        mHomeActivityView.dataLoadSuccessful(questionList, mode, null)
                    } else {
                        mHomeActivityView.dataLoadSuccessful(questionList, mode, it)
                    }
                }
            } else {
                mHomeActivityView.dataLoadSuccessful(questionList, mode, null)
            }

            if (resetToStart) {
                onPageSelected(0)
            }
        }
    }


    override fun onPageSelected(position: Int) {
        if (questionList.size - 1 >= position) {
            generateCategoryCounter(position)
            generateQuestionCounter(position)
            fetchCurrentQuestion(position)
            fetchLastQuestion(position)
            fetchSecondLastQuestion(position)
            if (mHomeActivityView.isLaunchedInReadMode()) {
                verifyForSubQuestion(position)
            }
        } else {
            mHomeActivityView.closeQuestionnaire()
        }
    }


    private fun generateCategoryCounter(position: Int) {

        val totalCategoryCount = categoryList.size
        val currentPosition = getCurrentCategoryPosition(position)

        val strCategoryCounter = "$currentPosition/$totalCategoryCount"

        mHomeActivityView.setCategoryCounter(strCategoryCounter)
    }


    private fun generateQuestionCounter(position: Int) {

        val totalQuestionCount = questionList.size
        val currentPosition = getCurrentQuestionPosition(position)

        val strCategoryCounter = "$currentPosition/$totalQuestionCount"

        mHomeActivityView.setQuestionCounter(strCategoryCounter)
    }


    private fun fetchCurrentQuestion(position: Int) {
        mHomeActivityView.setCurrentQuestion(questionList[position].question)
    }


    private fun fetchLastQuestion(position: Int) {
        if (position > 0) {
            mHomeActivityView.setLastQuestion(questionList[position - 1].question)
        } else {
            mHomeActivityView.setLastQuestion("")
        }
    }


    private fun fetchSecondLastQuestion(position: Int) {
        if (position > 1) {
            mHomeActivityView.setSecondLastQuestion(questionList[position - 2].question)
        } else {
            mHomeActivityView.setSecondLastQuestion("")
        }
    }


    private fun getCurrentCategoryPosition(position: Int): Int {
        return categoryList.indexOf(questionList[position].category) + 1
    }


    private fun getCurrentQuestionPosition(position: Int): Int {
        return position + 1
    }


    private fun verifyForSubQuestion(position: Int) {
        if (questionList[position].question_type.condition != "") {
            when (questionList[position].question_type.type) {
                "single_choice" -> {
                    //No handling required
                }
                "number_range" -> {
                    //No handling required
                }
                "single_choice_conditional" -> {
                    val ifPositiveModel: IfPositiveModel = Gson().fromJson(questionList[position].question_type.condition, IfPositiveModel::class.java)

                    if (ifPositiveModel.predicate.exactEquals.isNotEmpty()) {
                        val operationType: String = ifPositiveModel.predicate.exactEquals[0]
                        val operationValue: String = ifPositiveModel.predicate.exactEquals[1]

                        if (operationType.contains("selection")) {
                            mDatabaseInterface.getAnswer(mSharedPreferenceContract.fetchUsername(), questionList[position].id.toString()) { it ->
                                if (operationValue == it.answer) {
                                    println("shouldWaitForSubQuestion" + ifPositiveModel.if_positive.question)

                                    mDatabaseInterface.getAnswer(mSharedPreferenceContract.fetchUsername(), ifPositiveModel.if_positive.id.toString()) {

                                        if(it.answer != "") {
                                            mHomeActivityView.showSubQuestion(ifPositiveModel, it.answer)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    override fun verifyUserPreviousSession() {

        val username: String = mSharedPreferenceContract.fetchUsername()

        mDatabaseInterface.getUserAnswer(username) {
            if (!it.isNullOrEmpty()) {
                mHomeActivityView.showUserContent()
            }
        }
    }
}