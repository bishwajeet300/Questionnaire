package com.bishwajeet.questionnaire.view.splash

import com.bishwajeet.questionnaire.model.preferences.ISharedPreferenceContract
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class SplashActivityModule {

    @Binds
    abstract fun provideSplashActivityView(activity: SplashActivity): ISplashContract.SplashView

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideSplashActivityPresenter(splashActivityView: ISplashContract.SplashView, iSharedPreferenceContract: ISharedPreferenceContract): ISplashContract.SplashPresenter =
                SplashActivityPresenter(splashActivityView, iSharedPreferenceContract)
    }
}
