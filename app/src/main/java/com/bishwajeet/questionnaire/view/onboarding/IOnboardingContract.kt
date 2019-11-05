package com.bishwajeet.questionnaire.view.onboarding

interface IOnboardingContract {

    interface OnboardingView {

        fun dataLoadSuccessful(username: String)

        fun disableReadMode()

        fun enableReadMode()
    }

    interface OnboardingPresenter {

        fun onDataLoad()
    }
}