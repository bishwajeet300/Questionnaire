package com.bishwajeet.questionnaire.view.splash

interface ISplashContract {

    interface SplashView {

        fun onSplashActivityLoading()
    }

    interface SplashPresenter {
        fun saveUsername(username: String)
    }
}