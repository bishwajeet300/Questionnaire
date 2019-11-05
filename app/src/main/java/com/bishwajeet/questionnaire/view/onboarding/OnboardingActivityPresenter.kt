package com.bishwajeet.questionnaire.view.onboarding

import com.bishwajeet.questionnaire.model.firebase.database.IFirebaseDatabase
import com.bishwajeet.questionnaire.model.preferences.ISharedPreferenceContract
import javax.inject.Inject

class OnboardingActivityPresenter @Inject constructor(onboardingActivityView: IOnboardingContract.OnboardingView,
                                                      databaseInterface: IFirebaseDatabase, iSharedPreferenceContract: ISharedPreferenceContract) : IOnboardingContract.OnboardingPresenter {

    private var mOnboardingActivityView: IOnboardingContract.OnboardingView = onboardingActivityView
    private var mDatabaseInterface: IFirebaseDatabase = databaseInterface
    private var mSharedPreferenceContract: ISharedPreferenceContract = iSharedPreferenceContract


    override fun onDataLoad() {
        val username: String = mSharedPreferenceContract.fetchUsername()

        mDatabaseInterface.getUserAnswer(username) {
            if(it.isNullOrEmpty()) {
                mOnboardingActivityView.disableReadMode()
            } else {
                mOnboardingActivityView.enableReadMode()
            }
        }
        mOnboardingActivityView.dataLoadSuccessful(username)
    }
}