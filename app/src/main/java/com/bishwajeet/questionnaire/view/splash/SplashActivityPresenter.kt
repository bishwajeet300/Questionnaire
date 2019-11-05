package com.bishwajeet.questionnaire.view.splash

import com.bishwajeet.questionnaire.model.preferences.ISharedPreferenceContract
import javax.inject.Inject

class SplashActivityPresenter @Inject constructor(splashActivityView: ISplashContract.SplashView,
                                                  private var sharedPreferences: ISharedPreferenceContract) : ISplashContract.SplashPresenter {


    init {
        splashActivityView.onSplashActivityLoading()
        sharedPreferences.storeUsername("")
    }


    override fun saveUsername(username: String) {
        sharedPreferences.storeUsername(username)
    }
}